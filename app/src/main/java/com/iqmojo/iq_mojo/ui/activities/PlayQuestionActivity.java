package com.iqmojo.iq_mojo.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

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
import com.iqmojo.iq_mojo.models.response.GameResultResponse;
import com.iqmojo.iq_mojo.models.response.LoginResponse;
import com.iqmojo.iq_mojo.models.response.QuestionResponse;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.iqmojo.iq_mojo.utils.CommonFunctionsUtil;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Dialog;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.github.krtkush.lineartimer.LinearTimer;
import io.github.krtkush.lineartimer.LinearTimerStates;
import io.github.krtkush.lineartimer.LinearTimerView;

public class PlayQuestionActivity extends BaseActivity implements LinearTimer.TimerListener, onUpdateViewListener {

    LinearTimer linearTimer;
    TextView txvTime, txvQuestion, txvTotal, txvAttempted, txvCorrect;
    GameItemResponse gameItemResponse;
    int gameid = 0, timer = 0, quesId = 0, quesLevel = 0, ansValue = 0, attemptedCount = 1, correctCount = 0, play_mode = 0;
    LinearTimerView linearTimerView;
    ImageView imvQuestionImage, imvPause;
    CardView cardBackground;
    LinearLayout llyOptions;
    HashMap<String, Integer> valuesMap;
    HashMap<Integer, String> displayMap;
    HashMap<Integer, TextView> textviewMap = new HashMap<>();
    private boolean isResume;

