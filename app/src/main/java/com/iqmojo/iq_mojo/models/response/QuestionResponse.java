package com.iqmojo.iq_mojo.models.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shubhamlamba on 17/09/17.
 */

public class QuestionResponse implements Parcelable {

    private QuestionItemResponse question;
    private PrevQuestionResponse preQAns;
    private GameResultResponse gameResult;
    private Long coins;



    public PrevQuestionResponse getPreQAns() {
        return preQAns;
    }

    public void setPreQAns(PrevQuestionResponse preQAns) {
        this.preQAns = preQAns;
    }

    public GameResultResponse getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResultResponse gameResult) {
        this.gameResult = gameResult;
    }

    public Long getCoins() {
        return coins;
    }

    public void setCoins(Long coins) {
        this.coins = coins;
    }

    public QuestionItemResponse getQuestion() {
        return question;
    }

    public void setQuestion(QuestionItemResponse question) {
        this.question = question;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.question);
        dest.writeValue(this.preQAns);
        dest.writeValue(this.gameResult);
        dest.writeValue(this.coins);
    }

    public QuestionResponse() {
    }

    protected QuestionResponse(Parcel in) {
        this.question = in.readParcelable(QuestionItemResponse.class.getClassLoader());
        this.preQAns = in.readParcelable(PrevQuestionResponse.class.getClassLoader());
        this.gameResult = in.readParcelable(GameResultResponse.class.getClassLoader());
        this.coins = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<QuestionResponse> CREATOR = new Parcelable.Creator<QuestionResponse>() {
        @Override
        public QuestionResponse createFromParcel(Parcel source) {
            return new QuestionResponse(source);
        }

        @Override
        public QuestionResponse[] newArray(int size) {
            return new QuestionResponse[size];
        }
    };
}
