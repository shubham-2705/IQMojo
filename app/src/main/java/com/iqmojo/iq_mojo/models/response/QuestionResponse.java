package com.iqmojo.iq_mojo.models.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by shubhamlamba on 17/09/17.
 */

public class QuestionResponse implements Parcelable {

    private QuestionItemResponse question;
    private PrevQuestionResponse preQAns;
    private GameResultResponse gameResult;
    private Long coins;
    private ArrayList<GameItemResponse> games;




    public ArrayList<GameItemResponse> getGames() {
        return games;
    }

    public void setGames(ArrayList<GameItemResponse> games) {
        this.games = games;
    }

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


    public QuestionResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.question, flags);
        dest.writeParcelable(this.preQAns, flags);
        dest.writeParcelable(this.gameResult, flags);
        dest.writeValue(this.coins);
        dest.writeTypedList(this.games);
    }

    protected QuestionResponse(Parcel in) {
        this.question = in.readParcelable(QuestionItemResponse.class.getClassLoader());
        this.preQAns = in.readParcelable(PrevQuestionResponse.class.getClassLoader());
        this.gameResult = in.readParcelable(GameResultResponse.class.getClassLoader());
        this.coins = (Long) in.readValue(Long.class.getClassLoader());
        this.games = in.createTypedArrayList(GameItemResponse.CREATOR);
    }

    public static final Creator<QuestionResponse> CREATOR = new Creator<QuestionResponse>() {
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
