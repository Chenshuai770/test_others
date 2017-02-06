package com.cs.test_others.imageLoader5;

import android.graphics.Bitmap;

/**
 * Created by chenshuai on 2017/2/3.
 * sd卡缓存
 */

public class DiskCache5 implements ImageCache5 {
    @Override
    public Bitmap get(String url) {
        //从本地文件中获取缓存
        return null;
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        //将文件写入bitmap文件中

    }
}