    private int resumeGame = 0;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_question);


        try {
            mReceiver = new BatteryBroadcastReceiver();
            linearTimerView = (LinearTimerView) findViewById(R.id.linearTimer);
            txvTime = (TextView) findViewById(R.id.txvTime);

            txvTotal = (TextView) findViewById(R.id.txvTotal);
            txvAttempted = (TextView) findViewById(R.id.txvAttempted);
            txvCorrect = (TextView) findViewById(R.id.txvCorrect);
            imvQuestionImage = (ImageView) findViewById(R.id.imvQuestionImage);
            imvPause = (ImageView) findViewById(R.id.imvPause);
            cardBackground = (CardView) findViewById(R.id.cardBackground);
            llyOptions = (LinearLayout) findViewById(R.id.llyOptions);
            setupToolbar();

            play_mode = getIntent().getIntExtra(AppConstants.EXTRA_PLAY_MODE, 0);

            if (getIntent().hasExtra(AppConstants.GAME_ITEM_OBJECT) && getIntent().getParcelableExtra(AppConstants.GAME_ITEM_OBJECT) != null) {
                gameItemResponse = getIntent().getParcelableExtra(AppConstants.GAME_ITEM_OBJECT);
                gameid = gameItemResponse.getGameId();
                txvTotal.setText(("" + gameItemResponse.getTotalQ()));
                txvAttempted.setText("" + attemptedCount);
                txvCorrect.setText("" + correctCount);
            }
            if (getIntent().hasExtra(AppConstants.CHALLENGE_ITEM_OBJECT) &&  getIntent().getParcelableExtra(AppConstants.CHALLENGE_ITEM_OBJECT) != null) {

            }
            if (getIntent().hasExtra(AppConstants.CONTEST_ITEM_OBJECT) && getIntent().getParcelableExtra(AppConstants.CONTEST_ITEM_OBJECT) != null) {

            }

            if (getIntent().hasExtra(AppConstants.IS_RESUME))
                isResume = getIntent().getBooleanExtra(AppConstants.IS_RESUME, false);

            if (isResume)
                resumeGame = 1;

            imvPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // show dialog for pause button
                    showPauseDialog();
                }
            });

            if (play_mode == AppConstants.PLAY_MODE.GAME) {
                hitApiRequest(ApiConstants.REQUEST_TYPE.START_GAME, 0);
            } else if (play_mode == AppConstants.PLAY_MODE.CHALLENGE) {

            } else if (play_mode == AppConstants.PLAY_MODE.CONTEST) {
                hitApiRequest(ApiConstants.REQUEST_TYPE.START_CONTEST, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void updateCorrectVariable(int correctCount) {
        try {
            txvCorrect.setText("" + correctCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateAttemptVariable(int attemptedCount) {
        try {
            txvAttempted.setText("" + attemptedCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(AppConstants.PHONE_CALL));
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent intent1 = new Intent(PlayQuestionActivity.this, HomeActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
        }

    };

    private class BatteryBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            ShowLog.d("---battery", "   " + level);
            if (level <= 10) {
                ToastUtil.showShortToast(PlayQuestionActivity.this, "Please connect your device to power supply.");
                if (level <= 5) {
                    Intent intent1 = new Intent(context, HomeActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent1);
                    ToastUtil.showShortToast(PlayQuestionActivity.this, "Game paused due to low battery.");
                }

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
//            finish(); // close this activity and return to preview activity (if there is any)
            ToastUtil.showShortToast(this, "Sorry, you cannot go back.");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        ToastUtil.showShortToast(this, "Sorry, you cannot go back.");

//        Intent intent = new Intent(this, HomeActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        finish();
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

    private void updateToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        TextView txvCoins = (TextView) mToolbar.findViewById(R.id.txvCoins);
        txvCoins.setText(("" + new DecimalFormat("##,##,##0").format(IqMojoPrefrences.getInstance(this).getLong(AppConstants.KEY_COINS))));
    }

    private void hitApiRequest(int reqType, int ansValue) {
        try {
            // register
            if (!ConnectivityUtils.isNetworkEnabled(this)) {
                ToastUtil.showShortToast(this, getString(R.string.error_network_not_available));
                return;
            }

            Class clasz = null;
            String url = "";

            switch (reqType) {
                case ApiConstants.REQUEST_TYPE.START_GAME:

                    clasz = QuestionResponse.class;

                    url = ApiConstants.Urls.START_GAME + "?" + "userId=" + IqMojoPrefrences.getInstance(PlayQuestionActivity.this).getInteger(AppConstants.KEY_USER_ID) + "&gameId=" + gameid + "&resume=" + resumeGame;

                    url = url.replace(" ", "%20");
                    Log.v("url-->> ", url);
                    showProgressdialog("Starting...be ready!!");
                    break;


                case ApiConstants.REQUEST_TYPE.GAME_NEXT_QUESTION:

                    clasz = QuestionResponse.class;

                    url = ApiConstants.Urls.GAME_NEXT_QUESTION + "?" + "userId=" + IqMojoPrefrences.getInstance(PlayQuestionActivity.this).getInteger(AppConstants.KEY_USER_ID) + "&gameId=" + gameid + "&preQId=" + quesId + "&ansValue=" + ansValue;

                    url = url.replace(" ", "%20");
                    Log.v("url-->> ", url);
                    showProgressdialog("Validating..!!");
                    break;

                default:
                    break;

            }


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
            } else {
                switch (reqType) {
                    case ApiConstants.REQUEST_TYPE.START_GAME:
                        QuestionResponse questionResponse = (QuestionResponse) responseObject;
                        try {

                            IqMojoPrefrences.getInstance(this).setLong(AppConstants.KEY_COINS, questionResponse.getCoins());
                            updateToolbar();

                            // manipulation for displying options
//                            String str = "1#True^2#False^3#None";
                            String[] options = questionResponse.getQuestion().getAnsOptions().split("\\^");
//                            String[] options = str.split("\\^");
                            ShowLog.d("---length", "" + options.length);
                            valuesMap = new HashMap<>();
                            displayMap = new HashMap<>();

                            for (String option : options) {
                                ShowLog.d("---string", "" + option);
                                String[] singleOption = option.split("\\#");
                                valuesMap.put(singleOption[1], Integer.parseInt(singleOption[0]));
                                displayMap.put(Integer.parseInt(singleOption[0]), singleOption[1]);
                            }

                            ShowLog.d("----", "==" + valuesMap.toString());

                            int size = options.length;
                            int count = 0;
                            boolean even;
                            if (size % 2 == 0) {
                                count = size / 2;
                                even = true;
                            } else {
                                count = size / 2 + 1;
                                even = false;
                            }

                            switch (count) {
                                case 1:
                                    inflateOneLayout(even);
                                    break;
                                case 2:
                                    inflateTwoLayout(even);
                                    break;
                                case 3:
                                    inflateThreeLayout(even);
                                    break;
                                case 4:
                                    inflateFourLayout(even);
                                    break;
                            }

                            // end manpulation for diplaying options

                            if (questionResponse.getResumeGameInfo() != null) {
                                attemptedCount = questionResponse.getResumeGameInfo().getCorrect() + questionResponse.getResumeGameInfo().getWrong() + 1;
                                updateAttemptVariable(attemptedCount);

                                correctCount = questionResponse.getResumeGameInfo().getCorrect();
                                updateCorrectVariable(questionResponse.getResumeGameInfo().getCorrect());
                            }
                            timer = questionResponse.getQuestion().getAllowTime();
                            txvTime.setText("" + timer);
                            quesId = questionResponse.getQuestion().getqId();
                            quesLevel = questionResponse.getQuestion().getqLevel();
                            txvQuestion = (TextView) findViewById(R.id.txvQuestion);
                            txvQuestion.setText(questionResponse.getQuestion().getqText());

                            String decoded_url = null;
                            try {
                                if (questionResponse.getQuestion().getImageUrl() != null && !TextUtils.isEmpty(questionResponse.getQuestion().getImageUrl()))
                                    decoded_url = URLDecoder.decode(questionResponse.getQuestion().getImageUrl(), "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            if (decoded_url != null && !TextUtils.isEmpty(decoded_url)) {
                                Picasso.with(this).load(decoded_url).into(imvQuestionImage);
                                cardBackground.setVisibility(View.VISIBLE);
                            } else {
                                cardBackground.setVisibility(View.GONE);
                            }


                            linearTimer = new LinearTimer.Builder()
                                    .linearTimerView(linearTimerView)
                                    .duration(timer * 1000)
                                    .timerListener(this)
                                    .getCountUpdate(LinearTimer.COUNT_DOWN_TIMER, 1000)
                                    .build();

                            linearTimer.startTimer();
                        } catch (Exception e) {
                            hideProgressDialog();
                        }
                        break;
                    case ApiConstants.REQUEST_TYPE.GAME_NEXT_QUESTION:
                        try {
                            final QuestionResponse questionResponse1 = (QuestionResponse) responseObject;

                            // handle prev question answer

                            if (ansValue != -1) {
                                if (questionResponse1.getPreQAns().getResult() == 1) {
                                    correctCount++;
                                    updateCorrectVariable(correctCount);
                                    TextView view = textviewMap.get(ansValue);
                                    view.setTextColor(ContextCompat.getColor(PlayQuestionActivity.this, R.color.white));
                                    view.setBackground(ContextCompat.getDrawable(PlayQuestionActivity.this, R.drawable.correct_ans_bg));
                                } else {
                                    TextView wrongView = textviewMap.get(ansValue);
                                    TextView correctView = textviewMap.get(questionResponse1.getPreQAns().getValidAnswer());

                                    wrongView.setTextColor(ContextCompat.getColor(PlayQuestionActivity.this, R.color.white));
                                    wrongView.setBackground(ContextCompat.getDrawable(PlayQuestionActivity.this, R.drawable.wrong_ans_bg));
                                    correctView.setTextColor(ContextCompat.getColor(PlayQuestionActivity.this, R.color.white));
                                    correctView.setBackground(ContextCompat.getDrawable(PlayQuestionActivity.this, R.drawable.correct_ans_bg));
                                }
                            }


                            Iterator it = textviewMap.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry pair = (Map.Entry) it.next();
                                TextView textView = (TextView) pair.getValue();
                                textView.setEnabled(false);
                                textView.setClickable(false);
//                                System.out.println(pair.getKey() + " = " + pair.getValue());
                                it.remove(); // avoids a ConcurrentModificationException
                            }

                            // next question data manipulation

                            if (questionResponse1.getQuestion() != null) {
                                ToastUtil.showLongToast(PlayQuestionActivity.this, "Redirecting to next question..");
                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {


                                        // manipulation for displying options
                                        attemptedCount++;
                                        updateAttemptVariable(attemptedCount);

//                                        String str = "1#A^2#B^3#C^4#D";
                                        String[] options = questionResponse1.getQuestion().getAnsOptions().split("\\^");
//                                        String[] options = str.split("\\^");
                                        ShowLog.d("---length", "" + options.length);
                                        valuesMap = new HashMap<>();
                                        displayMap = new HashMap<>();
                                        textviewMap = new HashMap<>();

                                        for (String option : options) {
                                            ShowLog.d("---string", "" + option);
                                            String[] singleOption = option.split("\\#");
                                            valuesMap.put(singleOption[1], Integer.parseInt(singleOption[0]));
                                            displayMap.put(Integer.parseInt(singleOption[0]), singleOption[1]);
                                        }

                                        ShowLog.d("----", "==" + valuesMap.toString());

                                        int size = options.length;
                                        int count = 0;
                                        boolean even;
                                        if (size % 2 == 0) {
                                            count = size / 2;
                                            even = true;
                                        } else {
                                            count = size / 2 + 1;
                                            even = false;
                                        }

                                        switch (count) {
                                            case 1:
                                                llyOptions.removeAllViews();
                                                inflateOneLayout(even);
                                                break;
                                            case 2:
                                                llyOptions.removeAllViews();
                                                inflateTwoLayout(even);
                                                break;
                                            case 3:
                                                llyOptions.removeAllViews();
                                                inflateThreeLayout(even);
                                                break;
                                            case 4:
                                                llyOptions.removeAllViews();
                                                inflateFourLayout(even);
                                                break;
                                        }

                                        // end manpulation for diplaying options
                                        timer = questionResponse1.getQuestion().getAllowTime();
                                        txvTime.setText("" + timer);
                                        quesId = questionResponse1.getQuestion().getqId();
                                        quesLevel = questionResponse1.getQuestion().getqLevel();
                                        txvQuestion = (TextView) findViewById(R.id.txvQuestion);
                                        txvQuestion.setText(questionResponse1.getQuestion().getqText());

                                        String decoded_url = null;
                                        try {
                                            if (questionResponse1.getQuestion().getImageUrl() != null && !TextUtils.isEmpty(questionResponse1.getQuestion().getImageUrl()))
                                                decoded_url = URLDecoder.decode(questionResponse1.getQuestion().getImageUrl(), "UTF-8");
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                        if (decoded_url != null && !TextUtils.isEmpty(decoded_url)) {
                                            Picasso.with(PlayQuestionActivity.this).load(decoded_url).into(imvQuestionImage);
                                            cardBackground.setVisibility(View.VISIBLE);
                                        } else {
                                            cardBackground.setVisibility(View.GONE);
                                        }


                                        linearTimer = new LinearTimer.Builder()
                                                .linearTimerView(linearTimerView)
                                                .duration(timer * 1000)
                                                .timerListener(PlayQuestionActivity.this)
                                                .getCountUpdate(LinearTimer.COUNT_DOWN_TIMER, 1000)
                                                .build();

                                        linearTimer.startTimer();

                                    }
                                }, 2000);
                            }

                            // handle result if prev ques was the last question
                            if (questionResponse1.getGameResult() != null) {
                                ToastUtil.showLongToast(PlayQuestionActivity.this, "Redirecting to results..");
                                final GameResultResponse gameResult = questionResponse1.getGameResult();
                                final ArrayList<GameItemResponse> bonusGameItemResponses = questionResponse1.getGames();
                                IqMojoPrefrences.getInstance(PlayQuestionActivity.this).setLong(AppConstants.KEY_COINS, questionResponse1.getCoins());

                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(PlayQuestionActivity.this, GameResultActivity.class);
                                        intent.putExtra(AppConstants.GAME_RESULT, gameResult);
                                        intent.putExtra(AppConstants.GAME_ITEM_OBJECT, gameItemResponse);
                                        intent.putExtra(AppConstants.KEY_COINS, questionResponse1.getCoins().toString());
                                        intent.putParcelableArrayListExtra(AppConstants.BONUS_GAMES, bonusGameItemResponses);
                                        ShowLog.d("--coins", "----" + questionResponse1.getCoins());
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 2000);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
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

        try {
            ShowLog.i("Time left", String.valueOf(tickUpdateInMillis));
            txvTime.setText("" + (tickUpdateInMillis / 1000));

            if ((tickUpdateInMillis / 1000) == 0) {
                ShowLog.d("--next", "timer finish call next ques");
                callNextQuestion(-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTimerReset() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!(linearTimer.getState() == LinearTimerStates.FINISHED)) {
//            linearTimer.pauseTimer();
//            linearTimer.resetTimer();
        }

    }

    private void inflateOneLayout(boolean even) {
        LinearLayout view1 = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.single_option_layout, null, false);
        final TextView txvOptions1 = (TextView) view1.findViewById(R.id.txvOption1);
        txvOptions1.setText(displayMap.get(1));
        textviewMap.put(1, txvOptions1);
        final TextView txvOptions2 = (TextView) view1.findViewById(R.id.txvOption2);

        if (!even) {
            txvOptions2.setVisibility(View.GONE);
        } else {
            txvOptions2.setText(displayMap.get(2));
            textviewMap.put(2, txvOptions2);
        }

        txvOptions1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNextQuestion(valuesMap.get(txvOptions1.getText().toString()));
//                ToastUtil.showLongToast(PlayQuestionActivity.this,""+valuesMap.get(txvOptions1.getText().toString()) );

            }
        });
        txvOptions2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNextQuestion(valuesMap.get(txvOptions2.getText().toString()));
//                ToastUtil.showLongToast(PlayQuestionActivity.this, ""+valuesMap.get(txvOptions2.getText().toString()));

            }
        });
        llyOptions.addView(view1);
    }

    private void inflateTwoLayout(boolean even) {

        LinearLayout view01 = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.single_option_layout, null, false);
        final TextView txvOptions01 = (TextView) view01.findViewById(R.id.txvOption1);
        txvOptions01.setText(displayMap.get(1));
        textviewMap.put(1, txvOptions01);
        final TextView txvOptions02 = (TextView) view01.findViewById(R.id.txvOption2);
        txvOptions02.setText(displayMap.get(2));
        textviewMap.put(2, txvOptions02);


        txvOptions01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNextQuestion(valuesMap.get(txvOptions01.getText().toString()));
//                ToastUtil.showLongToast(PlayQuestionActivity.this, ""+valuesMap.get(txvOptions01.getText().toString()));

            }
        });
        txvOptions02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callNextQuestion(valuesMap.get(txvOptions02.getText().toString()));
