package com.iqmojo.iq_mojo.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.HashMap;


public class CommonFunctionsUtil {


    final static  String gcm_defaultSenderId = "681782354637";
    final static String mobileNoPattern = "[0-9]+";
    //    final static String emailPattern = "[A-Z0-9a-z\\\\!#$%&'*+-/=?^_`{|}~]+@([A-Za-z0-9-]+\\\\.)+[A-Za-z]{2,4}";
//
    final static String emailPattern = "[A-Z0-9a-z\\._%+-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}";
    //final static String emailPattern ="[A-Z0-9a-z\\\\._%+-]+@([A-Za-z0-9-]+\\\\.)+[A-Za-z]{2,4}";


    public static boolean validateMobileNumber(String mobileNumber) {
        if ((mobileNumber.length() < 10 || mobileNumber.startsWith("0") || mobileNumber.startsWith("1")
                || mobileNumber.startsWith("2") || mobileNumber.startsWith("3") || mobileNumber.startsWith("4")
                || !mobileNumber.matches(mobileNoPattern)))
            return false;
        return true;
    }


//    public static void updateWalletBalance(Context context, WalletInfoResponseModel.Wallets walletMod) {
//        try {
//            WalletInfoResponseModel.Wallet_GNOXG walletGnoxg = walletMod.getWallet_GNOXG();
//            String totalBalance = null;
//            String available_limit = null;
//            if (!TextUtils.isEmpty(walletMod.getTotal_balance())) {
//                totalBalance = walletMod.getTotal_balance();
//            }
//
//            ApplicationClass app = (ApplicationClass) context.getApplicationContext();
//            if (!TextUtils.isEmpty(totalBalance) && !TextUtils.isEmpty(walletGnoxg.getBalance())) {
//                app.setAmount(walletGnoxg.getBalance());
//                app.setTotalAmount(totalBalance);
//
//                app.setRestrictedAmount(String.valueOf(Double.parseDouble((totalBalance)) - Double.parseDouble(walletGnoxg.getBalance())));
//            }
//
//            if (!TextUtils.isEmpty(walletMod.getAvailable_limit())) {
//                available_limit = walletMod.getAvailable_limit();
//                if (!TextUtils.isEmpty(available_limit))
//                    app.setAvailable_limit(available_limit);
//            }
//
//            OxigenPrefrences.getInstance(context).setLong(PrefrenceConstants.LAST_UPDATE_TIME, System.currentTimeMillis());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public static void setUserInfo(Context context, WalletInfoResponseModel.User userInfo) {
//        try {
//            if (userInfo != null) {
//                OxigenPrefrences.getInstance(context).setString(PrefrenceConstants.NAME, userInfo.getName());
//                OxigenPrefrences.getInstance(context).setString(PrefrenceConstants.EMAIL, userInfo.getEmail());
//                OxigenPrefrences.getInstance(context).setString(PrefrenceConstants.LAST_NAME, userInfo.getLast_name());
//                OxigenPrefrences.getInstance(context).setString(PrefrenceConstants.MIDDLE_NAME, userInfo.getMiddle_name());
//                OxigenPrefrences.getInstance(context).setString(PrefrenceConstants.USER_KYC_STATUS, userInfo.getProfile_type());
//                OxigenPrefrences.getInstance(context).setString(PrefrenceConstants.IS_PAYBACK_LINKED, userInfo.getIs_payback_linked());
//            }
//        } catch (Exception e) {
//
//        }
//    }

//    public static void updateUserInfo(Context context, WalletInfoResponseModel.User userInfo) {
//        try {
//            if (userInfo != null) {
//                OxigenPrefrences.getInstance(context).setString(PrefrenceConstants.NAME, userInfo.getName());
//                OxigenPrefrences.getInstance(context).setString(PrefrenceConstants.EMAIL, userInfo.getEmail());
//                OxigenPrefrences.getInstance(context).setString(PrefrenceConstants.LAST_NAME, userInfo.getLast_name());
//            }
//        } catch (Exception e) {
//
//        }
//    }

//    public static String fetchBalance(Activity mActivity, int balanceType) {
//        String toReturn = "";
//
//
//        try {
//            ApplicationClass applicationClass = ((ApplicationClass) mActivity.getApplication());
//            switch (balanceType) {
//                case AppConstants.BALANCE_CONSTANTS.TOTAL_BALANCE:
//                    toReturn = applicationClass.getTotalAmount();
//                    break;
//                case AppConstants.BALANCE_CONSTANTS.GNOX_BALANCE:
//                    toReturn = applicationClass.getAmount();
//                    break;
//                case AppConstants.BALANCE_CONSTANTS.RESTRICTED_BALANCE:
//                    toReturn = applicationClass.getRestrictedAmount();
//                    break;
//
//                case AppConstants.BALANCE_CONSTANTS.AVAILABLE_LIMIT:
//                    toReturn = applicationClass.getAvailable_limit();
//                    break;
//            }
//       /* if(TextUtils.isEmpty(toReturn)){
//            toReturn="0.0";
//        }*/ //not recomended
//            toReturn=convertToDecimal(toReturn);  //convert to decimal of two places
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return toReturn;
//    }

//    private static String convertToDecimal(String toReturn) {
//        if(TextUtils.isEmpty(toReturn))
//            return toReturn;
//
//        try {
//            Double d= Double.parseDouble(toReturn);
//            d= Double.valueOf(Math.round(d*100)/100);  //so that always shows rounded of value //rounded to two decimal places.
//            return String.valueOf(d);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//        return toReturn; //if throws exception, return whatever was going to be written without roundoff
//    }
//
//    public static void loginBeforeProceeding(Activity activity) {
//        Intent intent = new Intent(activity, LoginSignupActivity.class);
//        intent.putExtra(AppConstants.EXTRAS.FROM_SCREEN, AppConstants.EXTRAS.FROM_SCREEN);
//        activity.startActivityForResult(intent, AppConstants.REQUEST_CODE.LOGIN);
//    }

//    public static String getFormatedAmount(String amount) {
//        try {
//            if (!amount.contains(".")) {
//                return amount + ".00";
//            } else {
//                int indexofdot = amount.indexOf(".");
//
//                if (indexofdot + 3 == amount.length())
//                    return amount;
//
//                else if (indexofdot + 2 == amount.length())
//                    return amount + "0";
//                else
//                    return amount.substring(0, indexofdot + 3);
//
//            }
//        } catch (Exception e) {
//            return amount;
//        }
//
//    }

