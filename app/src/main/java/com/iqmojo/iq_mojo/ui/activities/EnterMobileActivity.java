package com.iqmojo.iq_mojo.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.iqmojo.BuildConfig;
import com.iqmojo.R;
import com.iqmojo.base.listeners.onUpdateViewListener;
import com.iqmojo.base.network.NetworkEngine;
import com.iqmojo.base.ui.activity.BaseActivity;
import com.iqmojo.base.utils.ConnectivityUtils;
import com.iqmojo.base.utils.PermissionUtil;
import com.iqmojo.base.utils.ShowLog;
import com.iqmojo.base.utils.ToastUtil;
import com.iqmojo.iq_mojo.constants.ApiConstants;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.models.response.RegisterResponse;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.iqmojo.iq_mojo.utils.CommonFunctionsUtil;
import com.iqmojo.iq_mojo.utils.FontHelper;

public class EnterMobileActivity extends BaseActivity implements View.OnClickListener, onUpdateViewListener {

    private EditText edtMobile;
    private TextView txvGetOtp;
    private ProgressBar pbLoading;
    private String email = "", location = "", id = "", gcmId = "";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mobile);

        getView();

        if (!TextUtils.isEmpty(getIntent().getExtras().getString(AppConstants.EMAIL_ID)))
            email = getIntent().getExtras().getString(AppConstants.EMAIL_ID);
        else email = "";

        if (!TextUtils.isEmpty(getIntent().getExtras().getString(AppConstants.FB_ID)))
            id = getIntent().getExtras().getString(AppConstants.FB_ID);
        else id = "";

        if (!TextUtils.isEmpty(getIntent().getExtras().getString(AppConstants.LOCATION)))
            location = getIntent().getExtras().getString(AppConstants.LOCATION);
        else location = "";
        if (!TextUtils.isEmpty(getIntent().getExtras().getString(AppConstants.DEVICE_TOKEN)))
            gcmId = getIntent().getExtras().getString(AppConstants.DEVICE_TOKEN);
        else gcmId = "";


        edtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    edtMobile.setLetterSpacing(0.5f);
                } else {
                    edtMobile.setLetterSpacing(0f);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void getView() {
        context = EnterMobileActivity.this;
        edtMobile = (EditText) findViewById(R.id.edtMobile);
        txvGetOtp = (TextView) findViewById(R.id.txvGetOtp);
        pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
        txvGetOtp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.txvGetOtp:
                if (validateNumber()) {
                    PermissionUtil.with(EnterMobileActivity.this).setCallback(new PermissionUtil.PermissionGrantedListener() {
                        @Override
                        public void onPermissionResult(boolean isGranted, int requestCode) {
                            if (isGranted) {
                                // permission is granted
                                PermissionUtil.with(EnterMobileActivity.this).setCallback(new PermissionUtil.PermissionGrantedListener() {
                                    @Override
                                    public void onPermissionResult(boolean isGranted, int requestCode) {
                                        if (isGranted) {
                                            // permission is granted
                                            hitApiRequest(ApiConstants.REQUEST_TYPE.REGISTER_USER);
                                        }
                                    }
                                }).validate(Manifest.permission.READ_SMS);
                            }
                        }
                    }).validate(Manifest.permission.READ_PHONE_STATE);
                }
                break;
        }
    }

    public boolean validateNumber() {

        if (!CommonFunctionsUtil.validateMobileNumber(edtMobile.getText().toString().trim())) {
            ToastUtil.showShortToast(this, "Enter valid mobile number");
            return false;
        }

        return true;
    }

    private void hitApiRequest(int reqType) {
        try {
            // register
            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                ToastUtil.showShortToast(this, getString(R.string.error_network_not_available));
                return;
            }

            Class clasz = null;
            String url = "";
            showProgressdialog("Verifying...");

            switch (reqType) {
                case ApiConstants.REQUEST_TYPE.REGISTER_USER:

                    clasz = RegisterResponse.class;

                    // api request
                    url = ApiConstants.Urls.REGISTER_USER + "?" + "msisdn=" + edtMobile.getText().toString().trim() + "&email=" + email
                            + "&country=" + location + "&appVersion=" + BuildConfig.VERSION_NAME + "&deviceName=" + android.os.Build.MODEL +
                            "&deviceId=" + CommonFunctionsUtil.getDeviceImei(context) + "&deviceType=android" + "&deviceToken=" + gcmId
                            + "&androidVersion=" + Build.VERSION.RELEASE + "&androidId=" + CommonFunctionsUtil.getVersionName() + "&networkType=" +
                            CommonFunctionsUtil.getNetworkInfo(context) + "&utm_Source=" + "" + "&utm_Medium=" + "" + "&googlePlayerId=" + "" + "&googleId=" + "" +
                            "&googleName=" + "" + "&googleToken=" + "" + "&googlePicture=" + "" + "&fBPlayerId=" + id;
                    url = url.replace(" ", "%20");
                    ShowLog.v("url-->> ", url);
                    break;

                default:
                    break;

            }

            NetworkEngine.with(this).setClassType(clasz).setUrl(url).setRequestType(reqType).setHttpMethodType(Request.Method.GET).setUpdateViewListener(this).build();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateView(Object responseObject, boolean isSuccess, int reqType) {

        try {
            hideProgressDialog();
            if (!isSuccess) {
//                buildAndComm.showOkDialog(UpiCreateVpaActivity.this, (String) responseObject);
            } else {
                switch (reqType) {
                    case ApiConstants.REQUEST_TYPE.REGISTER_USER:
                        RegisterResponse registerResponse = (RegisterResponse) responseObject;
                        try {

                            IqMojoPrefrences.getInstance(context).setInteger(AppConstants.KEY_USER_ID, registerResponse.getUserId());
                            IqMojoPrefrences.getInstance(context).setLong(AppConstants.KEY_COINS, registerResponse.getCoins());
                            IqMojoPrefrences.getInstance(context).setString(AppConstants.KEY_EMAIL_ID, email);
                            IqMojoPrefrences.getInstance(context).setLong(AppConstants.KEY_OTP, registerResponse.getOtp());


                            Intent i = new Intent(context, EnterOtpActivity.class);
                            i.putExtra(AppConstants.MOBILE, edtMobile.getText().toString().trim());
                            startActivity(i);

                        } catch (Exception e) {
                            hideProgressDialog();
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
