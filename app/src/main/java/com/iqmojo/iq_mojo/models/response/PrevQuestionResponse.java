package com.iqmojo.iq_mojo.models.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shubhamlamba on 19/09/17.
 */

public class PrevQuestionResponse implements Parcelable {

    private int qId;
    private int result;
    private int validAnswer;



    public int getqId() {
        return qId;
    }

    public void setqId(int qId) {
        this.qId = qId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getValidAnswer() {
        return validAnswer;
    }

    public void setValidAnswer(int validAnswer) {
        this.validAnswer = validAnswer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.qId);
        dest.writeInt(this.result);
        dest.writeInt(this.validAnswer);
    }

    public PrevQuestionResponse() {
    }

    protected PrevQuestionResponse(Parcel in) {
        this.qId = in.readInt();
        this.result = in.readInt();
        this.validAnswer = in.readInt();
    }

    public static final Parcelable.Creator<PrevQuestionResponse> CREATOR = new Parcelable.Creator<PrevQuestionResponse>() {
        @Override
        public PrevQuestionResponse createFromParcel(Parcel source) {
            return new PrevQuestionResponse(source);
        }

        @Override
        public PrevQuestionResponse[] newArray(int size) {
            return new PrevQuestionResponse[size];
        }
    };
}
