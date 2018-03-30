package com.iqmojo.iq_mojo.ui.adapters;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iqmojo.R;
import com.iqmojo.base.utils.ToastUtil;
import com.iqmojo.iq_mojo.constants.AppConstants;
import com.iqmojo.iq_mojo.models.response.ChallengeItemResponse;
import com.iqmojo.iq_mojo.ui.activities.GameDetailsActivity;
import com.iqmojo.iq_mojo.ui.fragments.ChallengeListFragment;

import java.util.ArrayList;

/**
 * Created by shubhamlamba on 12/3/17.
 */

public class ChallengeListAdapter extends RecyclerView.Adapter<ChallengeListAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<ChallengeItemResponse> challengeItemResponses;
    private ChallengeListFragment fragment;

    public ChallengeListAdapter(Context context, ArrayList<ChallengeItemResponse> list, ChallengeListFragment fragment) {
        mcontext = context;
        challengeItemResponses = list;
        this.fragment = fragment;
    }

    public void setData(Context context, ArrayList<ChallengeItemResponse> list, ChallengeListFragment fragment) {
        mcontext = context;
        challengeItemResponses = list;
        this.fragment = fragment;
        notifyDataSetChanged();
    }

    @Override
    public ChallengeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.challenge_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ChallengeListAdapter.ViewHolder holder, int position) {

        try {

            final ChallengeItemResponse challengeItemResponse = challengeItemResponses.get(position);

            holder.txvChallengeName.setText(challengeItemResponse.getChallengeName());
            holder.txvChallengeNature.setText(challengeItemResponse.getChallengeNature());
            holder.txvMaxPlay.setText("Max Players: "+challengeItemResponse.getMaxPlay());
            holder.txvWin.setText("Won: "+challengeItemResponse.getWin()+"  Lost: "+challengeItemResponse.getLoss());
            holder.txvAmount.setText(""+challengeItemResponse.getPrizeMoney());


            if (challengeItemResponse.getOrigin() == 0) {
                holder.txvStop.setText("PLAY");
            } else {
                holder.txvStop.setText("STOP");
            }
            holder.txvStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (challengeItemResponse.getOrigin() == 0) {
                        Intent intent = new Intent(mcontext, GameDetailsActivity.class);
                        intent.putExtra(AppConstants.CHALLENGE_ITEM_OBJECT, challengeItemResponse);
//                        intent.putExtra(AppConstants.IS_RESUME, isResume);
                        intent.putExtra(AppConstants.EXTRA_PLAY_MODE, AppConstants.PLAY_MODE.CHALLENGE);
                        mcontext.startActivity(intent);
                    } else {
                        ((ChallengeListFragment)fragment).callStopChallengeApi(challengeItemResponse.getCid());
                    }
                }
            });

            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, challengeItemResponse.getChallengeText());
                    mcontext.startActivity(Intent.createChooser(sharingIntent,"Select to Challenge"));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        if (challengeItemResponses != null) {
            return challengeItemResponses.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvChallengeName, txvChallengeNature, txvMaxPlay,txvWin,txvAmount,txvStop;
        ImageView share;


        public ViewHolder(View itemView) {
            super(itemView);
            txvChallengeName = (TextView) itemView.findViewById(R.id.txvChallengeName);
            txvChallengeNature = (TextView) itemView.findViewById(R.id.txvChallengeNature);
            txvMaxPlay = (TextView) itemView.findViewById(R.id.txvMaxPlay);
            txvWin = (TextView) itemView.findViewById(R.id.txvWin);
            txvAmount = (TextView) itemView.findViewById(R.id.txvAmount);
            txvStop = (TextView) itemView.findViewById(R.id.txvStop);
            share = (ImageView) itemView.findViewById(R.id.shareIv);
        }
    }
}
