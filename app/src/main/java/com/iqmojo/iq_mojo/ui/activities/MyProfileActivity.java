package com.iqmojo.iq_mojo.ui.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
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

public class MyProfileActivity extends BaseActivity {

    private EditText edtName, edtEmail, editMobile;
    TextView text_rupees;
    CircleImageView img_profile;
    TextView text_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile);

        try {
            setupToolbar();

            text_name = (TextView) findViewById(R.id.text_name);
            text_rupees = (TextView) findViewById(R.id.text_rupees);
            img_profile = (CircleImageView) findViewById(R.id.profile_image);
            edtName = (EditText) findViewById(R.id.edtName);
            edtEmail = (EditText) findViewById(R.id.edtEmail);
            editMobile = (EditText) findViewById(R.id.editMobile);

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
//                    img_profile.setBorderColor(ContextCompat.getColor(getBaseActivity(),R.color.white));
            }

            if (!TextUtils.isEmpty(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_GOOGLE_NAME))) {
                text_name.setText(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_GOOGLE_NAME));
                edtName.setText(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_GOOGLE_NAME));
                edtName.setEnabled(false);
            }
            if (!TextUtils.isEmpty(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_FB_NAME))) {
                text_name.setText(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_FB_NAME));
                edtName.setText(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_FB_NAME));
                edtName.setEnabled(false);
            }

            text_rupees.setText(("" + new DecimalFormat("##,##,##0").format(IqMojoPrefrences.getInstance(this).getLong(AppConstants.KEY_COINS))));

            editMobile.setText(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_MOBILE));
            editMobile.setEnabled(false);

            edtEmail.setText(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_EMAIL_ID));
            edtEmail.setEnabled(false);
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
}
