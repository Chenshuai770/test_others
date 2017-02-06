package com.cs.test_others.imageLoader5;

import android.graphics.Bitmap;

/**
 * Created by chenshuai on 2017/2/3.
 */

public interface ImageCache5 {
    public Bitmap get(String url);
    public void put(String url,Bitmap bitmap);
}
