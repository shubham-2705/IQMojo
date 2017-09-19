package com.iqmojo.iq_mojo.models.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shubhamlamba on 19/09/17.
 */

public class QuestionItemResponse implements Parcelable {

    private int qId;
    private int qLevel;
    private String qText;
    private String imageUrl;
    private String ansOptions;
    private String category;
    private int validOption;
    private int allowTime;


    public int getqId() {
        return qId;
    }

    public void setqId(int qId) {
        this.qId = qId;
    }

    public int getqLevel() {
        return qLevel;
    }

    public void setqLevel(int qLevel) {
        this.qLevel = qLevel;
    }

    public String getqText() {
        return qText;
    }

    public void setqText(String qText) {
        this.qText = qText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAnsOptions() {
        return ansOptions;
    }

    public void setAnsOptions(String ansOptions) {
        this.ansOptions = ansOptions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getValidOption() {
        return validOption;
    }

    public void setValidOption(int validOption) {
        this.validOption = validOption;
    }

    public int getAllowTime() {
        return allowTime;
    }

    public void setAllowTime(int allowTime) {
        this.allowTime = allowTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.qId);
        dest.writeInt(this.qLevel);
        dest.writeString(this.qText);
        dest.writeString(this.imageUrl);
        dest.writeString(this.ansOptions);
        dest.writeString(this.category);
        dest.writeInt(this.validOption);
        dest.writeInt(this.allowTime);
    }

    public QuestionItemResponse() {
    }

    protected QuestionItemResponse(Parcel in) {
        this.qId = in.readInt();
        this.qLevel = in.readInt();
        this.qText = in.readString();
        this.imageUrl = in.readString();
        this.ansOptions = in.readString();
        this.category = in.readString();
        this.validOption = in.readInt();
        this.allowTime = in.readInt();
    }

    public static final Parcelable.Creator<QuestionItemResponse> CREATOR = new Parcelable.Creator<QuestionItemResponse>() {
        @Override
        public QuestionItemResponse createFromParcel(Parcel source) {
            return new QuestionItemResponse(source);
        }

        @Override
        public QuestionItemResponse[] newArray(int size) {
            return new QuestionItemResponse[size];
        }
    };
}
