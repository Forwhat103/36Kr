package com.zhuxiaoming.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by zhuxiaoming on 16/5/30.
 */
public class MyDrawable extends Drawable {
    private Paint paint;// 画笔
    private Bitmap bitmap;// 我们要操作的Bitmap
    private RectF rectF;// 矩形

    public MyDrawable(Bitmap bitmap) {
        this.bitmap = bitmap;
        paint = new Paint();
        paint.setAntiAlias(true);// 抗锯齿
        // 位图渲染器 (参数:1.我们要操作的Bitmap; 2.X轴图片的填充类型; 3.Y轴图片的填充类型)
        // 填充类型分三种: 1.REPEAT:重复; 2.CLAMP :拉伸; 3.:ERROR:镜像;
        // 注意:这里的CLAMP指的是拉伸图片的最后一个像素
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        paint.setShader(shader);
    }

    /**
     * 获取高度
     */
    @Override
    public int getIntrinsicHeight() {
        return bitmap.getHeight();
    }

    /**
     * 获取宽度
     */
    @Override
    public int getIntrinsicWidth() {
        return bitmap.getWidth();
    }

    /**
     * 这个方法是指drawable将被绘制在画布上的区域
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        rectF = new RectF(left, top, right, bottom);
    }


    /**
     * 这是我们核心的方法,绘制我们想要的图片
     */
    @Override
    public void draw(Canvas canvas) {
        // 参数: 绘制的区域; X轴圆角半径; Y轴圆角半径; 画笔
//        canvas.drawRoundRect(rectF, 100000, 100000, paint);
        // 画圆
        canvas.drawCircle(getIntrinsicWidth() / 2, getIntrinsicHeight() / 2, getIntrinsicWidth() / 2, paint);
    }

    /**
     * 设置透明度
     *
     * @param alpha
     */
    @Override
    public void setAlpha(int alpha) {

    }

    /**
     * 设置滤镜渲染颜色
     *
     * @param colorFilter
     */
    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    /**
     * 获取透明度
     *
     * @return
     */
    @Override
    public int getOpacity() {
        return 0;
    }
}
