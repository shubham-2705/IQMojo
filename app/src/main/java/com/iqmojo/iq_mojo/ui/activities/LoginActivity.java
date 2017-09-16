package com.iqmojo.iq_mojo.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.iqmojo.R;
import com.iqmojo.base.ui.activity.BaseActivity;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.iqmojo.base.utils.ShowLog;
import com.iqmojo.base.utils.ToastUtil;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.iqmojo.iq_mojo.utils.CommonFunctionsUtil;
import com.iqmojo.iq_mojo.utils.FontHelper;
import com.iqmojo.iq_mojo.utils.GCMHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by himanshu on 19/8/17.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {

    Toolbar mToolbar;
    RelativeLayout RlFbLogin, RlsGoogleLogin;
    CallbackManager callbackManager;
    Context context;
    TextView txvgoogle,txvfb;
    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 1000;
    String strEmail="", strId="", strLocation="", gcmRegID="", fbprofilepicurl="", fb_firstname="", fb_lastname="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txvgoogle= (TextView)findViewById(R.id.txvgoogle);
        txvfb= (TextView)findViewById(R.id.txvfb);
        TextView txvOr= (TextView)findViewById(R.id.txvOr);
//        FontHelper.applyFont(this,txvfb,"fonts/medium.OTF");
//        FontHelper.applyFont(this,txvgoogle,"fonts/medium.OTF");
//        FontHelper.applyFont(this,txvOr,"fonts/medium.OTF");

        context = LoginActivity.this;
        callbackManager = CallbackManager.Factory.create();
        RlFbLogin = (RelativeLayout) findViewById(R.id.rlyFb);
        RlsGoogleLogin = (RelativeLayout) findViewById(R.id.rlyGoogle);
        RlFbLogin.setOnClickListener(this);
        RlsGoogleLogin.setOnClickListener(this);
        txvgoogle.setOnClickListener(this);
        txvfb.setOnClickListener(this);

        // google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.google_signin_server_client_id))
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this , this )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        GetGCM();
        // fb sign in
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Toast.makeText(context, "Successfully logged in", Toast.LENGTH_SHORT).show();
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {

                                            Log.i("response --> ", ""+ response);


                                            if(response.getJSONObject().getString("id")!=null && !TextUtils.isEmpty(response.getJSONObject().getString("id"))){

                                                strId = response.getJSONObject().getString("id");
                                            }
                                            if(response.getJSONObject().getString("email")!=null && !TextUtils.isEmpty(response.getJSONObject().getString("email"))){

                                                strEmail = response.getJSONObject().getString("email");
                                            }
                                            if(response.getJSONObject().has("location") && !TextUtils.isEmpty(response.getJSONObject().getJSONObject("location").getJSONObject("location").getString("country"))){

                                                strLocation = response.getJSONObject().getJSONObject("location").getJSONObject("location").getString("country");
                                            }else strLocation = "";
                                            if(!TextUtils.isEmpty(response.getJSONObject().getString("first_name"))){

                                                fb_firstname = response.getJSONObject().getString("first_name");
                                            }
                                            if(!TextUtils.isEmpty(response.getJSONObject().getString("last_name"))){

                                                fb_lastname = response.getJSONObject().getString("last_name");
                                            }
                                            if (response != null && response.getError() == null &&
                                                    response.getJSONObject() != null) {
                                                fbprofilepicurl = response.getJSONObject().getJSONObject("picture")
                                                        .getJSONObject("data").getString("url");
                                            }else fbprofilepicurl = "";

                                            ShowLog.v("fbprofilepicurl---->>>", fbprofilepicurl);

                                            IqMojoPrefrences.getInstance(context).setString(AppConstants.KEY_GCM_ID, gcmRegID);
                                            IqMojoPrefrences.getInstance(context).setString(AppConstants.KEY_DISPLAY_PIC, fbprofilepicurl);
                                            IqMojoPrefrences.getInstance(context).setString(AppConstants.KEY_DISPLAY_NAME, fb_firstname + " "+ fb_lastname);

                                            Intent i = new Intent(context, EnterMobileActivity.class);
                                            i.putExtra(AppConstants.EMAIL_ID, strEmail);
                                            i.putExtra(AppConstants.LOCATION, strLocation);
                                            i.putExtra(AppConstants.ID, strId);
                                            i.putExtra(AppConstants.DEVICE_TOKEN, gcmRegID);
                                            startActivity(i);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location{location}, picture.width(140).height(130)");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }
                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.rlyFb:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_location"));
                break;
            case R.id.rlyGoogle:
                signIn();
                break;
            case R.id.txvfb:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_location"));
                break;
            case R.id.txvgoogle:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShowLog.d(TAG, "resultCode:" + resultCode);
        ShowLog.d(TAG, "data:" + data.getData());


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            ShowLog.d(TAG, "resultCode:" + resultCode);
            handleSignInResult(result);
        }
        else
        {
            // for fb
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    // for google sign in (google client)
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        ShowLog.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        ToastUtil.showLongToast(this,"handleSignInResult:" + result.isSuccess());
        ShowLog.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            ShowLog.d(TAG, "handleSignInResult:" + acct.getDisplayName());

            Uri personPhoto = acct.getPhotoUrl();
            IqMojoPrefrences.getInstance(context).setString(AppConstants.KEY_DISPLAY_NAME, acct.getDisplayName());
            IqMojoPrefrences.getInstance(context).setString(AppConstants.KEY_DISPLAY_PIC, personPhoto.toString());


            Intent i = new Intent(context, EnterMobileActivity.class);
            i.putExtra(AppConstants.EMAIL_ID,acct.getEmail());
            i.putExtra(AppConstants.ID,acct.getId());
            i.putExtra(AppConstants.GOOGLE_TOKEN, acct.getIdToken());
            startActivity(i);

        } else {
            // Signed out, show unauthenticated UI.
        }
    }

    private void GetGCM() {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    GCMHelper  gcmRegistrationHelper = new GCMHelper(
                            getApplicationContext());
                    gcmRegID = gcmRegistrationHelper.GCMRegister("681782354637");

                } catch (Exception bug) {
                    bug.printStackTrace();
                }

            }
        });

        thread.start();
    }


}