    public static boolean validateEmail(String email) {
        if (!TextUtils.isEmpty(email) && email.matches(emailPattern))
            return true;

        return false;
    }


    public static HashMap<String, String> extractParamsFromURL(final String url) throws URISyntaxException {
        Uri uri = Uri.parse(url);

        HashMap<String, String> map = new HashMap<>();
        for (String paramName : uri.getQueryParameterNames()) {
            if (paramName != null) {
                String paramValue = uri.getQueryParameter(paramName);
                if (paramValue != null) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }


//    public static ClickableSpan getLoginTermsAndConditions(final Activity activity, final TextView txvLoginTandC) {
//        return new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//                UrlManager urlManager = UrlManager.getInstance(activity.getApplicationContext());
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlManager.getLogin_tnc()));
//                activity.startActivity(browserIntent);
//            }
//
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setUnderlineText(false);
//                ds.setColor(ContextCompat.getColor(activity, R.color.blue));
//                txvLoginTandC.setHighlightColor(Color.TRANSPARENT);
//
//            }
//        };
//    }


    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static boolean isAllNumbers(String string) {

        double attempt = 0.0;
        try {
            attempt = Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static void setLengthInputFilter(EditText edtAmount, String maxLimit) {
        if (edtAmount != null) {
            InputFilter lengthFilter = new InputFilter.LengthFilter(TextUtils.isEmpty(maxLimit) ? 4 : maxLimit.trim().length());
            edtAmount.setFilters(new InputFilter[]{lengthFilter});
        }
    }


    public static void validateAmountField(EditText edtAmount) {
        try {
            if (!TextUtils.isEmpty(edtAmount.getText().toString())) {
                Long l = Long.parseLong(edtAmount.getText().toString());
                if (edtAmount.getText().toString().startsWith("0")) {
                    if (l == 0) {
                        edtAmount.setText("");
                    } else {
                        edtAmount.setText(String.valueOf(l));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static String getDeviceId(Context context){
      return (""+ Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
    }

    public static String getDeviceImei(Context context) {

        TelephonyManager mTelephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return (""+mTelephonyManager.getDeviceId());
    }

    public static  String getDeviceName(){
        return ""+Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
    }

    public static  String getVersionName(){
        Field[] fields = Build.VERSION_CODES.class.getFields();
        return ""+fields[Build.VERSION.SDK_INT + 1].getName();
    }

    public static String getNetworkInfo(Context context){

        final ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if( wifi.isAvailable() && wifi.getDetailedState() == NetworkInfo.DetailedState.CONNECTED){
            return "Wifi";
        }
        else if( mobile.isAvailable() && mobile.getDetailedState() == NetworkInfo.DetailedState.CONNECTED ){
            return "Mobile 3G";
        }

        return "No Network";
    }


    public static String getDeviceToken(Context context){
        String token="";

        try {
            InstanceID instanceID = InstanceID.getInstance(context);

            token = instanceID.getToken(gcm_defaultSenderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            Log.i("devicetoken", "GCM Registration Token: " + token);

        }catch (Exception e) {
            Log.d("devicetoken", "Failed to complete token refresh", e);
        }

        return token;
    }



}