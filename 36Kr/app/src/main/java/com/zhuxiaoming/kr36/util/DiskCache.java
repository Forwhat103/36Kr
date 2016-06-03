package com.zhuxiaoming.kr36.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zhuxiaoming on 16/4/12.
 * 硬盘缓存类
 */
public class DiskCache {
    String cacheDir;// 文件缓存的路径

    public DiskCache(Context context) {
        //初始化文件的目录
        cacheDir = context.getCacheDir() + "/Image/";
    }

    public Bitmap getBitmap(String url) {
        // 将图片的url转成MD5格式
        url = MD5Util.getMD5String(url);
        return BitmapFactory.decodeFile(cacheDir + url + ".png");
    }

    public void putBitmap(String url, Bitmap bitmap) {
        // 先获取Image文件夹
        File file = new File(cacheDir);
        if (!file.exists()) {
            // 如果该文件夹不存在,就新建一个文件夹
            file.mkdir();
        }
        // 处理一个文件名,从网址转成MD5
        url = MD5Util.getMD5String(url);
        File imageFile = new File(cacheDir, url + ".png");
        if (!imageFile.exists()) {
            // 如果图片不存在 就开始保存图片
            FileOutputStream fileOutputStream = null;
            try {
                // 创建imageFile这个文件
                imageFile.createNewFile();
                // 将输出流和刚创建的文件联系起来,之后所有通过该输出流输出的数据都会写入这个文件
                fileOutputStream = new FileOutputStream(imageFile);
                // 该方法将bitmap转换成fileOutputSteam,格式是png
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
