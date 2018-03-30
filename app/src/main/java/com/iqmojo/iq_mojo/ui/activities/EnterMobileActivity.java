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
import com.iqmojo.iq_mojo.persistence.PrefrenceConstants;
import com.iqmojo.iq_mojo.utils.CommonFunctionsUtil;
import com.iqmojo.iq_mojo.utils.FontHelper;

public class EnterMobileActivity extends BaseActivity implements View.OnClickListener, onUpdateViewListener {

    private EditText edtMobile;
    private TextView txvGetOtp;
    private String email = "", location = "", google_id = "",fb_id="", gcmId = "", google_token;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mobile);

        try {
            getView();

            if (getIntent().getExtras().getString(AppConstants.EMAIL_ID)!=null && !TextUtils.isEmpty(getIntent().getExtras().getString(AppConstants.EMAIL_ID)))
                email = getIntent().getExtras().getString(AppConstants.EMAIL_ID);
            else email = "";

            if (getIntent().getExtras().getString(AppConstants.GOOGLE_ID)!=null && !TextUtils.isEmpty(getIntent().getExtras().getString(AppConstants.GOOGLE_ID)))
                google_id = getIntent().getExtras().getString(AppConstants.GOOGLE_ID);
            else google_id = "";

            if (getIntent().getExtras().getString(AppConstants.FB_ID)!=null && !TextUtils.isEmpty(getIntent().getExtras().getString(AppConstants.FB_ID)))
                fb_id = getIntent().getExtras().getString(AppConstants.FB_ID);
            else fb_id = "";

            if (getIntent().getExtras().getString(AppConstants.LOCATION)!=null && !TextUtils.isEmpty(getIntent().getExtras().getString(AppConstants.LOCATION)))
                location = getIntent().getExtras().getString(AppConstants.LOCATION);
            else location = "";

            if (getIntent().getExtras().getString(AppConstants.DEVICE_TOKEN)!=null && !TextUtils.isEmpty(getIntent().getExtras().getString(AppConstants.DEVICE_TOKEN)))
                gcmId = getIntent().getExtras().getString(AppConstants.DEVICE_TOKEN);
            else gcmId = "";

            if (getIntent().getExtras().getString(AppConstants.GOOGLE_TOKEN)!=null && !TextUtils.isEmpty(getIntent().getExtras().getString(AppConstants.GOOGLE_TOKEN)))
                google_token = getIntent().getExtras().getString(AppConstants.GOOGLE_TOKEN);
            else google_token = "";


            edtMobile.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        // Do something for lollipop and above versions
                        if (s.length() > 0) {
                            edtMobile.setLetterSpacing(0.5f);
                        } else {
                            edtMobile.setLetterSpacing(0f);
                        }
                    }
                    if(s.length()==10)
                    {
                        hideDialogKeypad();
                        hideKeypad();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getView() {
        context = EnterMobileActivity.this;
        edtMobile = (EditText) findViewById(R.id.edtMobile);
        txvGetOtp = (TextView) findViewById(R.id.txvGetOtp);
        txvGetOtp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        try {
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
        } catch (Exception e) {
            e.printStackTrace();
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
                            "&deviceId=" + CommonFunctionsUtil.getDeviceImei(context) + "&deviceType=ANDROID" + "&deviceToken=" + gcmId
                            + "&androidVersion=" + Build.VERSION.RELEASE + "&androidId=" + CommonFunctionsUtil.getVersionName() + "&networkType=" +
                            CommonFunctionsUtil.getNetworkInfo(context) + "&utm_Source=" + IqMojoPrefrences.getInstance(this).getString(PrefrenceConstants.UTM_SOURCE) +
                            "&utm_Medium=" + IqMojoPrefrences.getInstance(this).getString(PrefrenceConstants.UTM_MEDIUM) + "&googlePlayerId=" + "" + "&googleId=" + google_id +
                            "&googleName=" + IqMojoPrefrences.getInstance(context).getString(AppConstants.KEY_GOOGLE_NAME) + "&googleToken=" +
                            google_token + "&googlePicture=" + IqMojoPrefrences.getInstance(context).getString(AppConstants.KEY_GOOGLE_PIC) + "&fBPlayerId=" + fb_id;

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

                            Intent i = new Intent(context, EnterOtpActivity.class);
                            i.putExtra(AppConstants.MOBILE, edtMobile.getText().toString().trim());
                            i.putExtra(AppConstants.KEY_USER_ID, registerResponse.getUserId());
                            i.putExtra(AppConstants.KEY_COINS, registerResponse.getCoins());
                            i.putExtra(AppConstants.KEY_EMAIL_ID, email);
                            i.putExtra(AppConstants.KEY_OTP, registerResponse.getOtp());
                            i.putParcelableArrayListExtra(AppConstants.KEY_TAB_LIST,registerResponse.getValidTab());
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
