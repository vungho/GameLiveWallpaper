package com.hoquocvung.gamelivewallpaper.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hoquocvung.gamelivewallpaper.Entity.DataStructure;
import com.hoquocvung.gamelivewallpaper.Entity.WallpaperInfo;
import com.hoquocvung.gamelivewallpaper.Performance.SaveState;
import com.hoquocvung.gamelivewallpaper.R;
import com.hoquocvung.gamelivewallpaper.Service.WallPaperService;

import java.io.File;
import java.io.IOException;

public class PreviewActivity extends AppCompatActivity implements DataStructure {

    private Button btnSetWall;
    private WallpaperInfo wallpaperInfo;
    private String itemSelected;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private SaveState saveState;
    private VideoView videoView;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        //View config
        videoView = (VideoView) findViewById(R.id.video_view);
        btnSetWall = (Button)findViewById(R.id.btnSetWall);
        //config system
        saveState = new SaveState(this);
        storage = FirebaseStorage.getInstance();
        itemSelected = saveState.restoringSharePreferences();

        Intent intent = getIntent();
        wallpaperInfo = new WallpaperInfo();
        wallpaperInfo.setId(intent.getStringExtra(WP_ID));
        wallpaperInfo.setName(intent.getStringExtra(WP_NAME));
        wallpaperInfo.setImage(intent.getStringExtra(WP_IMAGE));
        wallpaperInfo.setVideo(intent.getStringExtra(WP_VIDEO));
        wallpaperInfo.setDownloadState(intent.getBooleanExtra(WP_DOWNLOAD, false));
        wallpaperInfo.setFileUrl(intent.getStringExtra(WP_FILE));
        wallpaperInfo.setParent(intent.getStringExtra(WP_PARENT));

        //Event listener
        btnSetWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveState.savingSharePreferences(wallpaperInfo.getFileUrl());
                Log.i("videoPath", saveState.restoringSharePreferences());
                Intent intent = new Intent(
                        WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                        new ComponentName(PreviewActivity.this, WallPaperService.class));
                startActivity(intent);
            }
        });

        //Firebase download config anf download
        if (wallpaperInfo.isDownloadState()){
            videoView.setVideoPath(wallpaperInfo.getFileUrl());
            videoView.start();
            videoView.invalidate();
        }else {
            downloadVideo();
        }

    }

    public  void  downloadVideo (){
        storageRef = storage.getReferenceFromUrl(wallpaperInfo.getVideo());
        final File folder = new File(saveState.restoringVideoSharePreferences());

        final File save = new File(folder, wallpaperInfo.getParent() +"_" + wallpaperInfo.getId() +"_" + wallpaperInfo.getName() + ".mp4");
        storageRef.getFile(save).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("download", "That bai");
            }
        }).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.i("download", "Thanh cong");
                videoView.setVideoPath(save.getPath());
                wallpaperInfo.setFileUrl(save.getPath());
                videoView.start();
                videoView.invalidate();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState.savingSharePreferences(wallpaperInfo.getFileUrl());
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemSelected = saveState.restoringSharePreferences();
    }
}
