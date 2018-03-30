package com.iqmojo.iq_mojo.models.response;

import java.util.ArrayList;

/**
 * Created by shubhamlamba on 1/24/18.
 */

public class ContestListResponse {

    private ArrayList<ContestItemResponse> contest;

    public ArrayList<ContestItemResponse> getContest() {
        return contest;
    }

    public void setContest(ArrayList<ContestItemResponse> contest) {
        this.contest = contest;
    }
}
