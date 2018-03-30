package com.iqmojo.iq_mojo.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewParent;
import android.widget.TextView;

import com.android.volley.Request;
import com.iqmojo.R;
import com.iqmojo.base.listeners.onUpdateViewListener;
import com.iqmojo.base.network.NetworkEngine;
import com.iqmojo.base.ui.fragment.BaseFragment;
import com.iqmojo.base.ui.fragment.BasePagerFragment;
import com.iqmojo.base.utils.ConnectivityUtils;
import com.iqmojo.base.utils.ToastUtil;
import com.iqmojo.iq_mojo.constants.ApiConstants;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.models.response.ChallengeItemResponse;
import com.iqmojo.iq_mojo.models.response.ChallengeListResponse;
import com.iqmojo.iq_mojo.models.response.CreateChallengeResponseModel;
import com.iqmojo.iq_mojo.models.response.GameListResponse;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.iqmojo.iq_mojo.ui.adapters.ChallengeListAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChallengeListFragment extends BasePagerFragment implements onUpdateViewListener {

    private RecyclerView recycler_view;
    View view;
    private TextView emptyView;
    private ChallengeListAdapter challengeListAdapter;
    private ArrayList<ChallengeItemResponse> challengeItemResponses = new ArrayList<>();

    public ChallengeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.getParcelableArrayList("challenge_list") !=null) {
            challengeItemResponses = bundle.getParcelableArrayList("challenge_list");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {

            view = inflater.inflate(R.layout.fragment_home, container, false);
            initViews(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public void initViews(View view) {
        getIds();

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

            if (challengeItemResponses != null && challengeItemResponses.size() > 0) {
                challengeListAdapter = new ChallengeListAdapter(getBaseActivity(), challengeItemResponses, this);
                recycler_view.setAdapter(challengeListAdapter);
            } else {
                emptyView.setVisibility(View.VISIBLE);
                emptyView.setText("No Challenges!");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onFragmentFocused() {

    }

    public void callStopChallengeApi(long challengeId) {

        hitApiRequest(ApiConstants.REQUEST_TYPE.CHALLENGE_STOP, challengeId);
    }

    private void refreshChallengeList() {
        hitApiRequest(ApiConstants.REQUEST_TYPE.CHALLENGE_LIST,0);
    }

    private void hitApiRequest(int reqType, long challengeId) {
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
                case ApiConstants.REQUEST_TYPE.CHALLENGE_STOP:

                    clasz = CreateChallengeResponseModel.class;

                    // api request
                    url = ApiConstants.Urls.CHALLENGE_STOP + "?" + "userId=" + IqMojoPrefrences.getInstance(getBaseActivity()).getInteger(AppConstants.KEY_USER_ID)+"&gameId=" + challengeId;
                    url = url.replace(" ", "%20");
                    Log.v("url-->> ", url);
                    break;

                case ApiConstants.REQUEST_TYPE.CHALLENGE_LIST:

                    clasz = ChallengeListResponse.class;

                    // api request
                    url = ApiConstants.Urls.CHALLENGE_LIST + "?" + "userId=" + IqMojoPrefrences.getInstance(getBaseActivity()).getInteger(AppConstants.KEY_USER_ID);
                    url = url.replace(" ", "%20");
                    Log.v("url-->> ", url);
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
    public void updateView(Object responseObject, boolean isSuccess, int reqType) {

        try {

            if (!isSuccess) {
                getBaseActivity().hideProgressDialog();
//                buildAndComm.showOkDialog(UpiCreateVpaActivity.this, (String) responseObject);
            } else {
                switch (reqType) {
                    case ApiConstants.REQUEST_TYPE.CHALLENGE_STOP:
                        CreateChallengeResponseModel createChallengeResponseModel = (CreateChallengeResponseModel) responseObject;
                        if (createChallengeResponseModel.getStatus() == 1) {
                            refreshChallengeList();
                        } else {
                            getBaseActivity().hideProgressDialog();
                        }
                    case ApiConstants.REQUEST_TYPE.CHALLENGE_LIST:
                        if (responseObject instanceof ChallengeListResponse) {
                            getBaseActivity().hideProgressDialog();
                            ChallengeListResponse challengeListResponse = (ChallengeListResponse) responseObject;
                            try {
                                ArrayList<ChallengeItemResponse> challengeItemResponses = new ArrayList<>();
                                if (challengeListResponse.getMyChallenge() != null && challengeListResponse.getMyChallenge().size() > 0) {
                                    for (ChallengeItemResponse challengeItemResponse : challengeListResponse.getMyChallenge()) {
                                        if (challengeItemResponse.getOrigin() == 1) {
                                            challengeItemResponses.add(challengeItemResponse);
                                        }
                                    }
                                    updateList(challengeItemResponses);
                                } else {

                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                                getBaseActivity().hideProgressDialog();
                            }
                        }
                        break;
                }
            }
        } catch (Exception e) {
            getBaseActivity().hideProgressDialog();
            e.printStackTrace();
        }

    }

    private void updateList(ArrayList<ChallengeItemResponse> challengeItemResponses) {
        challengeListAdapter.setData(getBaseActivity(), challengeItemResponses, this);
        if (challengeItemResponses.size() == 0){
            showEmptyView();
        }
    }

    public void showEmptyView() {
        recycler_view.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setText("No Challenges!");
    }

}
