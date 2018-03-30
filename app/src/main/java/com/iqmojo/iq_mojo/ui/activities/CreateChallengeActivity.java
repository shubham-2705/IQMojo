package com.iqmojo.iq_mojo.ui.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.iqmojo.iq_mojo.models.response.CreateChallengeResponseModel;
import com.iqmojo.iq_mojo.models.response.GameItemResponse;
import com.iqmojo.iq_mojo.models.response.GameResultResponse;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;

import java.text.DecimalFormat;

public class CreateChallengeActivity extends BaseActivity implements onUpdateViewListener {

    GameItemResponse gameItemResponse;
    private EditText edtChallengeName, edtMaxPlayers, edtValidity;
    private RadioGroup rdGrpChallengeType, rdGrpChallengeAmount;
    //    private RadioButton rdBtnPublicType,rdBtnPrivateType,rdBtn5,rdBtn10,rdBtn15,rdBtn20;
    private TextView txvCreate;
    String amount = "", type = "", deeplinkText="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);

        getView();
        setupToolbar();

    }

    private void getView() {

        if (getIntent().getParcelableExtra(AppConstants.GAME_ITEM_OBJECT) != null)
            gameItemResponse = getIntent().getParcelableExtra(AppConstants.GAME_ITEM_OBJECT);

        edtChallengeName = (EditText) findViewById(R.id.edtChallengeName);
        edtMaxPlayers = (EditText) findViewById(R.id.edtMaxPlayers);
        edtValidity = (EditText) findViewById(R.id.edtValidity);
        rdGrpChallengeType = (RadioGroup) findViewById(R.id.rdGrpChallengeType);
        rdGrpChallengeAmount = (RadioGroup) findViewById(R.id.rdGrpChallengeAmount);
        txvCreate = (TextView) findViewById(R.id.txvCreate);


        txvCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    hitApiRequest(ApiConstants.REQUEST_TYPE.CREATE_CHALLENGE);
                }
            }
        });

    }

    private void fetchValues() {
        type = ((RadioButton) findViewById(rdGrpChallengeType.getCheckedRadioButtonId())).getText().toString().toLowerCase();
        amount = ((RadioButton) findViewById(rdGrpChallengeAmount.getCheckedRadioButtonId())).getText().toString();
    }

    private boolean validate() {

        if (TextUtils.isEmpty(edtChallengeName.getText().toString().trim())) {
            ToastUtil.showShortToast(this, "Please Enter Challenge Name");
            return false;
        }
        if (TextUtils.isEmpty(edtMaxPlayers.getText().toString().trim())) {
            ToastUtil.showShortToast(this, "Please Enter Max Players");
            return false;
        }
        if (TextUtils.isEmpty(edtValidity.getText().toString().trim())) {
            ToastUtil.showShortToast(this, "Please Enter Validity");
            return false;
        }

        fetchValues();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
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

            switch (reqType) {
                case ApiConstants.REQUEST_TYPE.CREATE_CHALLENGE:

                    clasz = CreateChallengeResponseModel.class;

                    url = ApiConstants.Urls.CREATE_CHALLENGE + "?" + "userId=" + IqMojoPrefrences.getInstance(CreateChallengeActivity.this).getInteger(AppConstants.KEY_USER_ID)
                            + "&gameId=" + gameItemResponse.getGameId() + "&challengeName=" + edtChallengeName.getText().toString().trim()
                            + "&challengeNature=" + type + "&maxPlay=" + edtMaxPlayers.getText().toString() + "&prizeMoney=" + amount
                            + "&validHours=" + "24";

                    url = url.replace(" ", "%20");
                    Log.v("url-->> ", url);
                    showProgressdialog("Please Wait..");
                    break;

                default:
                    break;

            }


            NetworkEngine.with(this).setClassType(clasz).setUrl(url).setRequestType(reqType).setHttpMethodType(Request.Method.GET).setUpdateViewListener(this).build();


        } catch (Exception e) {
            e.printStackTrace();
        }

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

    @Override
    public void updateView(Object responseObject, boolean isSuccess, int reqType) {
        try {
            hideProgressDialog();
            if (!isSuccess) {
            } else {
                switch (reqType) {
                    case ApiConstants.REQUEST_TYPE.CREATE_CHALLENGE:
                        CreateChallengeResponseModel createChallengeResponseModel = (CreateChallengeResponseModel) responseObject;
                        try {
                            if (createChallengeResponseModel.getStatus()==1) {

                                deeplinkText=createChallengeResponseModel.getChallengeText();
                                showShareDialog();
                            }
                            else
                                ToastUtil.showShortToast(this,createChallengeResponseModel.getChallengeText());
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

    public void showShareDialog() {
        // return dialog;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CreateChallengeActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
