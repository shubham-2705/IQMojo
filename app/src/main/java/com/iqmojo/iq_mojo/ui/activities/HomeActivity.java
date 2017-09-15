package com.iqmojo.iq_mojo.ui.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.iqmojo.R;
import com.iqmojo.base.ui.activity.BaseActivity;
import com.iqmojo.base.utils.ShowLog;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.persistence.IqMojoPrefrences;
import com.iqmojo.iq_mojo.ui.adapters.MenuAdapter;
import com.iqmojo.iq_mojo.ui.fragments.ContestsFragment;
import com.iqmojo.iq_mojo.ui.fragments.FaqFragment;
import com.iqmojo.iq_mojo.ui.fragments.HomeFragment;
import com.iqmojo.iq_mojo.ui.fragments.WinnerFragment;
import com.iqmojo.iq_mojo.utils.FontHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;


public class HomeActivity extends BaseActivity implements DrawerLayout.DrawerListener {

    FrameLayout container;
    Toolbar mToolbar;
    ViewPager viewPager;
    TabLayout tabs;
    private ListView listView;
    private MenuAdapter menuAdapter;
    private int active_position = 0;
    private TextView txvUserEmail,txvCoins,txvUserName;
    DuoDrawerLayout drawerLayout;
    DuoDrawerToggle drawerToggle;
    private static int[] tab_list = {AppConstants.HOME, AppConstants.WINNER,
            AppConstants.CONTEST, AppConstants.FAQ};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getView();
        setupDrawer_Toolbar();
        setupHamburgerList();

    }

    public void setupDrawer_Toolbar() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        txvCoins=(TextView)mToolbar.findViewById(R.id.txvCoins);

        mToolbar.setLogo(R.drawable.iqmojo_toolbar);

        txvCoins.setText((""+new DecimalFormat("##,##,##0").format(IqMojoPrefrences.getInstance(this).getLong(AppConstants.KEY_COINS))));

        drawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
        drawerToggle = new DuoDrawerToggle(this, drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.setHomeAsUpIndicator(R.drawable.hamburger);
        drawerLayout.setDrawerListener(this);
        drawerToggle.syncState();


        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    private void setupHamburgerList()
    {
        txvUserEmail.setText(IqMojoPrefrences.getInstance(this).getString(AppConstants.KEY_EMAIL_ID));
        txvUserName.setText("");
        menuAdapter = new MenuAdapter(this);
        listView.setAdapter(menuAdapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                ShowLog.d("---","close");
//                closeDrawer();
//                switch (position) {
//                    case AppConstants.My_Points:
//                        break;
//                    case AppConstants.My_Profile:
//                        break;
//                    case AppConstants.Transactions:
//                        break;
//                    case AppConstants.Referral:
//                        break;
//                    case AppConstants.Terms_And_Conditions:
//                        break;
//                    case AppConstants.Contact_Us:
//                        break;
//
//                }
//            }
//        });
    }

    public void getView() {

//        container = (FrameLayout) findViewById(R.id.container);
        viewPager = (ViewPager) findViewById(R.id.pager);
        listView = (ListView) findViewById(R.id.listView);
        txvUserEmail = (TextView) findViewById(R.id.txvUserEmail);
        txvUserName = (TextView) findViewById(R.id.txvUserName);
        setupViewPager(viewPager);

        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        setupTabIcons();


    }

    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(), "ONE");
        adapter.addFrag(new WinnerFragment(), "TWO");
        adapter.addFrag(new ContestsFragment(), "THREE");
        adapter.addFrag(new FaqFragment(), "Four");
        viewPager.setAdapter(adapter);
    }


    private void setupTabIcons() {


        for (int aTab_list : tab_list) {
            View tabOne = (View) LayoutInflater.from(this).inflate(R.layout.custom_tab_layout, null);
            TextView textView = (TextView) tabOne.findViewById(R.id.singleTabText);
            ImageView imageView = (ImageView) tabOne.findViewById(R.id.imvIcon);

            textView.setText(getResources().getStringArray(R.array.pager_)[aTab_list]);
            String mDrawableName = "pager_" + aTab_list + "_inactive";
            int id = getResources().getIdentifier(mDrawableName, "drawable", getPackageName());
            imageView.setImageResource(id);

            if (aTab_list == active_position) {
                textView.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.colorPrimaryDark));
                String mDrawable = "pager_" + aTab_list + "_active";
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
                            textView.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.colorPrimaryDark));
                            String mDrawable = "pager_" + tab.getPosition() + "_active";
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
                            textView.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.grey_text_color));
                            String mDrawableName = "pager_" + tab.getPosition() + "_inactive";
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

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
//        container.setElevation(30f);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
//        container.setElevation(10f);
    }

    @Override
    public void onDrawerStateChanged(int newState) {


    }

    public void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}
