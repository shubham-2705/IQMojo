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

import com.android.volley.Request;
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
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;

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

                                txvCost.setText("₹ "+storeItemResponse.getCost());
                                txvCoinsValue.setText(""+storeItemResponse.getCoins()+" Coins");

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
