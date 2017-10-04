package com.iqmojo.iq_mojo.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.iqmojo.R;
import com.iqmojo.base.ui.activity.BaseActivity;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddCoins extends BaseActivity implements View.OnClickListener{

    TextView txv_paytm, txv_bank;
    CircleImageView img_profile;
    Context context;
    TextView text_name, txvCoinsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coins);

        context = AddCoins.this;
        txv_paytm = (TextView) findViewById(R.id.txv_paytm);
        txv_bank= (TextView) findViewById(R.id.txv_bank);

        txv_bank.setOnClickListener(this);
        txv_paytm.setOnClickListener(this);

        text_name = (TextView) findViewById(R.id.text_name);
        txvCoinsText = (TextView) findViewById(R.id.txvCoinsText);
        img_profile = (CircleImageView) findViewById(R.id.profile_image);

        txvCoinsText.setText(getIntent().getStringExtra(AppConstants.ADD_COINS));


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
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.txv_bank:

                Intent i = new Intent(context, BankActivity.class);
                startActivity(i);

                break;

            case R.id.txv_paytm:
                Intent i1 = new Intent(context, PaytmActivity.class);
                startActivity(i1);
                break;
        }

    }
}
