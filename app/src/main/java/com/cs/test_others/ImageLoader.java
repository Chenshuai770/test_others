package com.cs.test_others;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
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
 * 这个
 */

public class ImageLoader {


    //图片缓存
    LruCache<String,Bitmap> mImageCache;
    //线程池,线程数量是cpu的数量 创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程。
    //Runtime.getRuntime().availableProcessors()获得线程数量//http://www.yiibai.com/java/lang/runtime_availableprocessors.html
    //ExecutorService 的方法说明 http://www.cnblogs.com/wanqieddy/p/3853863.html
    ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //调用单列的时候已经获取了大小
    public ImageLoader() {
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
               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
                    return bitmap.getAllocationByteCount()/1024;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
                    return bitmap.getByteCount()/1024;
                }*/
                //Bitmap所占用的内存空间数等于Bitmap的每一行所占用的空间数乘以Bitmap的行数
                return bitmap.getRowBytes()*bitmap.getHeight()/1024;
                //因为getByteCount要求的API版本较高，考虑到兼容性使用上面的方法
            }
        };
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