//                ToastUtil.showLongToast(PlayQuestionActivity.this, ""+valuesMap.get(txvOptions02.getText().toString()));
            }
        });
        llyOptions.addView(view01);

        LinearLayout view02 = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.single_option_layout, null, false);
        final TextView txvOptions12 = (TextView) view02.findViewById(R.id.txvOption1);
        final TextView txvOptions22 = (TextView) view02.findViewById(R.id.txvOption2);
        txvOptions12.setText(displayMap.get(3));
        textviewMap.put(3, txvOptions12);

        if (!even) {
            txvOptions22.setVisibility(View.GONE);
        } else {
            txvOptions22.setText(displayMap.get(4));
            textviewMap.put(4, txvOptions22);
        }
        txvOptions12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNextQuestion(valuesMap.get(txvOptions12.getText().toString()));
//                ToastUtil.showLongToast(PlayQuestionActivity.this, ""+valuesMap.get(txvOptions12.getText().toString()));

            }
        });
        txvOptions22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNextQuestion(valuesMap.get(txvOptions22.getText().toString()));
//                ToastUtil.showLongToast(PlayQuestionActivity.this, ""+valuesMap.get(txvOptions22.getText().toString()));

            }
        });
        llyOptions.addView(view02);
    }

    private void inflateThreeLayout(boolean even) {

        LinearLayout view01 = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.single_option_layout, null, false);
        final TextView txvOptions01 = (TextView) view01.findViewById(R.id.txvOption1);
        txvOptions01.setText(displayMap.get(1));
        textviewMap.put(1, txvOptions01);

        final TextView txvOptions02 = (TextView) view01.findViewById(R.id.txvOption2);
        txvOptions02.setText(displayMap.get(2));
        textviewMap.put(2, txvOptions02);


        txvOptions01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNextQuestion(valuesMap.get(txvOptions01.getText().toString()));
