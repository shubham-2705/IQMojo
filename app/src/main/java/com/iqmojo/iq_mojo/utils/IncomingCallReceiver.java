package com.iqmojo.iq_mojo.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.iqmojo.base.utils.ShowLog;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.ui.activities.HomeActivity;

/**
 * Created by shubhamlamba on 09/10/17.
 */

public class IncomingCallReceiver extends BroadcastReceiver {

    public IncomingCallReceiver() {
        super();
    }

    public void onReceive(Context context, Intent intent) {

        try {
//            // TELEPHONY MANAGER class object to register one listner
//            TelephonyManager tmgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//
//            //Create Listner
//            MyPhoneStateListener PhoneListener = new MyPhoneStateListener();
//
//            // Register listener for LISTEN_CALL_STATE
//            tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

            System.out.println("Receiver start");
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
//                Toast.makeText(context,"Incoming call state -"+incomingNumber,Toast.LENGTH_SHORT).show();
//                Intent intent1 = new Intent(context, HomeActivity.class);
//                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(intent1);
                Intent myIntent = new Intent(AppConstants.PHONE_CALL);
                LocalBroadcastManager.getInstance(context).sendBroadcast(myIntent);
            }
            if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))){
//                Toast.makeText(context,"Received State",Toast.LENGTH_SHORT).show();
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
//                Toast.makeText(context,"Idle State",Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            ShowLog.e("Phone Receive Error", " " + e);
        }

    }

//    private class MyPhoneStateListener extends PhoneStateListener {
//
//        public void onCallStateChanged(int state, String incomingNumber) {
//
//            if (state == 1) {
//                ShowLog.d("MyPhoneListener",state+"   incoming no:"+incomingNumber);
//                String msg = "New Phone Call Event. Incomming Number : "+incomingNumber;
//            }
//        }
//    }
}
