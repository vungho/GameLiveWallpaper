package com.hoquocvung.gamelivewallpaper.Performance;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.hoquocvung.gamelivewallpaper.Entity.DataStructure;
import com.hoquocvung.gamelivewallpaper.Entity.WallpaperInfo;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by vungho on 19/12/2016.
 */

public class Downloader implements DataStructure{
    private Context context;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private SaveState saveState;

    public Downloader(Context context){
        this.context = context;
        storage = FirebaseStorage.getInstance();
        saveState = new SaveState(context);

    }

    public String downloadVideo(WallpaperInfo wp){
        storageRef = storage.getReferenceFromUrl(wp.getVideo());
        final File folder = new File(saveState.restoringVideoSharePreferences());

        Log.i("urlF", saveState.restoringVideoSharePreferences());
        if (folder.exists()){
            Log.i("folder", "Exits");
        }  else{
            Log.i("folder", "No exits");
        }

        final File save = new File(folder, wp.getId()+wp.getName()+"mp4");
        storageRef.getFile(save).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("download", "That bai");
            }
        }).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.i("download", "Thanh cong");
            }
        });
        return folder.getPath();
    }
}
