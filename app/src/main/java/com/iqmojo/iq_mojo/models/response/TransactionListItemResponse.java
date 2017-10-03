package com.iqmojo.iq_mojo.models.response;

/**
 * Created by shubhamlamba on 03/10/17.
 */

public class TransactionListItemResponse {

    private int txnId;
    private int userId;
    private int gameId;
    private int totalQ;
    private int correct;
    private Long coins;
    private String txType;
    private String txTime;
    private String remarks;

    public int getTxnId() {
        return txnId;
    }

    public void setTxnId(int txnId) {
        this.txnId = txnId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
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

    public Long getCoins() {
        return coins;
    }

    public void setCoins(Long coins) {
        this.coins = coins;
    }

    public String getTxType() {
        return txType;
    }

    public void setTxType(String txType) {
        this.txType = txType;
    }

    public String getTxTime() {
        return txTime;
    }

    public void setTxTime(String txTime) {
        this.txTime = txTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
