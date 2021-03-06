package com.iqmojo.iq_mojo.constants;


public interface AppConstants {

    int SPLASH_TIMEOUT = 3000;


//    Bundle Constants

    String DEVICES_EXTRAS = "devices_extras";
    String PRODUCT_DETAIL = "product_detail";

    //Intent keys
    String LOCATION = "fb_location";
    String EMAIL_ID = "email_id";
    String GOOGLE_ID = "google_id";
    String FB_ID = "fb_id";
    String MOBILE = "mobile";
    String DEVICE_TOKEN = "Device_Token";
    String SCREEN_NO = "screen_no";
    String GOOGLE_TOKEN = "google_token";
    String GAME_ITEM_OBJECT = "game_object";
    String CHALLENGE_ITEM_OBJECT = "challenge_object";
    String CONTEST_ITEM_OBJECT = "contest_object";
    String GAME_RESULT = "game_result";
    String BONUS_GAMES = "bonus_games";
    String FCM_ID = "fcm_id";
    String IS_RESUME = "is_resume";
    String PHONE_CALL = "phone_call";
    String EXTRA_PLAY_MODE = "play_mode";

    //Toolbar Change
    String ADD_COINS = "coins_add_redeem";
    //Toolbar ChangeCoins

//    int DEFAULT = 0;
//    int HISTORY = 1;
//    int FILTER = 2;
//    int CROSS = 3;
//    int USER = 4;


    //Menu


    String WEBVIEW_URL = "webview_url";
    String WEBVIEW_HEADER = "webview_header";

    interface PLAY_MODE {
        int GAME = 1;
        int CHALLENGE = 2;
        int CONTEST = 3;
    }


    // Home tabs
    interface HomeTabKeys {
        int HOME = 0;
        int WINNER = 1;
        int CONTEST = 2;
        int FAQ = 3;
    }


    // My Account Dashboard Tabs
    interface MyAccountTabKeys {

        int MY_POINTS = 0;
        int MY_TRANSACTIONS = 1;
    }


    // Hamburger Menu options
    int My_Points = 0;
    int My_Profile = 1;
    int Transactions = 2;
    int Referral = 3;
    int Terms_And_Conditions = 4;
    int Privacy_Policy = 5;
    int Contact_Us = 6;

    //Shared Preferences Keys
    String KEY_COINS = "coins";
    String KEY_USER_ID = "user_id";
    String KEY_NEW_USER = "new_user";
    String KEY_EMAIL_ID = "email_id";
    String KEY_OTP = "otp";
    String KEY_GCM_ID = "gcm_id";
    String KEY_GOOGLE_NAME = "google_name";
    String KEY_GOOGLE_PIC = "google_pic";
    String KEY_FB_PIC = "fb_pic";
    String KEY_FB_NAME = "fb_name";
    String KEY_MOBILE = "mobile";
    String KEY_TAB_LIST = "tab_list";
    String KEY_FCM_ID = "fcm_id";
    String KEY_FCM_ID_UPDATED = "fcm_updated";
}
