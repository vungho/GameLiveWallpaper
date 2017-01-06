package com.hoquocvung.gamelivewallpaper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoquocvung.gamelivewallpaper.Activity.PreviewActivity;
import com.hoquocvung.gamelivewallpaper.Entity.DataStructure;
import com.hoquocvung.gamelivewallpaper.Entity.GameInfo;
import com.hoquocvung.gamelivewallpaper.Entity.WallpaperInfo;
import com.hoquocvung.gamelivewallpaper.Performance.VideoRequestHandler;
import com.hoquocvung.gamelivewallpaper.R;
import com.squareup.picasso.Picasso;

/**
 * Created by vungho on 17/12/2016.
 */

public class MainOfflineAdapter extends RecyclerView.Adapter<MainOfflineAdapter.ViewHolder> implements DataStructure{

    private int layout;
    private GameInfo gameInfo;
    private Context context;
    private Picasso picasso;
    private VideoRequestHandler videoRequestHandler;

    public MainOfflineAdapter(int layout, GameInfo gameInfo){
        this.layout = layout;
        this.gameInfo = gameInfo;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, null);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();

        Picasso.Builder builder = new Picasso.Builder(context);
        videoRequestHandler = new VideoRequestHandler();
        builder.addRequestHandler(videoRequestHandler);
        picasso = builder.build();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final WallpaperInfo item = gameInfo.getListWallpaper().get(position);

        if (item.isDownloadState()){
            holder.txtDownload.setTextColor(Color.parseColor("#009688"));
            holder.txtDownload.setText(context.getString(R.string.downloadStateTrue));
        }else {
            holder.txtDownload.setTextColor(Color.parseColor("#9e9e9e"));
            holder.txtDownload.setText(context.getString(R.string.downloadStateFalse));
        }
        picasso.load(videoRequestHandler.SCHEME_VIDEO +":"
                        +item.getFileUrl()).centerCrop().fit().into(holder.imgMain);

        holder.imgMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PreviewActivity.class);

                 intent.putExtra(WP_ID, item.getId());
                 intent.putExtra(WP_NAME, item.getName());
                 intent.putExtra(WP_IMAGE, item.getImage());
                 intent.putExtra(WP_VIDEO, item.getVideo());
                 intent.putExtra(WP_DOWNLOAD, item.isDownloadState());
                intent.putExtra(WP_FILE, item.getFileUrl());
                 intent.putExtra(WP_PARENT, item.getParent());

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return gameInfo.getListWallpaper().size();
    }

    public static class ViewHolder  extends RecyclerView.ViewHolder{

        CardView cardMain;
        ImageView imgMain;
        TextView txtDownload;


        public ViewHolder(View itemView) {
            super(itemView);
            cardMain = (CardView)itemView.findViewById(R.id.main_card_view);
            imgMain = (ImageView)itemView.findViewById(R.id.imgMain);
            txtDownload = (TextView)itemView.findViewById(R.id.txtDowload);
        }
    }



}
