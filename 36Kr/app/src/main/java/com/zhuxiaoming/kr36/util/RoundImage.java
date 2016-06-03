package com.zhuxiaoming.kr36.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zhuxiaoming.kr36.R;


/**
 * Created by dllo on 16/4/16.
 */
public class RoundImage extends ImageView {
    private boolean isRound = false;

    public RoundImage(Context context) {
        super(context);
    }

    public RoundImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImage);
        isRound = typedArray.getBoolean(R.styleable.RoundImage_is_round, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isRound) {
            // 需要显示圆形图片
            Drawable drawable = getDrawable();
            if (drawable != null) {
                // 从里面拿到设置的那张图片 ,先转成bitmap格式
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
                // 将它变成圆形图片
                Bitmap out = getCircleBitmap(bitmap1);
                // 绘制到画布上
                Paint paint = new Paint();
                Rect rect = new Rect(0, 0, out.getWidth(), out.getHeight());
                canvas.drawBitmap(out, rect, rect, paint);
            }
        } else {
            super.onDraw(canvas);
            // 需要显示的是正常图片
        }
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        // 先要生成一张空的目标宽高相同的Bitmap
        Bitmap outBitmap = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888);
        // 新建画布,将图片绘制到画布上
        Canvas canvas = new Canvas(outBitmap);
        // 新建画笔,并设置好颜色
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        // 绘制底层的图
        canvas.drawCircle(60, 60, 60, paint);
        // 设置画笔的叠放风格
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 再在上面放上目标Bitmap
        Rect rect = new Rect(0, 0, 120, 120);
        canvas.drawBitmap(bitmap, rect, rect, paint);
        // 输出画出的形状
        return outBitmap;
    }

    /**
     * 相片按自定义组件的比例动态缩放
     * bmp 要缩放的图片
     * width 组件宽度
     * height 组件高度
     */
//    public Bitmap upImageSize(Bitmap bmp, int width, int height) {
//        if (bmp == null) {
//            return null;
//        }
//        // 计算比例
//        float scaleX = (float) width / bmp.getWidth();// 宽的比例
//        float scaleY = (float) height / bmp.getHeight();// 高的比例
//        //新的宽高
//        int newW = 0;
//        int newH = 0;
//        if (scaleX > scaleY) {
//            newW = (int) (bmp.getWidth() * scaleX);
//            newH = (int) (bmp.getHeight() * scaleX);
//        } else if (scaleX <= scaleY) {
//            newW = (int) (bmp.getWidth() * scaleY);
//            newH = (int) (bmp.getHeight() * scaleY);
//        }
//        return Bitmap.createScaledBitmap(bmp, newW, newH, true);
//    }

}
