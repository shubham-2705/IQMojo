package com.iqmojo.iq_mojo.ui.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.iqmojo.R;
import com.iqmojo.base.ui.activity.BaseActivity;
import com.iqmojo.base.utils.ShowLog;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.iqmojo.iq_mojo.ui.fragments.ContestsFragment;
import com.iqmojo.iq_mojo.ui.fragments.FaqFragment;
import com.iqmojo.iq_mojo.ui.fragments.HomeFragment;
import com.iqmojo.iq_mojo.ui.fragments.MyPointsFragment;
import com.iqmojo.iq_mojo.ui.fragments.MyTransactionsFragment;
import com.iqmojo.iq_mojo.ui.fragments.WinnerFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MyAccountDashboardActivity extends BaseActivity {

    Toolbar mToolbar;
    ViewPager viewPager;
    TabLayout tabs;
    private TextView txvCoins;
    private int active_position = 0;
    private static int[] tab_list = {AppConstants.MyAccountTabKeys.MY_POINTS, AppConstants.MyAccountTabKeys.MY_TRANSACTIONS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_dashboard);

        getView();
        setupToolbar();
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
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setLogo(R.drawable.iqmojo_toolbar);


        txvCoins = (TextView) mToolbar.findViewById(R.id.txvCoins);
        txvCoins.setText(("" + new DecimalFormat("##,##,##0").format(IqMojoPrefrences.getInstance(this).getLong(AppConstants.KEY_COINS))));

    }

    public void getView() {


        try {
            if (getIntent().hasExtra(AppConstants.SCREEN_NO)) {
                active_position = getIntent().getIntExtra(AppConstants.SCREEN_NO, 0);
            }
            viewPager = (ViewPager) findViewById(R.id.pager);
            setupViewPager(viewPager);

            tabs = (TabLayout) findViewById(R.id.tabs);
            tabs.setupWithViewPager(viewPager);

            setupTabIcons();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MyPointsFragment(), "ONE");
        adapter.addFrag(new MyTransactionsFragment(), "TWO");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(active_position);
    }

    private void setupTabIcons() {


        for (int aTab_list : tab_list) {
            View tabOne = (View) LayoutInflater.from(this).inflate(R.layout.custom_tab_layout, null);
            TextView textView = (TextView) tabOne.findViewById(R.id.singleTabText);
            ImageView imageView = (ImageView) tabOne.findViewById(R.id.imvIcon);

            textView.setText(getResources().getStringArray(R.array.pager_0)[aTab_list]);
            String mDrawableName = "pager_0" + aTab_list + "_inactive";
            int id = getResources().getIdentifier(mDrawableName, "drawable", getPackageName());
            imageView.setImageResource(id);

            if (aTab_list == active_position) {
                textView.setTextColor(ContextCompat.getColor(MyAccountDashboardActivity.this, R.color.colorPrimaryDark));
                String mDrawable = "pager_0" + aTab_list + "_active";
                int _id = getResources().getIdentifier(mDrawable, "drawable", getPackageName());
                imageView.setImageResource(_id);
            }

            tabs.getTabAt(aTab_list).setCustomView(tabOne);

        }

        tabs.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        ShowLog.d("--tabs", "tab selected");
                        View view = tab.getCustomView();
                        if (view != null) {
                            TextView textView = (TextView) view.findViewById(R.id.singleTabText);
                            ImageView imageView = (ImageView) view.findViewById(R.id.imvIcon);
                            textView.setTextColor(ContextCompat.getColor(MyAccountDashboardActivity.this, R.color.colorPrimaryDark));
                            String mDrawable = "pager_0" + tab.getPosition() + "_active";
                            int _id = getResources().getIdentifier(mDrawable, "drawable", getPackageName());
                            imageView.setImageResource(_id);

                        }

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        ShowLog.d("--tabs", "tab unselected");
                        View view = tab.getCustomView();
                        if (view != null) {
                            TextView textView = (TextView) view.findViewById(R.id.singleTabText);
                            ImageView imageView = (ImageView) view.findViewById(R.id.imvIcon);
                            textView.setTextColor(ContextCompat.getColor(MyAccountDashboardActivity.this, R.color.grey_text_color));
                            String mDrawableName = "pager_0" + tab.getPosition() + "_inactive";
                            int id = getResources().getIdentifier(mDrawableName, "drawable", getPackageName());
                            imageView.setImageResource(id);
                        }
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );

//        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_layout, null);
//        tabTwo.setText("TWO");
//        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_call, 0, 0);
//        tabs.getTabAt(1).setCustomView(tabTwo);

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
