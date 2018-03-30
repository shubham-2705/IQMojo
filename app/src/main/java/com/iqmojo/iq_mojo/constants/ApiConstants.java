package com.iqmojo.iq_mojo.constants;


public interface ApiConstants {

    boolean isMock = false;


    interface Urls {
        String BASE_URL = "http://iqmojo.club/iqm"; // QA

        String GAME_BASE_URL = BASE_URL + "/api/v1/user/";
        String CHALLENGE_BASE_URL = BASE_URL + "/api/v1/challenge/"; // QA
        String CONTEST_BASE_URL = BASE_URL + "/api/v1/contest/"; // QA
//        http://108.161.135.146:9397/iqm/view/tnc.html
//        String BASE_URL_DEV = "http://103.253.37.61/"; // DEV

//        String BASE_URL = BASE_URL_DEV;


        String REGISTER_USER = GAME_BASE_URL + "saveUserReg/";
        String GAME_LIST = GAME_BASE_URL + "games";
        String LOGIN = GAME_BASE_URL + "login/";
        String RESEND = GAME_BASE_URL + "sendOtp/";
        String START_GAME = GAME_BASE_URL + "startGame";
        String GAME_NEXT_QUESTION = GAME_BASE_URL + "nextQ";
        String UPDATE_DEVICE_INFO = GAME_BASE_URL + "updateDeviceInfo";
        String TXN = GAME_BASE_URL + "tx";
        String CREATE_CHALLENGE = CHALLENGE_BASE_URL + "create";
        String CHALLENGE_LIST = CHALLENGE_BASE_URL + "list";
        String CHALLENGE_START = CHALLENGE_BASE_URL + "start";
        String CHALLENGE_STOP = CHALLENGE_BASE_URL + "stop";
        String CONTEST_LIST = CONTEST_BASE_URL + "list";
        String REDEEM = GAME_BASE_URL + "validateRedeem";
        String STORE_LIST = GAME_BASE_URL + "store";
        String TNC =  "http://iqmojo.club/iqm/view/tnc.html";
        String PRIVACY_POLICY = "http://iqmojo.club/iqm/view/privacy.html";
    }

    interface REQUEST_TYPE {
        int REGISTER_USER = 0;
        int LOGIN = 1;
        int RESEND = 2;
        int GAME_LIST = 3;
        int START_GAME = 4;
        int GAME_NEXT_QUESTION = 5;
        int UPDATE_DEVICE_INFO = 6;
        int TXN = 7;
        int CREATE_CHALLENGE = 8;
        int CHALLENGE_LIST = 9;
        int CHALLENGE_START = 10;
        int CHALLENGE_STOP = 11;
        int CONTEST_LIST = 12;
        int START_CONTEST = 13;
        int REDEEM = 14;
        int STORE_LIST = 15;
    }


    interface Values {

        interface ResponseCodes {

//            String SUCCESS="2000";
//            String FAILURE="4004";
//            String SERVER_ERROR="5000";

        }

        interface UserType {//based on response code
//            String NEW_USER = "1";
//            String ALREADY_EXISTING_USER = "0";
        }

    }


}
