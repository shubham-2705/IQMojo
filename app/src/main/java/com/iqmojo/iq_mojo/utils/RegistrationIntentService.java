package com.iqmojo.iq_mojo.utils;

import android.app.IntentService;
import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceId;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class RegistrationIntentService extends IntentService {

    public RegistrationIntentService() {
        super("RegistrationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String token = FirebaseInstanceId.getInstance().getToken();
            IqMojoPrefrences.getInstance(getApplicationContext()).setString(AppConstants.KEY_FCM_ID, token);
        }
    }
}
