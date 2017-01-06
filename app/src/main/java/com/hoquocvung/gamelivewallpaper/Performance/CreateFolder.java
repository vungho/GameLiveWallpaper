package com.hoquocvung.gamelivewallpaper.Performance;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by vungho on 18/12/2016.
 */

public class CreateFolder {
    private static  final String dir = ".Game live wallpaper";
    private Context context;
    private SaveState saveState;

    public CreateFolder(Context context) {
        this.context = context;
        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + dir);
        boolean success = true;

        if (!folder.exists()){
            success = folder.mkdir();
            saveState = new SaveState(context);
            saveState.savingVideoSharePreferences(folder.getPath());
            Log.i("mk", folder.getPath());
        }
        if (success){
            Log.i("folder", "Create folder success!");
            //Log.i("folder", saveState.restoringVideoSharePreferences());

        }else {
            Log.i("folder", "Create folfer fail");
        }
    }
}
