package com.iqmojo.iq_mojo.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iqmojo.R;
import com.iqmojo.base.ui.activity.BaseActivity;

public class EnterOtpActivity extends BaseActivity implements View.OnClickListener {

    private EditText edtOTP;
    private TextView txvDone,txvResend;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        getView();

        edtOTP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0)
                {
                    edtOTP.setLetterSpacing(0.5f);
                }
                else
                {
                    edtOTP.setLetterSpacing(0f);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    public void getView()
    {
        edtOTP=(EditText)findViewById(R.id.edtOTP);
        txvDone=(TextView) findViewById(R.id.txvDone);
        txvResend=(TextView) findViewById(R.id.txvResend);
        pbLoading=(ProgressBar) findViewById(R.id.pbLoading);
    }

    @Override
    public void onClick(View v) {

        int id=v.getId();

        switch (id)
        {
            case R.id.txvDone:
                break;
            case R.id.txvResend:
                break;

        }
    }
}
