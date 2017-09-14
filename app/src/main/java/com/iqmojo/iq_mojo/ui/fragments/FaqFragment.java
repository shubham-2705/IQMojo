package com.iqmojo.iq_mojo.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iqmojo.R;
import com.iqmojo.base.ui.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FaqFragment extends BaseFragment {


    public FaqFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public String getTitle() {
        return null;
    }
}
