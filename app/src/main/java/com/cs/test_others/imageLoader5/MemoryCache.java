package com.cs.test_others.imageLoader5;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by chenshuai on 2017/2/3.
 * 内存缓存
 */

public class MemoryCache implements ImageCache5 {
    private LruCache<String ,Bitmap> mMemoryCache;
    public MemoryCache() {
        //初始化lru缓存

    }

    @Override
    public Bitmap get(String url) {
        return mMemoryCache.get(url);
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        mMemoryCache.put(url,bitmap);

    }
}
