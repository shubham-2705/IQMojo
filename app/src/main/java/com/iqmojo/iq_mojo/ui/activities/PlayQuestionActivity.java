package com.iqmojo.iq_mojo.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.iqmojo.R;
import com.iqmojo.base.listeners.onUpdateViewListener;
import com.iqmojo.base.network.NetworkEngine;
import com.iqmojo.base.ui.activity.BaseActivity;
import com.iqmojo.base.utils.ConnectivityUtils;
import com.iqmojo.base.utils.ShowLog;
import com.iqmojo.base.utils.ToastUtil;
import com.iqmojo.iq_mojo.constants.ApiConstants;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.models.response.GameItemResponse;
import com.iqmojo.iq_mojo.models.response.LoginResponse;
import com.iqmojo.iq_mojo.models.response.QuestionResponse;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.iqmojo.iq_mojo.utils.CommonFunctionsUtil;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import io.github.krtkush.lineartimer.LinearTimer;
import io.github.krtkush.lineartimer.LinearTimerStates;
import io.github.krtkush.lineartimer.LinearTimerView;

public class PlayQuestionActivity extends BaseActivity implements LinearTimer.TimerListener, onUpdateViewListener {

    LinearTimer linearTimer;
    TextView txvTime, txvQuestion, txvTotal, txvAttempted, txvCorrect;
    GameItemResponse gameItemResponse;
    int gameid = 0, timer = 0;
    LinearTimerView linearTimerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_question);


        linearTimerView = (LinearTimerView) findViewById(R.id.linearTimer);
        txvTime = (TextView) findViewById(R.id.txvTime);
        txvQuestion = (TextView) findViewById(R.id.txvQuestion);
        txvTotal = (TextView) findViewById(R.id.txvTotal);
        txvAttempted = (TextView) findViewById(R.id.txvAttempted);
        txvCorrect = (TextView) findViewById(R.id.txvCorrect);
        setupToolbar();

        if (getIntent().getParcelableExtra(AppConstants.GAME_ITEM_OBJECT) != null) {
            gameItemResponse = getIntent().getParcelableExtra(AppConstants.GAME_ITEM_OBJECT);
            gameid = gameItemResponse.getGameId();
            txvTotal.setText((""+gameItemResponse.getTotalQ()));
            txvAttempted.setText("00");
            txvCorrect.setText("00");
        }

        hitApiRequest(ApiConstants.REQUEST_TYPE.GET_QUESTION);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setLogo(R.drawable.iqmojo_toolbar);

        TextView txvCoins = (TextView) mToolbar.findViewById(R.id.txvCoins);
        txvCoins.setText(("" + new DecimalFormat("##,##,##0").format(IqMojoPrefrences.getInstance(this).getLong(AppConstants.KEY_COINS))));

    }

    private void hitApiRequest(int reqType) {
        try {
            // register
            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                ToastUtil.showShortToast(this, getString(R.string.error_network_not_available));
                return;
            }

            Class clasz = null;
            String url = "";

            switch (reqType) {
                case ApiConstants.REQUEST_TYPE.GET_QUESTION:

                    clasz = QuestionResponse.class;

                    url = ApiConstants.Urls.GET_QUESTION + "?" + "userId=" + IqMojoPrefrences.getInstance(PlayQuestionActivity.this).getInteger(AppConstants.KEY_USER_ID) + "&gameId=" + gameid;

                    url = url.replace(" ", "%20");
                    Log.v("url-->> ", url);
                    break;


                default:
                    break;

            }

            showProgressdialog("Starting...be ready!!");
            NetworkEngine.with(this).setClassType(clasz).setUrl(url).setRequestType(reqType).setHttpMethodType(Request.Method.GET).setUpdateViewListener(this).build();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateView(Object responseObject, boolean isSuccess, int reqType) {
        try {
            hideProgressDialog();
            if (!isSuccess) {
//                buildAndComm.showOkDialog(UpiCreateVpaActivity.this, (String) responseObject);
            } else {
                switch (reqType) {
                    case ApiConstants.REQUEST_TYPE.GET_QUESTION:
                        QuestionResponse questionResponse = (QuestionResponse) responseObject;
                        try {

                            timer = questionResponse.getQuestion().getAllowTime();
                            txvQuestion.setText(questionResponse.getQuestion().getqText());


                            linearTimer = new LinearTimer.Builder()
                                    .linearTimerView(linearTimerView)
                                    .duration(timer * 1000)
                                    .timerListener(this)
                                    .getCountUpdate(LinearTimer.COUNT_DOWN_TIMER, 1000)
                                    .build();
                            txvTime.setText(""+timer);

                            linearTimer.startTimer();
                        } catch (Exception e) {
                            hideProgressDialog();
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void animationComplete() {

    }

    @Override
    public void timerTick(long tickUpdateInMillis) {

        ShowLog.i("Time left", String.valueOf(tickUpdateInMillis));

        txvTime.setText("" + (tickUpdateInMillis / 1000));

    }

    @Override
    public void onTimerReset() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!(linearTimer.getState()== LinearTimerStates.FINISHED))
        {
            linearTimer.pauseTimer();
            linearTimer.resetTimer();
        }

    }
}
