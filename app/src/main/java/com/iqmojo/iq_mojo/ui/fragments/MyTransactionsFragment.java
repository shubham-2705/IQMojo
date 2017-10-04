package com.iqmojo.iq_mojo.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iqmojo.R;
import com.iqmojo.base.listeners.onUpdateViewListener;
import com.iqmojo.base.ui.fragment.BasePagerFragment;
import com.iqmojo.iq_mojo.constants.ApiConstants;
import com.iqmojo.iq_mojo.models.response.GameListResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTransactionsFragment extends BasePagerFragment implements onUpdateViewListener {


    public MyTransactionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_transactions, container, false);
    }

    @Override
    public void onFragmentFocused() {

    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void updateView(Object responseObject, boolean isSuccess, int reqType) {
        try {
            getBaseActivity().hideProgressDialog();
            if (!isSuccess) {
//                buildAndComm.showOkDialog(UpiCreateVpaActivity.this, (String) responseObject);
            } else {
                switch (reqType) {
                    case ApiConstants.REQUEST_TYPE.GAME_LIST:
                       // GameListResponse gameListResponse = (GameListResponse) responseObject;
                        try {


                        } catch (Exception e) {
                            getBaseActivity().hideProgressDialog();
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
