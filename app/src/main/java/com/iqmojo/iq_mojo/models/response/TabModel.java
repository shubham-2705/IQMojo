package com.iqmojo.iq_mojo.models.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shubhamlamba on 11/21/17.
 */

public class TabModel implements Parcelable {

    private int tabId;
    private String displayName;
    private String tabAction;
    private String actionURL;
    private int status;
    private int priority;


    public int getTabId() {
        return tabId;
    }

    public void setTabId(int tabId) {
        this.tabId = tabId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTabAction() {
        return tabAction;
    }

    public void setTabAction(String tabAction) {
        this.tabAction = tabAction;
    }

    public String getActionURL() {
        return actionURL;
    }

    public void setActionURL(String actionURL) {
        this.actionURL = actionURL;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.tabId);
        dest.writeString(this.displayName);
        dest.writeString(this.tabAction);
        dest.writeString(this.actionURL);
        dest.writeInt(this.status);
        dest.writeInt(this.priority);
    }

    public TabModel() {
    }

    protected TabModel(Parcel in) {
        this.tabId = in.readInt();
        this.displayName = in.readString();
        this.tabAction = in.readString();
        this.actionURL = in.readString();
        this.status = in.readInt();
        this.priority = in.readInt();
    }

    public static final Parcelable.Creator<TabModel> CREATOR = new Parcelable.Creator<TabModel>() {
        @Override
        public TabModel createFromParcel(Parcel source) {
            return new TabModel(source);
        }

        @Override
        public TabModel[] newArray(int size) {
            return new TabModel[size];
        }
    };
}
