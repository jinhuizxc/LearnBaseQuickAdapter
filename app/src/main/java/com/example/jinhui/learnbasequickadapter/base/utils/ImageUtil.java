package com.example.jinhui.learnbasequickadapter.base.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Image图片处理工具类
 * Created by zsr on 2016/5/26.
 */
public class ImageUtil {


    public static final float DISPLAY_WIDTH = 600;
    public static final float DISPLAY_HEIGHT = 800;

    /**
     * 从path中获取图片信息,并根据设定值进行压缩
     *
     * @param path
     * @return
     */
    public static Bitmap decodeBitmap(String path) {
        BitmapFactory.Options op = new BitmapFactory.Options();
        //inJustDecodeBounds
        //If set to true, the decoder will return null (no bitmap), but the out…
        op.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(path, op); //获取尺寸信息
        //获取比例大小
        int wRatio = (int) Math.ceil(op.outWidth / DISPLAY_WIDTH);
        int hRatio = (int) Math.ceil(op.outHeight / DISPLAY_HEIGHT);
        //如果超出指定大小，则缩小相应的比例
        if (wRatio > 1 && hRatio > 1) {
            if (wRatio > hRatio) {
                op.inSampleSize = wRatio;
            } else {
                op.inSampleSize = hRatio;
            }
        }
        op.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeFile(path, op);
        return bmp;
    }

    /**
     * View 转化为Bitmap
     * //要在子线程内读取
     *
     * @param view
     * @return
     */

    public static Bitmap convertViewToBitmap(View view) {
        //当所需要的drawingCache >系统所提供的最大DrawingCache值时，生成Bitmap就会出现问题，此时获取的Bitmap就为null。
        //所以需要修改所需的cache值
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        //获取缓存
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        //清空画图缓冲区
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }


    /**
     * 从一个View中获取Bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap getBitmapFromView(View view) {
        // Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        // Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        // Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            // has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            // does not have background drawable, then draw white background on
            // the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        // return the bitmap
        return returnedBitmap;
    }


    //图片不被压缩
    public static Bitmap convertViewToBitmap(View view, int bitmapWidth, int bitmapHeight) {
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));

        return bitmap;
    }


}
