package com.cs.test_others.imageLoader4;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

/**
 * 
 * Created by chenshuai on 2017/2/3.
 * 1.单一职责原则（SRP）
 * 简单的说就是：一个类中应该是一组相关性很高的函数、数据的封装。两个不一样的功能不应该放在一个类中。
 * 这个
 */
 

public class ImageCache4 {
    //图片缓存
    LruCache<String,Bitmap> mImageCache;
    public ImageCache4() {
        initImageCache();
    }

    private void initImageCache() {
        //计算可用的最大内存//http://blog.csdn.net/wgw335363240/article/details/8878644
        int maxMemory= (int) (Runtime.getRuntime().maxMemory()/1024);
        //取四分之一可用内存做为缓存
        int  cacheSize=maxMemory/4;
        mImageCache=new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                //bitmap 大小链接 http://blog.csdn.net/quanshuai0225/article/details/47104251
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
                    return bitmap.getAllocationByteCount()/1024;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
                    return bitmap.getByteCount()/1024;
                }
                //Bitmap所占用的内存空间数等于Bitmap的每一行所占用的空间数乘以Bitmap的行数
                return bitmap.getRowBytes()*bitmap.getHeight()/1024;
                //因为getByteCount要求的API版本较高，考虑到兼容性使用上面的方法
            }
        };

    }
    //将图片和缓存存入内存
    public void put(String url,Bitmap bitmap){
        mImageCache.put(url,bitmap);
    }
    //获取缓存url
    public Bitmap get(String url){
        return mImageCache.get(url);
    }
}
