package com.iqmojo.iq_mojo.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.iqmojo.R;
import com.iqmojo.base.listeners.onUpdateViewListener;
import com.iqmojo.base.network.NetworkEngine;
import com.iqmojo.base.ui.fragment.BasePagerFragment;
import com.iqmojo.base.utils.ConnectivityUtils;
import com.iqmojo.base.utils.ToastUtil;
import com.iqmojo.iq_mojo.constants.ApiConstants;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.models.response.ChallengeItemResponse;
import com.iqmojo.iq_mojo.models.response.ChallengeListResponse;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.iqmojo.iq_mojo.ui.activities.HomeActivity;
import com.iqmojo.iq_mojo.ui.adapters.ChallengeListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChallengeListBaseFragment extends BasePagerFragment implements onUpdateViewListener {


    ViewPager viewPager;
    TabLayout tabs;
    View view;
    ViewPagerAdapter adapter;
    private ArrayList<ChallengeItemResponse> challengeItemResponsesByYou = new ArrayList<>();
    private ArrayList<ChallengeItemResponse> challengeItemResponsesForYou = new ArrayList<>();
    private ArrayList<ChallengeItemResponse> challengeItemResponses = new ArrayList<>();

    public ChallengeListBaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {

            view = inflater.inflate(R.layout.fragment_challenge_list_base, container, false);
            initViews(view);
            hitApiRequest(ApiConstants.REQUEST_TYPE.CHALLENGE_LIST);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void initViews(View v) {
        viewPager = (ViewPager) v.findViewById(R.id.pager);

        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(0);

        tabs = (TabLayout) v.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }

    public void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void updateViewPager() {
        ChallengeListFragment challengeListFragmentByYou = new ChallengeListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("challenge_list", challengeItemResponsesByYou);
        challengeListFragmentByYou.setArguments(bundle);

        ChallengeListFragment challengeListFragmentForYou = new ChallengeListFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putParcelableArrayList("challenge_list", challengeItemResponsesForYou);
        challengeListFragmentForYou.setArguments(bundle1);

        adapter.addFrag(challengeListFragmentByYou, "By You");
        adapter.addFrag(challengeListFragmentForYou, "For You");
        adapter.notifyDataSetChanged();
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
//            getBaseActivity().showProgressdialog("Loading...");

            switch (reqType) {
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
            getBaseActivity().hideProgressDialog();
            if (!isSuccess) {
//                buildAndComm.showOkDialog(UpiCreateVpaActivity.this, (String) responseObject);
            } else {
                switch (reqType) {
                    case ApiConstants.REQUEST_TYPE.CHALLENGE_LIST:
                        ChallengeListResponse challengeListResponse = (ChallengeListResponse) responseObject;
                        try {

                            // filter list "by you " and "for you" and notify
                            challengeItemResponses.clear();
                            challengeItemResponses = challengeListResponse.getMyChallenge();
                            challengeItemResponsesForYou.clear();
                            challengeItemResponsesByYou.clear();
                            if (challengeItemResponses !=null && challengeItemResponses.size() >0) {
                                for (ChallengeItemResponse challengeItemResponse : challengeItemResponses) {
                                    if (challengeItemResponse.getOrigin() == 0) {
                                        challengeItemResponsesForYou.add(challengeItemResponse);
                                    } else {
                                        challengeItemResponsesByYou.add(challengeItemResponse);
                                    }
                                }
                            }
                            updateViewPager();

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

    @Override
    public String getTitle() {
        return null;
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
