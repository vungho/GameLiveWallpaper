package com.hoquocvung.gamelivewallpaper.Performance;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;

import java.io.IOException;

/**
 * Created by vungho on 20/12/2016.
 */

public class VideoRequestHandler extends RequestHandler {
    public String SCHEME_VIDEO ="video";

    @Override
    public boolean canHandleRequest(Request data) {
        String s = data.uri.getScheme();
        return (SCHEME_VIDEO.equals(s));
    }

    @Override
    public Result load(Request request, int networkPolicy) throws IOException {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail
                (request.uri.getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
        return new Result(bitmap, Picasso.LoadedFrom.DISK);
    }
}
