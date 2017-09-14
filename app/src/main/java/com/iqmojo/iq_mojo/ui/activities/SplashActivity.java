package com.iqmojo.iq_mojo.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.iqmojo.BuildConfig;
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
import com.iqmojo.iq_mojo.models.response.RegisterResponse;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.iqmojo.iq_mojo.ui.activities.HomeActivity;
import com.iqmojo.iq_mojo.utils.CommonFunctionsUtil;

/**
 * Created by shubhamlamba on 18/08/17.
 */

public class SplashActivity extends BaseActivity implements onUpdateViewListener {


    int userid;
    long otp;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = SplashActivity.this;

        try {

            userid = IqMojoPrefrences.getInstance(context).getInteger(AppConstants.KEY_USER_ID);
            otp = IqMojoPrefrences.getInstance(context).getLong(AppConstants.KEY_OTP);

            if (!(userid == -1) && !(otp == -1)) {

                PermissionUtil.with(context).setCallback(new PermissionUtil.PermissionGrantedListener() {
                    @Override
                    public void onPermissionResult(boolean isGranted, int requestCode) {
                        if (isGranted) {
                            // permission is granted
                            hitApiRequest(ApiConstants.REQUEST_TYPE.LOGIN);
                        }
                    }
                }).validate(Manifest.permission.READ_PHONE_STATE);
            }else {

                Intent i = new Intent(context, LoginActivity.class);
                startActivity(i);


            }
        }catch (Exception ex){
            ex.printStackTrace();
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
                case ApiConstants.REQUEST_TYPE.LOGIN:

                    clasz = LoginResponse.class;

                    url = ApiConstants.Urls.LOGIN + "?" + "email=" +IqMojoPrefrences.getInstance(SplashActivity.this).getString(AppConstants.KEY_EMAIL_ID) +"&deviceId=" + CommonFunctionsUtil.getDeviceImei(SplashActivity.this) + "&deviceToken=" + IqMojoPrefrences.getInstance(SplashActivity.this).getString(AppConstants.KEY_GCM_ID) +
                            "&UserId=" + IqMojoPrefrences.getInstance(SplashActivity.this).getInteger(AppConstants.KEY_USER_ID) + "&Otp=" + IqMojoPrefrences.getInstance(SplashActivity.this).getLong(AppConstants.KEY_OTP);

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
                    case ApiConstants.REQUEST_TYPE.LOGIN:
                        LoginResponse loginResponse = (LoginResponse) responseObject;
                        try {
                            if (loginResponse.getLoginStatus()) {
                                Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                                startActivity(i);
                            }
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


