package com.cs.test_others.Imageloader3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by chenshuai on 2017/2/3.
 * 2.开闭原则（OCP）
 * 定义：软件中的对象（类、模块、函数等）应该对于扩展是开放的，但是对于修改是封闭的。
 *
 */

public class DiskCache {
    static String cacheDir="sdcard/cache/";

    //将图片缓存到内存中
    public void put(String url,Bitmap bitmap){
        FileOutputStream fileoutputStream=null;
        try {
            fileoutputStream = new FileOutputStream(cacheDir + url);

            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileoutputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (fileoutputStream != null) {
                try {
                    fileoutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //从缓存中获取图片
    public Bitmap get(String url){
        return BitmapFactory.decodeFile(cacheDir+url);
    }


}
