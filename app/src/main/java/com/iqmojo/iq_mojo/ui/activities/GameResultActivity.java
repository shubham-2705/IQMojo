package com.iqmojo.iq_mojo.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iqmojo.R;
import com.iqmojo.base.ui.activity.BaseActivity;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.models.response.GameItemResponse;
import com.iqmojo.iq_mojo.models.response.GameResultResponse;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GameResultActivity extends BaseActivity {

    GameResultResponse gameResultResponse;
    GameItemResponse gameItemResponse;
    ArrayList<GameItemResponse> bonusGameItemResponses;
    String coins;
    ImageView imvQuestionImage;
    CardView cardBackground;
    TextView txvResp,txvRespExtended,txStatsLabel,txvTotalQValue,txvCorrectQValue,txvPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        try {
            if (getIntent().getParcelableExtra(AppConstants.GAME_RESULT) != null)
                gameResultResponse = getIntent().getParcelableExtra(AppConstants.GAME_RESULT);

            if (getIntent().getParcelableExtra(AppConstants.GAME_ITEM_OBJECT) != null)
                gameItemResponse = getIntent().getParcelableExtra(AppConstants.GAME_ITEM_OBJECT);

            if (getIntent().getParcelableArrayListExtra(AppConstants.BONUS_GAMES) != null)
                bonusGameItemResponses = getIntent().getParcelableArrayListExtra(AppConstants.BONUS_GAMES);

            if (getIntent().getStringExtra(AppConstants.KEY_COINS) !=null)
                coins = getIntent().getStringExtra(AppConstants.KEY_COINS);

            getView();
            setView();
            setupToolbar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getView()
    {
        txvPlay=(TextView)findViewById(R.id.txvPlay);
        txvResp=(TextView)findViewById(R.id.txvResp);
        txvRespExtended=(TextView)findViewById(R.id.txvRespExtended);
        txStatsLabel=(TextView)findViewById(R.id.txStatsLabel);
        txvTotalQValue=(TextView)findViewById(R.id.txvTotalQValue);
        txvCorrectQValue=(TextView)findViewById(R.id.txvCorrectQValue);
        imvQuestionImage=(ImageView) findViewById(R.id.imvQuestionImage);
        cardBackground=(CardView) findViewById(R.id.cardBackground);
    }
    private void setView() {
        txvResp.setText(Html.fromHtml(gameResultResponse.getRespText()));
        if(gameResultResponse.getReward()>0)
        {
            txvRespExtended.setText("You get "+gameResultResponse.getReward()+" coins for playing #"+gameItemResponse.getName()+"!");
        }
        else {
            txvRespExtended.setVisibility(View.GONE);
        }
        txvCorrectQValue.setText(""+gameResultResponse.getCorrect());
        txvTotalQValue.setText(""+gameResultResponse.getTotalQ());

        String decoded_url = null;
        try {
            if (gameItemResponse.getDescImage() != null && !TextUtils.isEmpty(gameItemResponse.getDescImage()))
                decoded_url = URLDecoder.decode(gameItemResponse.getDescImage(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (decoded_url != null && !TextUtils.isEmpty(decoded_url)) {
            Picasso.with(this).load(decoded_url).into(imvQuestionImage);
        } else {
            cardBackground.setVisibility(View.GONE);
        }
        txvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GameResultActivity.this,CreateChallengeActivity.class);
                intent.putExtra(AppConstants.GAME_ITEM_OBJECT, gameItemResponse);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setLogo(R.drawable.iqmojo_toolbar);

        TextView txvCoins = (TextView) mToolbar.findViewById(R.id.txvCoins);
        txvCoins.setText(("" + new DecimalFormat("##,##,##0").format(Long.parseLong(coins))));
        IqMojoPrefrences.getInstance(this).setLong(AppConstants.KEY_COINS,Long.parseLong(coins));

    }
}
