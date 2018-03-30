package com.iqmojo.iq_mojo.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iqmojo.R;
import com.iqmojo.base.ui.activity.BaseActivity;
import com.iqmojo.base.utils.ShowLog;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.models.response.GameItemResponse;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;

public class GameDetailsActivity extends BaseActivity {

    TextView txvGameName, txvDesc, txvTnC, txvPlay, txvResume;
    GameItemResponse gameItemResponse;
    ImageView imvQuestionImage;
    CardView cardBackground;
    private boolean isResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        try {
            if (getIntent().getParcelableExtra(AppConstants.GAME_ITEM_OBJECT) != null)
                gameItemResponse = getIntent().getParcelableExtra(AppConstants.GAME_ITEM_OBJECT);

            isResume = getIntent().getBooleanExtra(AppConstants.IS_RESUME, false);

            setupToolbar();
            getView();
            setView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setView() {
        if (gameItemResponse.getName() != null) {
            txvGameName.setText(gameItemResponse.getName());
        }
        if (gameItemResponse.getDetailDesc() != null) {
            txvDesc.setText(gameItemResponse.getDetailDesc());
        }
        if (gameItemResponse.gettAndC() != null) {
            txvTnC.setText(gameItemResponse.gettAndC());
        }

        if (isResume) {
            txvResume.setVisibility(View.VISIBLE);
        }

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

    }

    private void getView() {
        txvDesc = (TextView) findViewById(R.id.txvDesc);
        txvGameName = (TextView) findViewById(R.id.txvGameName);
        txvTnC = (TextView) findViewById(R.id.txvTnC);
        txvPlay = (TextView) findViewById(R.id.txvPlay);
        txvResume = (TextView) findViewById(R.id.txvResume);
        imvQuestionImage = (ImageView) findViewById(R.id.imvQuestionImage);
        cardBackground = (CardView) findViewById(R.id.cardBackground);

        txvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameDetailsActivity.this, GameEntryFeeActivity.class);
                intent.putExtra(AppConstants.GAME_ITEM_OBJECT, gameItemResponse);
                intent.putExtra(AppConstants.IS_RESUME,false);
                intent.putExtra(AppConstants.EXTRA_PLAY_MODE,getIntent().getIntExtra(AppConstants.EXTRA_PLAY_MODE,0));
                startActivity(intent);

            }
        });
        txvResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameDetailsActivity.this, PlayQuestionActivity.class);
                intent.putExtra(AppConstants.GAME_ITEM_OBJECT, gameItemResponse);
                intent.putExtra(AppConstants.IS_RESUME,true);
                intent.putExtra(AppConstants.EXTRA_PLAY_MODE,getIntent().getIntExtra(AppConstants.EXTRA_PLAY_MODE,0));
                startActivity(intent);

            }
        });
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
}
