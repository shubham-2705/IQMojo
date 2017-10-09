package com.iqmojo.iq_mojo.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.iqmojo.iq_mojo.models.response.GameListResponse;
import com.iqmojo.iq_mojo.models.response.TransactionListItemResponse;
import com.iqmojo.iq_mojo.models.response.TransactionListResponse;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.iqmojo.iq_mojo.utils.ExpandableListAdapter;
import com.iqmojo.iq_mojo.utils.TransactionListAdapter;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyTransactionsFragment extends BasePagerFragment implements  onUpdateViewListener, View.OnClickListener {

    View view;
    ArrayList<TransactionListItemResponse> transactionListResponsesCredit = new ArrayList<TransactionListItemResponse>();
    ArrayList<TransactionListItemResponse> transactionListResponsesDebit = new ArrayList<TransactionListItemResponse>();
    TransactionListAdapter transactionListAdapter, debitAdapter;
    ListView listCredit, listDebit;
    RelativeLayout relativeCredit, relativeDebit;
    TextView text_name, txvCoinsText, txt_amountCredit, txt_amountdebit;
    CircleImageView img_profile;
    long debitAmount, creditAmount;

    public MyTransactionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_transactions, container, false);


        txt_amountdebit = (TextView)view.findViewById(R.id.txt_amountdebit);
        txt_amountCredit = (TextView)view.findViewById(R.id.txt_amountCredit);

        listCredit = (ListView) view.findViewById(R.id.listCredit);
        relativeCredit = (RelativeLayout)view.findViewById(R.id.relativeCredit);
        relativeCredit.setOnClickListener(this);

        listDebit = (ListView) view.findViewById(R.id.listDebit);
        relativeDebit = (RelativeLayout)view.findViewById(R.id.relativeDebit);
        relativeDebit.setOnClickListener(this);

        listCredit.setVisibility(View.GONE);
        listDebit.setVisibility(View.GONE);

        text_name = (TextView) view.findViewById(R.id.text_name);
        txvCoinsText = (TextView) view.findViewById(R.id.txvCoinsText);
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
            Picasso.with(getActivity()).load(decoded_url).into(img_profile);
        }

        if (!TextUtils.isEmpty(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_GOOGLE_NAME)))
            text_name.setText(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_GOOGLE_NAME));
        if (!TextUtils.isEmpty(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_FB_NAME)))
            text_name.setText(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_FB_NAME));

        hitApiRequest(ApiConstants.REQUEST_TYPE.TXN);
        return view;
    }

    @Override
    public void onFragmentFocused() {

        hitApiRequest(ApiConstants.REQUEST_TYPE.TXN);
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
                    case ApiConstants.REQUEST_TYPE.TXN:
                        TransactionListResponse transactionListResponse = (TransactionListResponse) responseObject;
                        try {

                            if(transactionListResponse != null){

                                for(int i = 0; i<transactionListResponse.getTransaction_list().size(); i++){

                                    if (transactionListResponse.getTransaction_list().get(i).getTxType().equalsIgnoreCase("C")){
                                        transactionListResponsesCredit.add(transactionListResponse.getTransaction_list().get(i));
                                        creditAmount = creditAmount + transactionListResponse.getTransaction_list().get(i).getCoins();
                                        txt_amountCredit.setText(String.valueOf(creditAmount));
                                    }else if (transactionListResponse.getTransaction_list().get(i).getTxType().equalsIgnoreCase("D")){
                                        transactionListResponsesDebit.add(transactionListResponse.getTransaction_list().get(i));
                                        debitAmount = debitAmount + transactionListResponse.getTransaction_list().get(i).getCoins();
                                        txt_amountdebit.setText(String.valueOf(debitAmount));
                                    }
                                }




                                transactionListAdapter = new TransactionListAdapter(getActivity(), transactionListResponsesCredit);
                                listCredit.setAdapter(transactionListAdapter);

                                debitAdapter = new TransactionListAdapter(getActivity(), transactionListResponsesDebit);
                                listDebit.setAdapter(debitAdapter);
                            }
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
                case ApiConstants.REQUEST_TYPE.TXN:

                    clasz = TransactionListResponse.class;

                    // api request
                    url = ApiConstants.Urls.TXN + "?" + "userId="+ IqMojoPrefrences.getInstance(getBaseActivity()).getInteger(AppConstants.KEY_USER_ID);
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
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.relativeCredit:

                listDebit.setVisibility(View.GONE);
                listCredit.setVisibility(View.VISIBLE);
                break;

            case R.id.relativeDebit:

                listCredit.setVisibility(View.GONE);
                listDebit.setVisibility(View.VISIBLE);
                break;
        }

    }
}
