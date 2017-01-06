package com.hoquocvung.gamelivewallpaper.Performance;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import org.rajawali3d.Object3D;
import org.rajawali3d.lights.PointLight;
import org.rajawali3d.loader.ALoader;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.loader.async.IAsyncLoaderCallback;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.methods.SpecularMethod;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.StreamingTexture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Cube;
import org.rajawali3d.primitives.Plane;
import org.rajawali3d.renderer.RajawaliRenderer;

import java.io.IOException;


/**
 * Created by vungho on 15/12/2016.
 */

public class WallpaperRenderer extends RajawaliRenderer{

    private MediaPlayer mMediaPlayer;
    private StreamingTexture mVideoTexture;
    private SaveState saveState;

    public WallpaperRenderer(Context context) {
        super(context);
        saveState = new SaveState(context);
    }

    @Override
    protected void initScene() {
        String videoPath = saveState.restoringSharePreferences();
        Log.i("videopath", videoPath);
        PointLight pointLight = new PointLight();
        pointLight.setPower(1);
        pointLight.setPosition(-1, 1, 4);

        getCurrentScene().addLight(pointLight);
        getCurrentScene().setBackgroundColor(0xff040404);

        try {
            Object3D android = new Cube(2.0f);
            Material material = new Material();
            material.enableLighting(true);
            material.setDiffuseMethod(new DiffuseMethod.Lambert());
            material.setSpecularMethod(new SpecularMethod.Phong());
            android.setMaterial(material);
            android.setColor(0xff99C224);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }


        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource("file://" +videoPath);
            mMediaPlayer.prepareAsync();
            if (mMediaPlayer == null){
                Log.i("media", "NULL");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMediaPlayer.setLooping(true);
        mVideoTexture = new StreamingTexture("GLW", mMediaPlayer);
        Material material = new Material();
        material.setColorInfluence(0);
        try {
            material.addTexture(mVideoTexture);
        } catch (ATexture.TextureException e) {
            e.printStackTrace();
        }

        Plane screen = new Plane(4, 3, 4, 3, Vector3.Axis.Z);
        screen.setMaterial(material);

        screen.setX(.1f);
        screen.setY(-.2f);
        screen.setZ(1.5f);

        getCurrentScene().addChild(screen);
        getCurrentCamera().enableLookAt();
        getCurrentCamera().setLookAt(0, 0, 0);

        mMediaPlayer.start();


    }

    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);
        mVideoTexture.update();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMediaPlayer != null)
            mMediaPlayer.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMediaPlayer != null)
            mMediaPlayer.start();
    }


    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }
}
