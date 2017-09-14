package com.iqmojo.base.ui.fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.View;



public abstract class BasePagerFragment extends BaseFragment {

    private boolean alreadyFocused;

    public void removeView(View v){
        v.setVisibility(View.GONE);
    }
    public void showView(View v){
        v.setVisibility(View.VISIBLE);
    }

    public void focusFragment() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getBaseActivity() != null)
                    getBaseActivity().hideDialogKeypad();
                if (!alreadyFocused) {
                    onFragmentFocused();
                    alreadyFocused = true;
                } else {
                    onFragmentReFocused();
                }
            }
        }, 300);
    }

    public abstract void onFragmentFocused();

    public void onFragmentReFocused() {
        // do nothing
    }
    public void onLostFocus(){
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                onFragmentLostFocus(); //so that oncreate is created
            }
        }, 100);
    }
    public void onFragmentLostFocus(){

    }

    public String formatPhoneNumber(String s) {
        String ans="";
        try {
            for(int i=0;i<s.length();i++){
                char c=s.charAt(i);
                if(Character.isDigit(c)){
                    ans = ans + c;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }


}
