package com.iqmojo.iq_mojo.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MyPointsFragment extends BaseFragment implements View.OnClickListener {

    View view;
    Button btn_paytm, btn_bank;
    ImageView img_profile;
    String img_url;
    Context context;
    TextView text_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_points, container, false);

        context = getActivity();

        text_name = (TextView) view.findViewById(R.id.text_name);
        btn_bank = (Button) view.findViewById(R.id.btn_bank);
        btn_paytm = (Button) view.findViewById(R.id.btn_paytm);
        btn_bank.setOnClickListener(this);
        btn_paytm.setOnClickListener(this);

        img_profile = (ImageView) view.findViewById(R.id.img_profile_pic);

        img_url =  IqMojoPrefrences.getInstance(context).getString(AppConstants.KEY_DISPLAY_PIC);

        if (img_url != null && !TextUtils.isEmpty(img_url))
            Picasso.with(context).load(img_url).into(img_profile);

        if (!TextUtils.isEmpty(IqMojoPrefrences.getInstance(context).getString(AppConstants.KEY_DISPLAY_NAME)))
        text_name.setText(IqMojoPrefrences.getInstance(context).getString(AppConstants.KEY_DISPLAY_NAME));

        return view;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.btn_bank:

                Intent i = new Intent(context, BankActivity.class);
                startActivity(i);

                break;

            case R.id.btn_paytm:
                Intent i1 = new Intent(context, PaytmActivity.class);
                startActivity(i1);
                break;
        }

    }
}
