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

public class EnterMobileActivity extends BaseActivity implements View.OnClickListener {

    private EditText edtMobile;
    private TextView txvGetOtp;
    private ProgressBar pbLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mobile);

        getView();


        edtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0)
                {
                    edtMobile.setLetterSpacing(0.5f);
                }
                else
                {
                    edtMobile.setLetterSpacing(0f);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void getView()
    {
        edtMobile=(EditText)findViewById(R.id.edtMobile);
        txvGetOtp=(TextView) findViewById(R.id.txvGetOtp);
        pbLoading=(ProgressBar) findViewById(R.id.pbLoading);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        switch (id)
        {
            case R.id.txvGetOtp:
                break;

        }
    }
}
