package com.iqmojo.iq_mojo.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.iqmojo.R;
import com.iqmojo.base.ui.activity.BaseActivity;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddCoins extends BaseActivity implements View.OnClickListener {

    TextView txv_paytm, txv_bank, text_rupees;
    CircleImageView img_profile;
    Context context;
    TextView text_name, txvCoinsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coins);

        setupToolbar();
        context = AddCoins.this;
        txv_paytm = (TextView) findViewById(R.id.txv_paytm);
//        txv_bank = (TextView) findViewById(R.id.txv_bank);
        text_rupees = (TextView) findViewById(R.id.text_rupees);

//        txv_bank.setOnClickListener(this);
        txv_paytm.setOnClickListener(this);

        text_name = (TextView) findViewById(R.id.text_name);
        txvCoinsText = (TextView) findViewById(R.id.txvCoinsText);
        img_profile = (CircleImageView) findViewById(R.id.profile_image);

        txvCoinsText.setText(getIntent().getStringExtra(AppConstants.ADD_COINS));

        if (getIntent().getStringExtra(AppConstants.ADD_COINS).equalsIgnoreCase("Add coins")) {
            txv_paytm.setText("Pay from Wallet");
        } else {
            txv_paytm.setText("Cash to Wallet");
        }

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
            Picasso.with(context).load(decoded_url).into(img_profile);
        }

        if (!TextUtils.isEmpty(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_GOOGLE_NAME)))
            text_name.setText(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_GOOGLE_NAME));
        if (!TextUtils.isEmpty(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_FB_NAME)))
            text_name.setText(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_FB_NAME));

        text_rupees.setText(("" + new DecimalFormat("##,##,##0").format(IqMojoPrefrences.getInstance(this).getLong(AppConstants.KEY_COINS))));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

//            case R.id.txv_bank:
//                if (txvCoinsText.getText().toString().trim().equalsIgnoreCase("Add Coins")) {
//                    Intent i = new Intent(context, AddCoinsDetails.class);
//                    startActivity(i);
//                } else {
//                    Intent i = new Intent(context, BankActivity.class);
//                    startActivity(i);
//                }
//
//
//                break;

            case R.id.txv_paytm:
                if (txvCoinsText.getText().toString().trim().equalsIgnoreCase("Add Coins")) {
                    Intent i = new Intent(context, AddCoinsDetails.class);
                    startActivity(i);

                } else {
                    Intent i = new Intent(context, PaytmActivity.class);
                    startActivity(i);
                }

                break;
        }

    }
}
