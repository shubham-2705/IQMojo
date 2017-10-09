package com.iqmojo.iq_mojo.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iqmojo.R;
import com.iqmojo.iq_mojo.models.response.TransactionListItemResponse;

import java.util.ArrayList;

/**
 * Created by himanshu on 9/10/17.
 */

public class TransactionListAdapter extends BaseAdapter {

    Context context;
    ArrayList<TransactionListItemResponse> transactionList;


    public TransactionListAdapter(Context context, ArrayList<TransactionListItemResponse> transactionList){

        this.context = context;
        this.transactionList = transactionList;
    }


    @Override
    public int getCount() {
        return transactionList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=null;
        if (v==null) {

            LayoutInflater layin=(LayoutInflater)context.getSystemService( Context.LAYOUT_INFLATER_SERVICE);
            v=layin.inflate(R.layout.transaction_list_childs, null);
        }

       /* CoinCatalogBean catalogBean = arraylist.get(position);

        TextView textTitle=(TextView)v.findViewById(R.id.text_title_ID);
        textTitle.setText(catalogBean.Title);

        TextView textCat=(TextView)v.findViewById(R.id.text_coincategory_ID);

        if (catalogBean.Category.contains("IRA Eligible")) {
            textCat.setText("IRA Eligible");
        } else {
            textCat.setText("");
        }*/

        TransactionListItemResponse transactionListItemResponse = transactionList.get(position);

        TextView textDate = (TextView)v.findViewById(R.id.textDate);
        TextView textActivity = (TextView)v.findViewById(R.id.textActivity);
        TextView textAmount = (TextView)v.findViewById(R.id.textAmount);

        textDate.setText(formatDate(transactionListItemResponse.getTxTime()));
      //  textAmount.setText(transactionListItemResponse.get);

        return v;
    }

    String formatDate(String Date){

        String[] dateSplit = Date.split(" ");
        return dateSplit[0];
    }

}
