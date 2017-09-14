package com.iqmojo.iq_mojo.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iqmojo.R;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.ui.activities.HomeActivity;


public class MenuAdapter extends BaseAdapter {

    private static int[] list = {AppConstants.My_Points, AppConstants.My_Profile, AppConstants.Transactions,
            AppConstants.Referral, AppConstants.Transactions, AppConstants.Contact_Us};

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

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.hamburger_list_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.txvMenu.setText(mContext.getResources().getStringArray(R.array.menu_)[getItem(position)]);
            String mDrawableName = "menu_" + getItem(position);
            int id = mContext.getResources().getIdentifier(mDrawableName, "drawable", mContext.getPackageName());
            viewHolder.txvMenu.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0);
            viewHolder.txvMenu.setCompoundDrawablePadding((int) (((HomeActivity) mContext).convertPixelsToDp(15, mContext)));

            return convertView;


    }
}
