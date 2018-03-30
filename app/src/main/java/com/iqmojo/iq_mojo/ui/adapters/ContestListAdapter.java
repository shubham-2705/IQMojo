package com.iqmojo.iq_mojo.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iqmojo.R;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.models.response.ContestItemResponse;
import com.iqmojo.iq_mojo.models.response.GameItemResponse;
import com.iqmojo.iq_mojo.ui.activities.GameDetailsActivity;
import com.iqmojo.iq_mojo.ui.fragments.ContestsFragment;
import com.iqmojo.iq_mojo.ui.fragments.HomeFragment;
import com.squareup.picasso.Picasso;

import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by shubhamlamba on 13/09/17.
 */

public class ContestListAdapter extends RecyclerView.Adapter<ContestListAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<ContestItemResponse> contestItemResponses;
    private ContestsFragment contestsFragment;
    private boolean isResume;

    public ContestListAdapter(Context context, ArrayList<ContestItemResponse> list, ContestsFragment fragment) {
        mcontext = context;
        contestItemResponses = list;
        contestsFragment = fragment;
    }

    public void setData(Context context, ArrayList<ContestItemResponse> list, ContestsFragment fragment) {
        mcontext = context;
        contestItemResponses = list;
        contestsFragment = fragment;
        notifyDataSetChanged();
    }

    @Override
    public ContestListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.game_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ContestListAdapter.ViewHolder holder, int position) {

        try {

            final ContestItemResponse contestItemResponse = contestItemResponses.get(position);

            holder.txvDesc.setText(contestItemResponse.getShortDesc());
            holder.txvHeading.setText(contestItemResponse.getName());

            String decoded_url = URLDecoder.decode(contestItemResponse.getImageUrl(), "UTF-8");
            if (decoded_url != null && !TextUtils.isEmpty(decoded_url))
                Picasso.with(mcontext).load(decoded_url).into(holder.imvGameLogo);

            if (contestItemResponse.getActive() == 2) {
                holder.txvStart.setText("RESUME CONTEST");
                isResume=true;
            } else {
                isResume=false;
                holder.txvStart.setText("START");
            }

            holder.txvStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mcontext, GameDetailsActivity.class);
                    intent.putExtra(AppConstants.CONTEST_ITEM_OBJECT, contestItemResponse);
                    isResume = contestItemResponse.getActive() == 2;
                    intent.putExtra(AppConstants.IS_RESUME,isResume);
                    intent.putExtra(AppConstants.EXTRA_PLAY_MODE,AppConstants.PLAY_MODE.CONTEST);
                    mcontext.startActivity(intent);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        if (contestItemResponses != null) {
            return contestItemResponses.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvHeading, txvDesc, txvStart;
        ImageView imvGameLogo;


        public ViewHolder(View itemView) {
            super(itemView);
            txvHeading = (TextView) itemView.findViewById(R.id.txvHeading);
            txvDesc = (TextView) itemView.findViewById(R.id.txvDesc);
            txvStart = (TextView) itemView.findViewById(R.id.txvStart);
            imvGameLogo = (ImageView) itemView.findViewById(R.id.imvGameLogo);
        }
    }
}
