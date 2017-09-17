package com.iqmojo.iq_mojo.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.iqmojo.R;
import com.iqmojo.base.ui.activity.BaseActivity;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.models.response.GameItemResponse;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;

import java.text.DecimalFormat;

public class GameEntryFeeActivity extends BaseActivity {

    TextView txvWallet,txvPurchase,txvEntry,txvGameName;
    GameItemResponse gameItemResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_entry_fee);


        if (getIntent().getParcelableExtra(AppConstants.GAME_ITEM_OBJECT) != null)
            gameItemResponse = getIntent().getParcelableExtra(AppConstants.GAME_ITEM_OBJECT);

        setupToolbar();
        getView();
        setView();

    }

    private void setView() {
        if (gameItemResponse.getTotalQ()>0) {
            txvGameName.setText("Set of "+gameItemResponse.getTotalQ()+" Questions!");
        }
        if (gameItemResponse.getEntryFee()>0) {
            txvEntry.setText(""+gameItemResponse.getEntryFee());
        }

    }

    private void getView() {
        txvEntry = (TextView) findViewById(R.id.txvEntry);
        txvGameName = (TextView) findViewById(R.id.txvGameName);
        txvPurchase = (TextView) findViewById(R.id.txvPurchase);
        txvWallet = (TextView) findViewById(R.id.txvWallet);

        txvWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GameEntryFeeActivity.this,PlayQuestionActivity.class);
                intent.putExtra(AppConstants.GAME_ITEM_OBJECT, gameItemResponse);
                startActivity(intent);

            }
        });

        txvPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
