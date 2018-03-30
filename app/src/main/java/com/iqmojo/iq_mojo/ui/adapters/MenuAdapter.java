package com.iqmojo.iq_mojo.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iqmojo.R;
import com.iqmojo.base.utils.ShowLog;
import com.iqmojo.iq_mojo.constants.ApiConstants;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.ui.activities.ContactUsActivity;
import com.iqmojo.iq_mojo.ui.activities.HomeActivity;
import com.iqmojo.iq_mojo.ui.activities.MyAccountDashboardActivity;
import com.iqmojo.iq_mojo.ui.activities.MyProfileActivity;
import com.iqmojo.iq_mojo.ui.activities.ReferralActivity;
import com.iqmojo.iq_mojo.ui.activities.WebviewActivity;


public class MenuAdapter extends BaseAdapter {

    private static int[] list = {AppConstants.My_Points, AppConstants.My_Profile, AppConstants.Transactions,
            AppConstants.Referral, AppConstants.Terms_And_Conditions, AppConstants.Privacy_Policy, AppConstants.Contact_Us};
    private static int[] list_images= {R.drawable.menu_0,R.drawable.menu_1,R.drawable.menu_2,R.drawable.menu_3,R.drawable.menu_4,R.drawable.menu_5,R.drawable.menu_6};
    private static String[] list_values={"My Points","My Profile","Transactions","Referral","Terms and Conditions","Privacy Policy","Contact Us"};

    private Context mContext;
    private LayoutInflater inflater;

    public MenuAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Integer getItem(int position) {
        return list[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView txvMenu;
        RelativeLayout rlyParent;

        ViewHolder(View convertView) {
            txvMenu = (TextView) convertView.findViewById(R.id.txvMenu);
            rlyParent = (RelativeLayout) convertView.findViewById(R.id.rlyParent);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        try {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.hamburger_list_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ShowLog.d("----posi", position + "---" + list_values[position]);
            viewHolder.txvMenu.setText(list_values[position]);
            String mDrawableName = "menu_" + getItem(position);
//        int id = mContext.getResources().getIdentifier(mDrawableName, "drawable", mContext.getPackageName());
            int id = list_images[position];
            viewHolder.txvMenu.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0);
//            viewHolder.txvMenu.setCompoundDrawablePadding((int) (((HomeActivity) mContext).convertPixelsToDp(35, mContext)));

            viewHolder.txvMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowLog.d("---", "close");
                    ((HomeActivity) mContext).closeDrawer();
                    Intent intent=null;
                    switch (position) {
                        case AppConstants.My_Points:
                            intent = new Intent(mContext, MyAccountDashboardActivity.class);
                            intent.putExtra(AppConstants.SCREEN_NO, 0);
                            mContext.startActivity(intent);
                            break;
                        case AppConstants.My_Profile:
                            intent = new Intent(mContext, MyProfileActivity.class);
                            mContext.startActivity(intent);
                            break;
                        case AppConstants.Transactions:
                            intent = new Intent(mContext, MyAccountDashboardActivity.class);
                            intent.putExtra(AppConstants.SCREEN_NO, 1);
                            mContext.startActivity(intent);
                            break;
                        case AppConstants.Referral:
                            intent = new Intent(mContext, ReferralActivity.class);
                            mContext.startActivity(intent);
                            break;
                        case AppConstants.Terms_And_Conditions:
                            intent = new Intent(mContext, WebviewActivity.class);
                            intent.putExtra(AppConstants.WEBVIEW_URL, ApiConstants.Urls.TNC);
                            mContext.startActivity(intent);
                            break;
                        case AppConstants.Privacy_Policy:
                            intent = new Intent(mContext, WebviewActivity.class);
                            intent.putExtra(AppConstants.WEBVIEW_URL, ApiConstants.Urls.PRIVACY_POLICY);
                            mContext.startActivity(intent);
                            break;
                        case AppConstants.Contact_Us:
                            intent = new Intent(mContext, ContactUsActivity.class);
                            mContext.startActivity(intent);
                            break;

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;


    }
}
