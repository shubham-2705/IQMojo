package com.iqmojo.base.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.iqmojo.base.listeners.UpdateGsonListener;
import com.iqmojo.base.listeners.onUpdateViewListener;
import com.iqmojo.base.model.ParentRequestModel;
import com.iqmojo.base.utils.ReadFileUtil;
import com.iqmojo.base.utils.ShowLog;

import java.lang.ref.WeakReference;


public class NetworkEngine {

    private static final String TAG = NetworkEngine.class.getSimpleName();
    private static NetworkEngine instance;
    public static final int NOT_SET = -1;
    private int httpMethodType = NOT_SET;
    private static Context mContext;
    private String url;
    private Class clasz;
    private onUpdateViewListener listener;
    private ParentRequestModel parentRequestModel;
    private int requestType;
    private boolean encryptionRequired = false;
    private REQUEST_FLOW requestFlow = REQUEST_FLOW.NORMAL;

    private NetworkEngine() {

    }

    public static NetworkEngine with (Context context) {
        instance = new NetworkEngine();
        mContext = context.getApplicationContext();
        return instance;
    }

    public NetworkEngine setUrl(String url) {
        this.url = url;
        return instance;
    }

    public NetworkEngine setHttpMethodType(int httpMethodType) {
        this.httpMethodType = httpMethodType;
        return instance;
    }

    public NetworkEngine setClassType(Class clasz) {
        this.clasz = clasz;
        return instance;
    }

    public NetworkEngine setUpdateViewListener(onUpdateViewListener listener) {
        this.listener = listener;
        return instance;
    }

    public NetworkEngine setRequestModel(ParentRequestModel parentRequestModel) {
        this.parentRequestModel = parentRequestModel;
        return instance;
    }


    public NetworkEngine setRequestType(int requestType) {
        this.requestType = requestType;
        return instance;
    }


    public NetworkEngine setRequestEncryption(boolean required) {
        this.encryptionRequired = required;
        return instance;
    }

    public NetworkEngine setRequestFlow(REQUEST_FLOW requestFlow) {
        this.requestFlow = requestFlow;
        return instance;
    }

    public void build() {


        if (requestFlow != REQUEST_FLOW.NORMAL) {
//            String responseJson = ReadFileUtil.readFromfile(mContext, serviceType);
//            listener.updateView(new Gson().fromJson(responseJson, clasz), !TextUtils.isEmpty(responseJson), requestType);
        } else {
            String requestBody = "";

            ShowLog.i("RequestBody=======", requestBody);
            ShowLog.i("URL=======", url);
            VolleyGsonRequest volleyGsonRequest = VolleyGsonRequest.processRequest(mContext, httpMethodType,url, new UpdateGsonListener<Object>(mContext, listener, requestType), clasz, requestBody, encryptionRequired);
            VolleyManager.getInstance(mContext).addToRequestQueue(volleyGsonRequest, ""+requestType);
        }
    }

    public enum REQUEST_FLOW {
        NORMAL,
        MOCK_SUCCESS,
        MOCK_FAILURE
    }
}
