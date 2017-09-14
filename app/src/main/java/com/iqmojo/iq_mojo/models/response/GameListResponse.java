package com.iqmojo.iq_mojo.models.response;

import java.util.ArrayList;

/**
 * Created by shubhamlamba on 13/09/17.
 */

public class GameListResponse {

    private ArrayList<GameItemResponse> games;

    public ArrayList<GameItemResponse> getGames() {
        return games;
    }

    public void setGames(ArrayList<GameItemResponse> games) {
        this.games = games;
    }
}
