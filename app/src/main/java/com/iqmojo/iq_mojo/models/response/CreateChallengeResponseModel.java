package com.iqmojo.iq_mojo.models.response;

/**
 * Created by shubhamlamba on 11/22/17.
 */

public class CreateChallengeResponseModel {

    private Long userId;
    private Long challengeId;
    private int status;
    private String challengeText;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getChallengeText() {
        return challengeText;
    }

    public void setChallengeText(String challengeText) {
        this.challengeText = challengeText;
    }
}
