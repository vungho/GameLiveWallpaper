package com.hoquocvung.gamelivewallpaper.Performance;

import android.content.Context;
import android.content.SharedPreferences;

import com.hoquocvung.gamelivewallpaper.Entity.DataStructure;

import java.io.File;

/**
 * Created by vungho on 18/12/2016.
 */

public class SaveState implements DataStructure{
    private Context context;
    public SaveState (Context context){
        this.context = context;
    }

    //Đây là phần lưu trữ thông tin Service
    //Lưu trạng thái
    public void savingSharePreferences (String url){
        SharedPreferences sRef = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sRef.edit();

        if (url != null && !url.isEmpty()){
            editor.putString(PREF_KEY, url);
            editor.commit();
        }
    }


    //Đọc trạng thái
    public String restoringSharePreferences (){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String res = preferences.getString(PREF_KEY, "-1");
        return res;
    }

    //Đây là phần lưu trữ thông tin Folder chứa video tải xuống
    public void savingVideoSharePreferences (String folderUrl){
        SharedPreferences sRef = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sRef.edit();

        if (folderUrl != null && !folderUrl.isEmpty()){
            editor.putString(PREF_FOLDER_URL, folderUrl);
            editor.commit();
        }
    }


    //Đọc trạng thái
    public String restoringVideoSharePreferences (){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String res = preferences.getString(PREF_FOLDER_URL, "-1");
        return res;
    }

}
