package com.iqmojo.iq_mojo.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.iqmojo.R;
import com.iqmojo.base.ui.activity.BaseActivity;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.models.response.GameResultResponse;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;

import java.text.DecimalFormat;

public class GameResultActivity extends BaseActivity {

    GameResultResponse gameResultResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        try {
            if (getIntent().getParcelableExtra(AppConstants.GAME_RESULT) != null)
                gameResultResponse = getIntent().getParcelableExtra(AppConstants.GAME_RESULT);

            setView();
            setupToolbar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setView() {
        TextView txvResult = (TextView) findViewById(R.id.txvResult);
        String result = "";
        if (gameResultResponse.getResult() == 0) {
            result = "Lost";
        } else {
            result = "Won";
        }

        txvResult.setText("Total Questions : " + gameResultResponse.getTotalQ()+"\nGame Result : "+result+"\nCoins Earned : "+gameResultResponse.getReward()
        +"\nCorrect Answers : "+gameResultResponse.getCorrect()+"\nWrong Answers : "+gameResultResponse.getWrong());
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
        txvCoins.setText(("" + new DecimalFormat("##,##,##0").format(IqMojoPrefrences.getInstance(this).getLong(AppConstants.KEY_COINS))));

    }
}
