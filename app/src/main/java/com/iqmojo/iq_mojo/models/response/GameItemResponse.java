package com.iqmojo.iq_mojo.models.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shubhamlamba on 13/09/17.
 */

public class GameItemResponse implements Parcelable {

    private String name;
    private int gameId;
    private String imageUrl;
    private String descImage;
    private String shortDesc;
    private String detailDesc;
    private String tAndC;
    private int entryFee;
    private int active;
    private int totalQ;
    private int maxAttempt;
    private int reward;

    public String getDescImage() {
        return descImage;
    }

    public void setDescImage(String descImage) {
        this.descImage = descImage;
    }

    public int getMaxAttempt() {
        return maxAttempt;
    }

    public void setMaxAttempt(int maxAttempt) {
        this.maxAttempt = maxAttempt;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public static Creator<GameItemResponse> getCREATOR() {
        return CREATOR;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String gettAndC() {
        return tAndC;
    }

    public void settAndC(String tAndC) {
        this.tAndC = tAndC;
    }

    public int getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(int entryFee) {
        this.entryFee = entryFee;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getTotalQ() {
        return totalQ;
    }

    public void setTotalQ(int totalQ) {
        this.totalQ = totalQ;
    }

    public GameItemResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.gameId);
        dest.writeString(this.imageUrl);
        dest.writeString(this.descImage);
        dest.writeString(this.shortDesc);
        dest.writeString(this.detailDesc);
        dest.writeString(this.tAndC);
        dest.writeInt(this.entryFee);
        dest.writeInt(this.active);
        dest.writeInt(this.totalQ);
        dest.writeInt(this.maxAttempt);
        dest.writeInt(this.reward);
    }

    protected GameItemResponse(Parcel in) {
        this.name = in.readString();
        this.gameId = in.readInt();
        this.imageUrl = in.readString();
        this.descImage = in.readString();
        this.shortDesc = in.readString();
        this.detailDesc = in.readString();
        this.tAndC = in.readString();
        this.entryFee = in.readInt();
        this.active = in.readInt();
        this.totalQ = in.readInt();
        this.maxAttempt = in.readInt();
        this.reward = in.readInt();
    }

    public static final Creator<GameItemResponse> CREATOR = new Creator<GameItemResponse>() {
        @Override
        public GameItemResponse createFromParcel(Parcel source) {
            return new GameItemResponse(source);
        }

        @Override
        public GameItemResponse[] newArray(int size) {
            return new GameItemResponse[size];
        }
    };
}
