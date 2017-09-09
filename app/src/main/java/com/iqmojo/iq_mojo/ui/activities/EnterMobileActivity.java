package com.iqmojo.iq_mojo.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.iqmojo.R;
import com.iqmojo.base.listeners.onUpdateViewListener;
import com.iqmojo.base.network.NetworkEngine;
import com.iqmojo.base.ui.activity.BaseActivity;
import com.iqmojo.base.utils.ConnectivityUtils;
import com.iqmojo.base.utils.PermissionUtil;
import com.iqmojo.base.utils.ToastUtil;
import com.iqmojo.iq_mojo.constants.ApiConstants;
import com.iqmojo.iq_mojo.models.response.RegisterResponse;
import com.iqmojo.iq_mojo.utils.CommonFunctionsUtil;

public class EnterMobileActivity extends BaseActivity implements View.OnClickListener, onUpdateViewListener {

    private EditText edtMobile;
    private TextView txvGetOtp;
    private ProgressBar pbLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mobile);

        getView();


        edtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0)
                {
                    edtMobile.setLetterSpacing(0.5f);
                }
                else
                {
                    edtMobile.setLetterSpacing(0f);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void getView()
    {
        edtMobile=(EditText)findViewById(R.id.edtMobile);
        txvGetOtp=(TextView) findViewById(R.id.txvGetOtp);
        pbLoading=(ProgressBar) findViewById(R.id.pbLoading);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        switch (id)
        {
            case R.id.txvGetOtp:
                if(validateNumber())
                {
                    PermissionUtil.with(this).setCallback(new PermissionUtil.PermissionGrantedListener() {
                        @Override
                        public void onPermissionResult(boolean isGranted, int requestCode) {
                            if (isGranted) {
                                hitApiRequest(ApiConstants.REQUEST_TYPE.REGISTER_USER);
                            }
                        }
                    }).validate(Manifest.permission.READ_PHONE_STATE);
                }
                break;

        }
    }

    public boolean validateNumber()
    {

        if(!CommonFunctionsUtil.validateMobileNumber(edtMobile.getText().toString().trim()))
        {
            ToastUtil.showShortToast(this,"Enter vaild mobile number");
            return false;
        }

        return true;
    }

    private void hitApiRequest(int reqType) {
        try {
            // register
            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                ToastUtil.showShortToast(this,getString(R.string.error_network_not_available));
                return;
            }

            Class clasz = null;
            String url="";
            showProgressdialog("Verifying...");

            switch (reqType) {
                case ApiConstants.REQUEST_TYPE.REGISTER_USER:

                    clasz = RegisterResponse.class;
                    url=ApiConstants.Urls.REGISTER_USER+"?";

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
                        RegisterResponse vpaInfoResponseModel = (RegisterResponse) responseObject;
                        try {
                            // ------- write your code here
//                            if () {
//
//                            } else {
//                                hideProgressDialog();
//                            }
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
