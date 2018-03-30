package com.iqmojo.iq_mojo.models.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shubhamlamba on 12/3/17.
 */

public class ChallengeItemResponse implements Parcelable {

    private long cId;
    private String challengeName;
    private String challengeNature;
    private int maxPlay;
    private int prizeMoney;
    private int win;
    private int loss;
    private int origin;
    private String challengeText;


    public String getChallengeText() {
        return challengeText;
    }

    public void setChallengeText(String challengeText) {
        this.challengeText = challengeText;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public long getCid() {
        return cId;
    }

    public void setCid(long cId) {
        this.cId = cId;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public String getChallengeNature() {
        return challengeNature;
    }

    public void setChallengeNature(String challengeNature) {
        this.challengeNature = challengeNature;
    }

    public int getMaxPlay() {
        return maxPlay;
    }

    public void setMaxPlay(int maxPlay) {
        this.maxPlay = maxPlay;
    }

    public int getPrizeMoney() {
        return prizeMoney;
    }

    public void setPrizeMoney(int prizeMoney) {
        this.prizeMoney = prizeMoney;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public ChallengeItemResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.cId);
        dest.writeString(this.challengeName);
        dest.writeString(this.challengeNature);
        dest.writeInt(this.maxPlay);
        dest.writeInt(this.prizeMoney);
        dest.writeInt(this.win);
        dest.writeInt(this.loss);
        dest.writeInt(this.origin);
        dest.writeString(this.challengeText);
    }

    protected ChallengeItemResponse(Parcel in) {
        this.cId = in.readLong();
        this.challengeName = in.readString();
        this.challengeNature = in.readString();
        this.maxPlay = in.readInt();
        this.prizeMoney = in.readInt();
        this.win = in.readInt();
        this.loss = in.readInt();
        this.origin = in.readInt();
        this.challengeText = in.readString();
    }

    public static final Creator<ChallengeItemResponse> CREATOR = new Creator<ChallengeItemResponse>() {
        @Override
        public ChallengeItemResponse createFromParcel(Parcel source) {
            return new ChallengeItemResponse(source);
        }

        @Override
        public ChallengeItemResponse[] newArray(int size) {
            return new ChallengeItemResponse[size];
        }
    };
}
