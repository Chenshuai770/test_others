package com.cs.test_others.imageLoader4;

import android.graphics.Bitmap;

/**
 * Created by chenshuai on 2017/2/3.
 * 2.开闭原则（OCP）
 * 定义：软件中的对象（类、模块、函数等）应该对于扩展是开放的，但是对于修改是封闭的。
 * 双缓存.获取图片是先从sd卡获取缓存,若果内存中没有缓存该图片,再从sd卡中缓存
 * 缓存图片也是在内存和sd卡中个缓存一部分
 */

public class DoubleCache {
    ImageCache4 imageCache4=new ImageCache4();
    DiskCache4 diskCache4 = new DiskCache4();
    //实现了双缓存
    public Bitmap get(String url){
        Bitmap bitmap = imageCache4.get(url);
        if (bitmap == null) {
            bitmap=diskCache4.get(url);
        }
        return bitmap;
    }
    public void put(String url,Bitmap bitmap) {
        imageCache4.put(url, bitmap);
        diskCache4.put(url, bitmap);
    }
}
