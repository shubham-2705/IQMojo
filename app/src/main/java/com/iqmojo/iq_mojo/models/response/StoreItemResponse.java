package com.iqmojo.iq_mojo.models.response;

/**
 * Created by shubhamlamba on 3/24/18.
 */

public class StoreItemResponse {

    private int offerId;
    private int cost;
    private int coins;

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
