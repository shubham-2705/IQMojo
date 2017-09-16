package com.iqmojo.iq_mojo.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iqmojo.R;
import com.iqmojo.base.ui.fragment.BaseFragment;

public class MyPointsFragment extends BaseFragment implements View.OnClickListener {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_points, container, false);





        return view;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }
}
