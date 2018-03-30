package com.iqmojo.iq_mojo.models.response;

/**
 * Created by shubhamlamba on 3/24/18.
 */

public class RedeemResponse {

    private  int status;
    private  int coins;
    private  int sysCoins;
    private  int walletCoins;
    private String redeemUrl;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getSysCoins() {
        return sysCoins;
    }

    public void setSysCoins(int sysCoins) {
        this.sysCoins = sysCoins;
    }

    public int getWalletCoins() {
        return walletCoins;
    }

    public void setWalletCoins(int walletCoins) {
        this.walletCoins = walletCoins;
    }

    public String getRedeemUrl() {
        return redeemUrl;
    }

    public void setRedeemUrl(String redeemUrl) {
        this.redeemUrl = redeemUrl;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
