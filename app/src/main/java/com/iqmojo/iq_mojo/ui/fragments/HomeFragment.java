package com.iqmojo.iq_mojo.ui.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.InputQueue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.iqmojo.BuildConfig;
import com.iqmojo.R;
import com.iqmojo.base.listeners.onUpdateViewListener;
import com.iqmojo.base.network.NetworkEngine;
import com.iqmojo.base.ui.fragment.BaseFragment;
import com.iqmojo.base.ui.fragment.BasePagerFragment;
import com.iqmojo.base.utils.ConnectivityUtils;
import com.iqmojo.base.utils.ToastUtil;
import com.iqmojo.iq_mojo.constants.ApiConstants;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.models.response.GameItemResponse;
import com.iqmojo.iq_mojo.models.response.GameListResponse;
import com.iqmojo.iq_mojo.models.response.RegisterResponse;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.iqmojo.iq_mojo.ui.adapters.GameListAdapter;
import com.iqmojo.iq_mojo.utils.CommonFunctionsUtil;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BasePagerFragment implements onUpdateViewListener, View.OnClickListener{

    private RecyclerView recycler_view;
    View view;
    private TextView emptyView;
    private GameListAdapter gameListAdapter;
    private ArrayList<GameItemResponse> gameItemResponses;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {

            if (view == null) {
                view = inflater.inflate(R.layout.fragment_home, container, false);
                initViews(view);

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

    public void initViews(View view) {
        getIds();

        hitApiRequest(ApiConstants.REQUEST_TYPE.GAME_LIST);

    }

    public void getIds() {

        try {
            recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
            emptyView = (TextView) view.findViewById(R.id.emptyView);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseActivity().getApplicationContext());
            recycler_view.setLayoutManager(layoutManager);


//        float converted = getBaseActivity().convertPixelsToDp(16, getBaseActivity());
//        int leftSpacingInDp = (int) converted, rightSpacingInDp = (int) converted;
//        converted = getBaseActivity().convertPixelsToDp(14, getBaseActivity());
//        int upSpacingInDp = (int) converted, downSpacingDp = (int) converted;
//        recycler_view.addItemDecoration(new SpacesItemDecoration(leftSpacingInDp, rightSpacingInDp, upSpacingInDp, downSpacingDp));
//        productResponses=new ArrayList<ProductResponse>();
//

            gameItemResponses = new ArrayList<GameItemResponse>();
            gameListAdapter = new GameListAdapter(getBaseActivity(), gameItemResponses, this);
            recycler_view.setAdapter(gameListAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void hitApiRequest(int reqType) {
        try {
            // register
            if (!ConnectivityUtils.isNetworkEnabled(getBaseActivity())) {
                ToastUtil.showShortToast(getBaseActivity(), getString(R.string.error_network_not_available));
                return;
            }

            Class clasz = null;
            String url = "";
            getBaseActivity().showProgressdialog("Loading...");

            switch (reqType) {
                case ApiConstants.REQUEST_TYPE.GAME_LIST:

                    clasz = GameListResponse.class;

                    // api request
                    url = ApiConstants.Urls.GAME_LIST + "?" + "userId="+ IqMojoPrefrences.getInstance(getBaseActivity()).getInteger(AppConstants.KEY_USER_ID);
                    url = url.replace(" ", "%20");
                    Log.v("url-->> ",url);
                    break;

                default:
                    break;

            }

            NetworkEngine.with(getBaseActivity()).setClassType(clasz).setUrl(url).setRequestType(reqType).setHttpMethodType(Request.Method.GET).setUpdateViewListener(this).build();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onClick(View v) {

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
                        GameListResponse gameListResponse = (GameListResponse) responseObject;
                        try {

                            gameItemResponses=gameListResponse.getGames();
                            gameListAdapter.setData(getBaseActivity(),gameItemResponses,this);

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

    @Override
    public void onFragmentFocused() {

    }
}
