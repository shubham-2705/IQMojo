package com.iqmojo.iq_mojo.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.iqmojo.R;
import com.iqmojo.base.ui.activity.BaseActivity;
import com.iqmojo.iq_mojo.ui.HomeActivity;

/**
 * Created by shubhamlamba on 18/08/17.
 */

public class SplashActivity extends BaseActivity {

    private static final long SPLASH_TIMER = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }

        }, SPLASH_TIMER);

    }


}
