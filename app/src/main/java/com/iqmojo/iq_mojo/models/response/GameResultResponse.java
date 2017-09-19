package com.iqmojo.iq_mojo.models.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shubhamlamba on 19/09/17.
 */

public class GameResultResponse implements Parcelable {

    private int gameId;
    private int result;
    private int reward;
    private int totalQ;
    private int correct;
    private int wrong;
    private String  respText;


    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getTotalQ() {
        return totalQ;
    }

    public void setTotalQ(int totalQ) {
        this.totalQ = totalQ;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public String getRespText() {
        return respText;
    }

    public void setRespText(String respText) {
        this.respText = respText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.gameId);
        dest.writeInt(this.result);
        dest.writeInt(this.reward);
        dest.writeInt(this.totalQ);
        dest.writeInt(this.correct);
        dest.writeInt(this.wrong);
        dest.writeString(this.respText);
    }

    public GameResultResponse() {
    }

    protected GameResultResponse(Parcel in) {
        this.gameId = in.readInt();
        this.result = in.readInt();
        this.reward = in.readInt();
        this.totalQ = in.readInt();
        this.correct = in.readInt();
        this.wrong = in.readInt();
        this.respText = in.readString();
    }

    public static final Parcelable.Creator<GameResultResponse> CREATOR = new Parcelable.Creator<GameResultResponse>() {
        @Override
        public GameResultResponse createFromParcel(Parcel source) {
            return new GameResultResponse(source);
        }

        @Override
        public GameResultResponse[] newArray(int size) {
            return new GameResultResponse[size];
        }
    };
}
