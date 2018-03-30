package com.iqmojo.iq_mojo.models.response;

import java.util.ArrayList;

/**
 * Created by shubhamlamba on 3/24/18.
 */

public class StoreListResponse {

    private ArrayList<StoreItemResponse> games;

    public ArrayList<StoreItemResponse> getGames() {
        return games;
    }

    public void setGames(ArrayList<StoreItemResponse> games) {
        this.games = games;
    }
}
