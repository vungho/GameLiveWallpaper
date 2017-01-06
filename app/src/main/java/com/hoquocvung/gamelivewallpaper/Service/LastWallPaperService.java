package com.hoquocvung.gamelivewallpaper.Service;

import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by vungho on 13/12/2016.
 */

public class LastWallPaperService extends WallpaperService {


    @Override
    public Engine onCreateEngine() {
        try {
            Log.i("service", "YES");
            Movie movie = Movie.decodeStream(getResources().getAssets().open("raw/star_guardians.MP4"));
            return new WallEngine(movie);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private class WallEngine extends Engine{
        private final int countFrame = 20;

        private SurfaceHolder holder;
        private Movie movie;
        private boolean visible;
        private Handler handler;

        private WallEngine(Movie movie){
            this.movie = movie;
            handler = new Handler();
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.holder = surfaceHolder;

        }
        private Runnable drawVideo = new Runnable() {
            @Override
            public void run() {
                draw();
            }
        };

        private void draw(){
            if (visible){
                Canvas canvas  = holder.lockCanvas();
                canvas.save();
                canvas.scale(1.3f, 1.3f);

                movie.draw(canvas, -100, 0);
                canvas.restore();

                holder.unlockCanvasAndPost(canvas);
                movie.setTime((int) (System.currentTimeMillis() % movie.duration()));

                handler.removeCallbacks(drawVideo);
                handler.postDelayed(drawVideo, countFrame);
            }
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible){
                handler.post(drawVideo);
            }else {
                handler.removeCallbacks(drawVideo);
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(drawVideo);
        }
    }
}
