package com.cs.test_others.imageLoader4;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenshuai on 2017/2/3.
 * 2.开闭原则（OCP）
 * 定义：软件中的对象（类、模块、函数等）应该对于扩展是开放的，但是对于修改是封闭的。
 * 劲量用拓展的方式来实现代码,而不是通过修改代码
 */

public class ImageLoader4 {
    ImageCache4 mImageCache4=new ImageCache4();
    DiskCache4 mDiskCache4=new DiskCache4();

    //是否使用sd缓存
    boolean isUseDiskCache=false;
    //是否使用双缓存
    boolean isUseDoubleCache=false;

    DoubleCache mDoubleCache4=new DoubleCache();

    //线程池
    //线程池,线程数量是cpu的数量 创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程。
    ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //显示图片
    public void dispalyImage(final String url, final ImageView imageView){
        Bitmap bitmap=null;
        if (isUseDoubleCache) {
            bitmap=mDoubleCache4.get(url);
        }else if(isUseDiskCache){
            bitmap=mDiskCache4.get(url);
        }else {
            mImageCache4.get(url);
        }
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
        //没有缓存,交给线程池异步下载图片

    }
    public void useDoubleCache(boolean useDoubleCache){
        isUseDoubleCache=useDoubleCache;
    }
    public void useDiskCache(boolean useDiskCache){
        isUseDiskCache=useDiskCache;
    }

}
