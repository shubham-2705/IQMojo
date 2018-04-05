package com.iqmojo.iq_mojo.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.iqmojo.BuildConfig;
import com.iqmojo.R;
import com.iqmojo.base.listeners.onUpdateViewListener;
import com.iqmojo.base.network.NetworkEngine;
import com.iqmojo.base.ui.activity.BaseActivity;
import com.iqmojo.base.utils.ConnectivityUtils;
import com.iqmojo.base.utils.ToastUtil;
import com.iqmojo.iq_mojo.constants.ApiConstants;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.models.response.StoreItemResponse;
import com.iqmojo.iq_mojo.models.response.StoreListResponse;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddCoinsDetails extends BaseActivity implements onUpdateViewListener {

    CircleImageView img_profile;
    Context context;
    TextView text_name, text_rupees;
    private LinearLayout dynamicLayout;
    private ArrayList<StoreItemResponse> storeListResponses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coins_details);

        setupUIandToolbar();

        hitApiRequest(ApiConstants.REQUEST_TYPE.STORE_LIST);
    }

    private void setupUIandToolbar() {
        text_rupees = (TextView) findViewById(R.id.text_rupees);
        text_name = (TextView) findViewById(R.id.text_name);
        dynamicLayout = (LinearLayout) findViewById(R.id.dynamicLayout);
        img_profile = (CircleImageView) findViewById(R.id.profile_image);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setLogo(R.drawable.iqmojo_toolbar);

        TextView txvCoins = (TextView) mToolbar.findViewById(R.id.txvCoins);
        txvCoins.setText(("" + new DecimalFormat("##,##,##0").format(IqMojoPrefrences.getInstance(this).getLong(AppConstants.KEY_COINS))));

        String decoded_url = null;
        try {
            if (!TextUtils.isEmpty(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_GOOGLE_PIC)))
                decoded_url = URLDecoder.decode(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_GOOGLE_PIC), "UTF-8");
            if (!TextUtils.isEmpty(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_FB_PIC)))
                decoded_url = URLDecoder.decode(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_FB_PIC), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (decoded_url != null && !TextUtils.isEmpty(decoded_url)) {
            Picasso.with(this).load(decoded_url).into(img_profile);
        }

        if (!TextUtils.isEmpty(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_GOOGLE_NAME)))
            text_name.setText(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_GOOGLE_NAME));
        if (!TextUtils.isEmpty(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_FB_NAME)))
            text_name.setText(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_FB_NAME));

        text_rupees.setText(("" + new DecimalFormat("##,##,##0").format(IqMojoPrefrences.getInstance(this).getLong(AppConstants.KEY_COINS))));


//        PaytmPGService paytmService = null;
//        if (BuildConfig.IS_PAYTM_STAGING)
//
//        {
//            paytmService = PaytmPGService.getStagingService();
//        } else
//
//        {
//            paytmService = PaytmPGService.getProductionService();
//        }
//
//        //Create new order Object having all order information.
//        Map<String, String> paramMap = new HashMap<String, String>();
//        paramMap.put("MID", "PAYTM_MERCHANT_ID");
//        paramMap.put("ORDER_ID", "ORDER0000000001");
//        paramMap.put("CUST_ID", "10000988111");
//        paramMap.put("INDUSTRY_TYPE_ID", "PAYTM_INDUSTRY_TYPE_ID");
//        paramMap.put("CHANNEL_ID", "WAP");
//        paramMap.put("TXN_AMOUNT", "1");
//        paramMap.put("WEBSITE", "PAYTM_WEBSITE");
//        paramMap.put("CALLBACK_URL", "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=ORDER0000000001");
//        paramMap.put("EMAIL", "abc@gmail.com");
//        paramMap.put("MOBILE_NO", "9999999999");
//        paramMap.put("CHECKSUMHASH", "w2QDRMgp1/BNdEnJEAPCIOmNgQvsi+BhpqijfM9KvFfRiPmGSt3Ddzw+oTaGCLneJwxFFq5mqTMwJXdQE2EzK4px2xruDqKZjHupz9yXev4=");
//
//        PaytmOrder paytmOrder = new PaytmOrder(paramMap);
//
//        paytmService.initialize(paytmOrder, null);
//
//        //Start the Payment Transaction. Before starting the transaction ensure that initialize method is called.
//
//        paytmService.startPaymentTransaction(this, true, true, new
//
//                PaytmPaymentTransactionCallback() {
//
//                    @Override
//                    public void someUIErrorOccurred(String inErrorMessage) {
//                        Log.d("LOG", "UI Error Occur.");
//                        Toast.makeText(getApplicationContext(), " UI Error Occur. ", Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onTransactionResponse(Bundle inResponse) {
//                        Log.d("LOG", "Payment Transaction : " + inResponse);
//                        Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void networkNotAvailable() {
//                        Log.d("LOG", "UI Error Occur.");
//                        Toast.makeText(getApplicationContext(), " UI Error Occur. ", Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void clientAuthenticationFailed(String inErrorMessage) {
//                        Log.d("LOG", "UI Error Occur.");
//                        Toast.makeText(getApplicationContext(), " Severside Error " + inErrorMessage, Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onErrorLoadingWebPage(int iniErrorCode,
//                                                      String inErrorMessage, String inFailingUrl) {
//
//                    }
//
//                    @Override
//                    public void onBackPressedCancelTransaction() {
//// TODO Auto-generated method stub
//                    }
//
//                    @Override
//                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
//                        Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
//                        Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
//                    }
//
//                });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
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
                case ApiConstants.REQUEST_TYPE.STORE_LIST:

                    clasz = StoreListResponse.class;

                    url = ApiConstants.Urls.STORE_LIST + "?" + "&userId=" + IqMojoPrefrences.getInstance(AddCoinsDetails.this).getInteger(AppConstants.KEY_USER_ID);
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
                    case ApiConstants.REQUEST_TYPE.STORE_LIST:
                        StoreListResponse storeListResponse = (StoreListResponse) responseObject;
                        try {
                            storeListResponses = storeListResponse.getGames();

                            for (StoreItemResponse storeItemResponse : storeListResponses) {
                                View view = getLayoutInflater().inflate(R.layout.store_item_layout, null);

                                TextView txvCoinsValue = (TextView) view.findViewById(R.id.txvCoinsValue);
                                TextView txvCost = (TextView) view.findViewById(R.id.txvCost);

                                txvCost.setText("₹ " + storeItemResponse.getCost());
                                txvCoinsValue.setText("" + storeItemResponse.getCoins() + " Coins");

                                dynamicLayout.addView(view);
                            }

                        } catch (Exception e) {
                            hideProgressDialog();
                            e.printStackTrace();
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
