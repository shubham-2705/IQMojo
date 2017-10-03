package com.iqmojo.iq_mojo.models.response;

import java.util.ArrayList;

/**
 * Created by shubhamlamba on 03/10/17.
 */

public class TransactionListResponse {

    private ArrayList<TransactionListItemResponse> list;

    public ArrayList<TransactionListItemResponse> getTransaction_list() {
        return list;
    }

    public void setTransaction_list(ArrayList<TransactionListItemResponse> transaction_list) {
        this.list = transaction_list;
    }
}
