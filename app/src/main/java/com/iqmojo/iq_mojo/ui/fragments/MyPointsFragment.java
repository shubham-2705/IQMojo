package com.iqmojo.iq_mojo.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iqmojo.R;
import com.iqmojo.base.ui.fragment.BaseFragment;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.iqmojo.iq_mojo.ui.activities.BankActivity;
import com.iqmojo.iq_mojo.ui.activities.PaytmActivity;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPointsFragment extends BaseFragment implements View.OnClickListener {

    View view;
    TextView btn_paytm, btn_bank;
    CircleImageView img_profile;
    String img_url;
    Context context;
    TextView text_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {

            if (view == null) {
                view = inflater.inflate(R.layout.fragment_my_points, container, false);
                context = getBaseActivity();

                text_name = (TextView) view.findViewById(R.id.text_name);
                btn_bank = (TextView) view.findViewById(R.id.btn_bank);
                btn_paytm = (TextView) view.findViewById(R.id.btn_paytm);
                btn_bank.setOnClickListener(this);
                btn_paytm.setOnClickListener(this);

                img_profile = (CircleImageView) view.findViewById(R.id.profile_image);

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
                    Picasso.with(getBaseActivity()).load(decoded_url).into(img_profile);
//                    img_profile.setBorderColor(ContextCompat.getColor(getBaseActivity(),R.color.white));
                }

                if (!TextUtils.isEmpty(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_GOOGLE_NAME)))
                    text_name.setText(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_GOOGLE_NAME));
                if (!TextUtils.isEmpty(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_FB_NAME)))
                    text_name.setText(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_FB_NAME));

            } else {
                final ViewParent parent = view.getParent();
                if (parent instanceof ViewManager) {
                    final ViewManager viewManager = (ViewManager) parent;
                    viewManager.removeView(view);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onClick(View v) {


        try {
            switch (v.getId()) {

                case R.id.btn_bank:

                    Intent i = new Intent(context, BankActivity.class);
                    startActivity(i);

                    break;

                case R.id.btn_paytm:
                    Intent i1 = new Intent(context, PaytmActivity.class);
                    startActivity(i1);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
