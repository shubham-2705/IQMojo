package com.iqmojo.iq_mojo.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.iqmojo.R;
import com.iqmojo.base.listeners.onUpdateViewListener;
import com.iqmojo.base.network.NetworkEngine;
import com.iqmojo.base.ui.activity.BaseActivity;
import com.iqmojo.base.utils.ConnectivityUtils;
import com.iqmojo.base.utils.PermissionUtil;
import com.iqmojo.base.utils.ToastUtil;
import com.iqmojo.iq_mojo.constants.ApiConstants;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.models.response.LoginResponse;
import com.iqmojo.iq_mojo.models.response.ResendResponse;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.iqmojo.iq_mojo.utils.CommonFunctionsUtil;
import com.iqmojo.iq_mojo.utils.FontHelper;

public class EnterOtpActivity extends BaseActivity implements View.OnClickListener, onUpdateViewListener {

    private EditText edtOTP;
    private TextView txvDone,txvResend,txvgetCode;
    private Context context;
    private String mobileNo;
    private int attempt = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        context = EnterOtpActivity.this;
        mobileNo = getIntent().getStringExtra(AppConstants.MOBILE);
        getView();

        edtOTP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0)
                {
                    edtOTP.setLetterSpacing(0.5f);
                }
                else
                {
                    edtOTP.setLetterSpacing(0f);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    public void getView()
    {
        edtOTP=(EditText)findViewById(R.id.edtOTP);
        txvDone=(TextView) findViewById(R.id.txvDone);
        txvResend=(TextView) findViewById(R.id.txvResend);
        txvgetCode=(TextView) findViewById(R.id.txvgetCode);
//        pbLoading=(ProgressBar) findViewById(R.id.pbLoading);



        txvDone.setOnClickListener(this);
        edtOTP.setText(""+IqMojoPrefrences.getInstance(context).getLong(AppConstants.KEY_OTP));

    }

    @Override
    public void onClick(View v) {

        int id=v.getId();

        switch (id)
        {
            case R.id.txvDone:

                IqMojoPrefrences.getInstance(context).setInteger(AppConstants.KEY_USER_ID, getIntent().getIntExtra(AppConstants.KEY_USER_ID, 0));
                IqMojoPrefrences.getInstance(context).setLong(AppConstants.KEY_COINS, getIntent().getLongExtra(AppConstants.KEY_COINS,0));
                IqMojoPrefrences.getInstance(context).setString(AppConstants.KEY_EMAIL_ID, getIntent().getStringExtra(AppConstants.KEY_EMAIL_ID));
                IqMojoPrefrences.getInstance(context).setLong(AppConstants.KEY_OTP, getIntent().getLongExtra(AppConstants.KEY_OTP,0));

                Intent i = new Intent(context, HomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

                break;
            case R.id.txvResend:

/*
                http://108.161.135.146:9397/iqm/api/v1/user/sendOtp/?msisdn=

                include parameters msisdn, deviceId, googleMail and attempt*/

                PermissionUtil.with(EnterOtpActivity.this).setCallback(new PermissionUtil.PermissionGrantedListener() {
                    @Override
                    public void onPermissionResult(boolean isGranted, int requestCode) {
                        if (isGranted) {

                            hitApiRequest(ApiConstants.REQUEST_TYPE.RESEND);
                        }
                    }
                }).validate(Manifest.permission.READ_PHONE_STATE);
                break;

        }
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
                case ApiConstants.REQUEST_TYPE.RESEND:

                    clasz = ResendResponse.class;

                    url = ApiConstants.Urls.RESEND + "?" + "msisdn=" + getIntent().getStringExtra(AppConstants.MOBILE) +"&deviceId=" + CommonFunctionsUtil.getDeviceImei(context) + "&googleMail=" + IqMojoPrefrences.getInstance(context).getString(AppConstants.KEY_EMAIL_ID) +
                            "&attempt=" + attempt;
                    url = url.replace(" ", "%20");
                    Log.v("url-->> ",url);
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
                    case ApiConstants.REQUEST_TYPE.RESEND:
                        ResendResponse resendResponse = (ResendResponse) responseObject;
                        try {

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
