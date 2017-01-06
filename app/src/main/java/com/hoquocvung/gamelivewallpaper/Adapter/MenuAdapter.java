package com.hoquocvung.gamelivewallpaper.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hoquocvung.gamelivewallpaper.Entity.MenuInfo;
import com.hoquocvung.gamelivewallpaper.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vungho on 13/12/2016.
 */

public class MenuAdapter extends ArrayAdapter<MenuInfo> {

    private Context context;
    private int layout;
    private ArrayList<MenuInfo> list;

    public  MenuAdapter(Context context, int layout, ArrayList<MenuInfo> list){
        super(context, layout, list);
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);

        MenuInfo item = list.get(position);

        ImageView imgMainDrawer = (ImageView)convertView.findViewById(R.id.imgMainDrawer);
        TextView txtManDrawer = (TextView)convertView.findViewById(R.id.txtMainDrawer);

        txtManDrawer.setText(item.getName());
        Picasso.with(convertView.getContext()).load(item.getIcon()).into(imgMainDrawer);

        Glide
                .with(context)
                .load(item.getIcon())
                .centerCrop()
                .placeholder(R.drawable.bg_placeholder)
                .crossFade()
                .into(imgMainDrawer);
        return convertView;
    }

    public void changeData(){
        notifyDataSetChanged();
    }
}
