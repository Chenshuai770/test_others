package com.cs.test_others.imageloader2;

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
 * 1.单一职责原则（SRP）
 * 简单的说就是：一个类中应该是一组相关性很高的函数、数据的封装。两个不一样的功能不应该放在一个类中。
 */

public class Imageloader2 {
    //图片缓存
    ImageCache mImageCache=new ImageCache();
    ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //显示图片
    public void dispalyImage(final String url, final ImageView imageView){
        final Bitmap bitmap = mImageCache.get(url);
        //如果有缓存,直接从缓存取出图片,否则进行网络加载
        if (bitmap!=null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        //标记imageview已经设置了标签
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap1=downloadImage(url);
                if (bitmap1 == null) {
                    return;
                }
                //这一步是当第一次进来的时候去检查,为了对应下一步
                if (imageView.getTag().equals(url)){
                    imageView.setImageBitmap(bitmap1);
                }
                //加载完后去缓存
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