//                ToastUtil.showLongToast(PlayQuestionActivity.this, ""+valuesMap.get(txvOptions01.getText().toString()));

            }
        });
        txvOptions02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callNextQuestion(valuesMap.get(txvOptions02.getText().toString()));
//                ToastUtil.showLongToast(PlayQuestionActivity.this, ""+valuesMap.get(txvOptions02.getText().toString()));
            }
        });
        llyOptions.addView(view01);
        LinearLayout view11 = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.single_option_layout, null, false);
        final TextView txvOptions11 = (TextView) view11.findViewById(R.id.txvOption1);
        txvOptions11.setText(displayMap.get(3));
        textviewMap.put(3, txvOptions11);

        final TextView txvOptions12 = (TextView) view11.findViewById(R.id.txvOption2);
        txvOptions12.setText(displayMap.get(4));
        textviewMap.put(4, txvOptions12);


        txvOptions11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNextQuestion(valuesMap.get(txvOptions11.getText().toString()));
//                ToastUtil.showLongToast(PlayQuestionActivity.this, ""+valuesMap.get(txvOptions11.getText().toString()));

            }
        });
        txvOptions12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callNextQuestion(valuesMap.get(txvOptions12.getText().toString()));
//                ToastUtil.showLongToast(PlayQuestionActivity.this, ""+valuesMap.get(txvOptions12.getText().toString()));
            }
        });
        llyOptions.addView(view11);

        LinearLayout view21 = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.single_option_layout, null, false);
        final TextView txvOptions21 = (TextView) view21.findViewById(R.id.txvOption1);
        final TextView txvOptions22 = (TextView) view21.findViewById(R.id.txvOption2);
        txvOptions21.setText(displayMap.get(5));
        textviewMap.put(5, txvOptions21);

        if (!even) {
            txvOptions22.setVisibility(View.GONE);
        } else {
            txvOptions22.setText(displayMap.get(6));
            textviewMap.put(6, txvOptions22);
        }
        txvOptions21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNextQuestion(valuesMap.get(txvOptions21.getText().toString()));
