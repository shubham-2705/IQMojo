package com.iqmojo.iq_mojo.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iqmojo.R;
import com.iqmojo.iq_mojo.models.response.GameItemResponse;
import com.iqmojo.iq_mojo.ui.fragments.HomeFragment;
import com.iqmojo.iq_mojo.utils.FontHelper;
import com.squareup.picasso.Picasso;

import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by shubhamlamba on 13/09/17.
 */

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<GameItemResponse> gameItemResponses;
    private HomeFragment homeFragment;

    public GameListAdapter(Context context,ArrayList<GameItemResponse> list, HomeFragment fragment)
    {
        mcontext=context;
        gameItemResponses=list;
        homeFragment=fragment;
    }

    public void setData(Context context,ArrayList<GameItemResponse> list, HomeFragment fragment)
    {
        mcontext=context;
        gameItemResponses=list;
        homeFragment=fragment;
        notifyDataSetChanged();
    }

    @Override
    public GameListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.game_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(GameListAdapter.ViewHolder holder, int position) {

        try
        {

            final GameItemResponse gameItemResponse = gameItemResponses.get(position);

            holder.txvDesc.setText(gameItemResponse.getShortDesc());
            holder.txvHeading.setText(gameItemResponse.getName());

            FontHelper.applyFont(mcontext,holder.txvDesc,"fonts/sub_heading.OTF");
            FontHelper.applyFont(mcontext,holder.txvHeading,"fonts/heading.OTF");
            FontHelper.applyFont(mcontext,holder.txvStart,"fonts/sub_heading.OTF");

            String decoded_url = URLDecoder.decode(gameItemResponse.getImageUrl(), "UTF-8");
            Picasso.with(mcontext).load(decoded_url).into(holder.imvGameLogo);

            holder.txvHeading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        if (gameItemResponses != null) {
            return gameItemResponses.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
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
