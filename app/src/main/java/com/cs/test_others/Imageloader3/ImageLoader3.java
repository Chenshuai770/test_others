package com.cs.test_others.Imageloader3;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenshuai on 2017/2/3.
 *  缓存优先使用内存缓存,如果内存缓存没有图片则再使用sd缓存,如果sd缓存也没有图片则使用网络下载 双缓存
 * 这个缓存系列中是只能有单缓存
 *  Bitmap bitmap=isUseDiskCache?mDiskCache.get(url):mImageCache3.get(url);
 */

public class ImageLoader3 {
    //内存缓存
    ImageCache3 mImageCache3=new ImageCache3();
    //sd卡缓存
    DiskCache mDiskCache=new DiskCache();
    //是否可用sd卡缓存 disk磁盘
    boolean isUseDiskCache=false;
    //线程池,线程数量是cpu的数量 创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程。
    ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //显示图片
    public void dispalyImage(final String url, final ImageView imageView){
        Bitmap bitmap=isUseDiskCache?mDiskCache.get(url):mImageCache3.get(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        //没有缓存则交给线程池去下载


    }
    public void useDiskCache(boolean useDiskCache){
        isUseDiskCache=useDiskCache;
    }


}
