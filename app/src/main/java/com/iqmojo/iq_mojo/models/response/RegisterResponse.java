package com.iqmojo.iq_mojo.models.response;

/**
 * Created by shubhamlamba on 09/09/17.
 */

public class RegisterResponse {

    private String userId;
    private String otp;
    private String coins;
    private String respText;
    private String newUser;

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getOtp() {
        return otp;
    }

    public String getNewUser() {
        return newUser;
    }

    public void setNewUser(String newUser) {
        this.newUser = newUser;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getRespText() {
        return respText;
    }

    public void setRespText(String respText) {
        this.respText = respText;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
