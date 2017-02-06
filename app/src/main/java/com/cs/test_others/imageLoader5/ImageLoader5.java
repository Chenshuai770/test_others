package com.cs.test_others.imageLoader5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenshuai on 2017/2/3.
 */

public class ImageLoader5 {
    //图片缓存
    ImageCache5 mImageCache=new MemoryCache();
    //线程池,线程数量是cpu的数量 创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程。
    //Runtime.getRuntime().availableProcessors()获得线程数量//http://www.yiibai.com/java/lang/runtime_availableprocessors.html
    //ExecutorService 的方法说明 http://www.cnblogs.com/wanqieddy/p/3853863.html
    ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //注入缓存实现
    public void setImageCache(ImageCache5 cache){
        mImageCache=cache;
    }

    //显示图片
    public void dispalyImage(final String url, final ImageView imageView){
        imageView.setTag(url);
        //线程池添加异步新的队列
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                //通过downloadImage()方法获取我们需要的bitmap对象用imageview来显示
                Bitmap bitmap = downloadImage(url);
                if (bitmap==null) {
                    return;
                }
                if (imageView.getTag().equals(url)){
                    imageView.setImageBitmap(bitmap);
                }
                //写入缓存
                mImageCache.put(url,bitmap);
            }
        });
    }

    //从网络下载图片
    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap=null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            //直接转流
            bitmap= BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
