package com.hoquocvung.gamelivewallpaper.Service;



import android.util.Log;

import com.hoquocvung.gamelivewallpaper.Performance.WallpaperRenderer;


import org.rajawali3d.surface.IRajawaliSurface;
import org.rajawali3d.wallpaper.Wallpaper;
/**
 * Created by vungho on 13/12/2016.
 */

public class WallPaperService extends Wallpaper {

    private WallpaperRenderer mRenderer;
    @Override
    public Engine onCreateEngine() {
        Log.i("service", "Start");
        mRenderer = new WallpaperRenderer(this);
        return new WallpaperEngine(getBaseContext(), mRenderer, IRajawaliSurface.ANTI_ALIASING_CONFIG.NONE);
    }
}
