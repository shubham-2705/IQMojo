package com.iqmojo.iq_mojo.models.response;

import java.util.ArrayList;

/**
 * Created by shubhamlamba on 12/3/17.
 */

public class ChallengeListResponse {
    private ArrayList<ChallengeItemResponse> myChallenge;

    public ArrayList<ChallengeItemResponse> getMyChallenge() {
        return myChallenge;
    }

    public void setMyChallenge(ArrayList<ChallengeItemResponse> myChallenge) {
        this.myChallenge = myChallenge;
    }
}
