package com.iqmojo.iq_mojo.models.response;

import java.util.ArrayList;

/**
 * Created by shubhamlamba on 09/09/17.
 */

public class RegisterResponse {


    private String respText;
    private Integer userId;
    private Long otp;
    private Long coins;
    private Boolean newUser;
    private ArrayList<TabModel> validTab;

    public ArrayList<TabModel> getValidTab() {
        return validTab;
    }

    public void setValidTab(ArrayList<TabModel> validTab) {
        this.validTab = validTab;
    }

    public String getRespText() {
        return respText;
    }

    public void setRespText(String respText) {
        this.respText = respText;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getOtp() {
        return otp;
    }

    public void setOtp(Long otp) {
        this.otp = otp;
    }

    public Long getCoins() {
        return coins;
    }

    public void setCoins(Long coins) {
        this.coins = coins;
    }

    public Boolean getNewUser() {
        return newUser;
    }

    public void setNewUser(Boolean newUser) {
        this.newUser = newUser;
    }
}
