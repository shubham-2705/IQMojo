package com.iqmojo.iq_mojo.constants;


public interface ApiConstants {

    boolean isMock = false;


    interface Urls {
        String BASE_URL = "http://108.161.135.146:9397/iqm/api/v1/user/"; // QA
//        String BASE_URL_DEV = "http://103.253.37.61/"; // DEV

//        String BASE_URL = BASE_URL_DEV;


        String REGISTER_USER = BASE_URL + "saveUserReg/";
        String GAME_LIST = BASE_URL + "games";
        String LOGIN = BASE_URL + "login/";
        String RESEND = BASE_URL + "sendOtp/";
        String GET_QUESTION = BASE_URL + "startGame";
        String NEXT_QUESTION=BASE_URL + "nextQ";
        String UPDATE_DEVICE_INFO =  BASE_URL + "updateDeviceInfo";
    }

    interface REQUEST_TYPE
    {
        int REGISTER_USER=0;
        int LOGIN = 1;
        int RESEND = 2;
        int GAME_LIST=3;
        int GET_QUESTION =4 ;
        int NEXT_QUESTION = 5;
        int UPDATE_DEVICE_INFO = 6;
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