//                ToastUtil.showLongToast(PlayQuestionActivity.this, ""+valuesMap.get(txvOptions21.getText().toString()));

            }
        });
        txvOptions22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNextQuestion(valuesMap.get(txvOptions22.getText().toString()));
//                ToastUtil.showLongToast(PlayQuestionActivity.this, ""+valuesMap.get(txvOptions22.getText().toString()));

            }
        });
        llyOptions.addView(view21);
    }


    private void inflateFourLayout(boolean even) {

    }

    private void callNextQuestion(int ansId) {

        if (!(linearTimer.getState() == LinearTimerStates.FINISHED)) {
            linearTimer.pauseTimer();
            linearTimer.resetTimer();
        }
        hitApiRequest(ApiConstants.REQUEST_TYPE.GAME_NEXT_QUESTION, ansId);

        ansValue = ansId;

    }

    public void showPauseDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawableResource(R.drawable.transparent_bg_image);
        dialog.setContentView(R.layout.pause_game_dialog);

        TextView txvResume = (TextView) dialog.findViewById(R.id.txvResume);
        TextView txvNewGame = (TextView) dialog.findViewById(R.id.txvNewGame);

        try {

            txvResume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            txvNewGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent = new Intent(PlayQuestionActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        dialog.setCancelable(true);
        dialog.show();
        try {
            if (!((Activity) this).isFinishing()) {
//                dialog.show();
            }
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        // return dialog;
    }

}
