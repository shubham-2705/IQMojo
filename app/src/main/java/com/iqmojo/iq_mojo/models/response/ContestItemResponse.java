package com.iqmojo.iq_mojo.models.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shubhamlamba on 1/24/18.
 */

public class ContestItemResponse implements Parcelable {

    private int contestId;
    private String name;
    private String descImage;
    private String imageUrl;
    private String shortDesc;
    private String tAndC;
    private String detailDesc;
    private int entryFee;
    private int active;
    private int totalQ;
    private int qLevel;
    private int baseReward;
    private int reward;

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescImage() {
        return descImage;
    }

    public void setDescImage(String descImage) {
        this.descImage = descImage;
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

    public String gettAndC() {
        return tAndC;
    }

    public void settAndC(String tAndC) {
        this.tAndC = tAndC;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
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

    public int getqLevel() {
        return qLevel;
    }

    public void setqLevel(int qLevel) {
        this.qLevel = qLevel;
    }

    public int getBaseReward() {
        return baseReward;
    }

    public void setBaseReward(int baseReward) {
        this.baseReward = baseReward;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.contestId);
        dest.writeString(this.name);
        dest.writeString(this.descImage);
        dest.writeString(this.imageUrl);
        dest.writeString(this.shortDesc);
        dest.writeString(this.tAndC);
        dest.writeString(this.detailDesc);
        dest.writeInt(this.entryFee);
        dest.writeInt(this.active);
        dest.writeInt(this.totalQ);
        dest.writeInt(this.qLevel);
        dest.writeInt(this.baseReward);
        dest.writeInt(this.reward);
    }

    public ContestItemResponse() {
    }

    protected ContestItemResponse(Parcel in) {
        this.contestId = in.readInt();
        this.name = in.readString();
        this.descImage = in.readString();
        this.imageUrl = in.readString();
        this.shortDesc = in.readString();
        this.tAndC = in.readString();
        this.detailDesc = in.readString();
        this.entryFee = in.readInt();
        this.active = in.readInt();
        this.totalQ = in.readInt();
        this.qLevel = in.readInt();
        this.baseReward = in.readInt();
        this.reward = in.readInt();
    }

    public static final Parcelable.Creator<ContestItemResponse> CREATOR = new Parcelable.Creator<ContestItemResponse>() {
        @Override
        public ContestItemResponse createFromParcel(Parcel source) {
            return new ContestItemResponse(source);
        }

        @Override
        public ContestItemResponse[] newArray(int size) {
            return new ContestItemResponse[size];
        }
    };
}
