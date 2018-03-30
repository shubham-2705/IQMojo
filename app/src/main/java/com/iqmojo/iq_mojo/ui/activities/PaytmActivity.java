package com.iqmojo.iq_mojo.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.iqmojo.R;
import com.iqmojo.base.listeners.UpdateGsonListener;
import com.iqmojo.base.listeners.onUpdateViewListener;
import com.iqmojo.base.network.NetworkEngine;
import com.iqmojo.base.ui.activity.BaseActivity;
import com.iqmojo.base.utils.ConnectivityUtils;
import com.iqmojo.base.utils.ToastUtil;
import com.iqmojo.iq_mojo.constants.ApiConstants;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.models.response.RedeemResponse;
import com.iqmojo.iq_mojo.models.response.ResendResponse;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.iqmojo.iq_mojo.utils.CommonFunctionsUtil;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class PaytmActivity extends BaseActivity implements onUpdateViewListener {

    private EditText edtMobile, editAmount;
    private TextView txvSubmit, text_rupees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm);

        try {
            setupToolbar();

            edtMobile = (EditText) findViewById(R.id.edtMobile);
            editAmount = (EditText) findViewById(R.id.editAmount);
            txvSubmit = (TextView) findViewById(R.id.txvSubmit);
            edtMobile.setEnabled(false);
            edtMobile.setText(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_MOBILE));
            text_rupees = (TextView) findViewById(R.id.text_rupees);
            text_rupees.setText(("" + new DecimalFormat("##,##,##0").format(IqMojoPrefrences.getInstance(this).getLong(AppConstants.KEY_COINS))));

            try {
                txvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(editAmount.getText().toString().trim()) && Double.parseDouble(editAmount.getText().toString().trim()) > 10) {
                            hitApiRequest(ApiConstants.REQUEST_TYPE.REDEEM);
                        } else {
                            ToastUtil.showShortToast(PaytmActivity.this, "Please Enter Amount greater than 10");
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setLogo(R.drawable.iqmojo_toolbar);

        TextView txvCoins = (TextView) mToolbar.findViewById(R.id.txvCoins);
        txvCoins.setText(("" + new DecimalFormat("##,##,##0").format(IqMojoPrefrences.getInstance(this).getLong(AppConstants.KEY_COINS))));

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
            showProgressdialog("Please Wait...");

            switch (reqType) {
                case ApiConstants.REQUEST_TYPE.REDEEM:

                    clasz = RedeemResponse.class;

                    url = ApiConstants.Urls.REDEEM + "?" + "msisdn=" + IqMojoPrefrences.getInstance(this).getString(AppConstants.MOBILE) + "&amount=" + editAmount.getText().toString().trim() +
                            "&userId=" + IqMojoPrefrences.getInstance(PaytmActivity.this).getInteger(AppConstants.KEY_USER_ID) + "&otp=" + IqMojoPrefrences.getInstance(PaytmActivity.this).getLong(AppConstants.KEY_OTP);
                    url = url.replace(" ", "%20");
                    Log.v("url-->> ", url);
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
                    case ApiConstants.REQUEST_TYPE.REDEEM:
                        RedeemResponse redeemResponse = (RedeemResponse) responseObject;
                        try {

                            if (redeemResponse.getStatus() == 1) {

                                String url = "https://www.google.com";
                                if (!TextUtils.isEmpty(url)) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(browserIntent);
                                } else {
                                    ToastUtil.showShortToast(PaytmActivity.this, redeemResponse.getMsg());
                                }
                            } else {
                                if (!org.apache.http.util.TextUtils.isEmpty(redeemResponse.getMsg())) {
                                    ToastUtil.showShortToast(PaytmActivity.this, redeemResponse.getMsg());
                                }
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
