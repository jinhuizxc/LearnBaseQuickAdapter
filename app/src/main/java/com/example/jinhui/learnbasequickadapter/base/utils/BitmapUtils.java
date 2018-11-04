package com.example.jinhui.learnbasequickadapter.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhusr on 2017/7/11.
 */

public class BitmapUtils {

    /**
     * 屏幕分辨率和指定清晰度的图片压缩方法
     *
     * @param context
     * @param image   Bitmap图片
     * @return
     */
    public static Bitmap comp(Context context, Bitmap image) {
        int maxLength = 1024 * 1024; // 预定的图片最大内存，单位byte
        // 压缩大小
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length > maxLength) { // 循环判断，大于继续压缩
            options -= 10;// 每次都减少10
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//PNG 压缩options%
        }
        // 压缩尺寸
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options opts = new BitmapFactory.Options(); // 选项对象(在加载图片时使用)
        opts.inJustDecodeBounds = true; // 修改选项, 只获取大小
        BitmapFactory.decodeStream(bais, null, opts);// 加载图片(只得到图片大小)
        // 获取屏幕大小，按比例压缩
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int scaleX = opts.outWidth / manager.getDefaultDisplay().getWidth(); // X轴缩放比例(图片宽度/屏幕宽度)
        int scaleY = opts.outHeight / manager.getDefaultDisplay().getHeight(); // Y轴缩放比例
        int scale = scaleX > scaleY ? scaleX : scaleY; // 图片的缩放比例(X和Y哪个大选哪个)

        opts.inJustDecodeBounds = false; // 修改选项, 不只解码边界
        opts.inSampleSize = scale > 1 ? scale : 1; // 修改选项, 加载图片时的缩放比例
        return BitmapFactory.decodeStream(bais, null, opts); // 加载图片(得到压缩后的图片)
    }

    /**
     * 屏幕分辨率和指定清晰度的图片压缩方法
     *
     * @param context
     * @param path    图片的路径
     * @return
     */
    public static Bitmap comp(Context context, String path) {
        return compressImage(getUsableImage(context, path));
    }

    /**
     * 获取屏幕分辨率的Bitmap
     *
     * @param context
     * @param path    图片的路径
     * @return
     */
    public static Bitmap getUsableImage(Context context, String path) {
        BitmapFactory.Options opts = new BitmapFactory.Options(); // 选项对象(在加载图片时使用)
        opts.inJustDecodeBounds = true; // 修改选项, 只获取大小
        BitmapFactory.decodeFile(path, opts); // 加载图片(只得到图片大小)
        DisplayMetrics metrics = new DisplayMetrics();
        metrics = context.getApplicationContext().getResources().getDisplayMetrics();
        int scaleX = opts.outWidth / metrics.widthPixels; // X轴缩放比例(图片宽度/屏幕宽度)
        int scaleY = opts.outHeight / metrics.heightPixels; // Y轴缩放比例
        int scale = scaleX > scaleY ? scaleX : scaleY; // 图片的缩放比例(X和Y哪个大选哪个)

        opts.inJustDecodeBounds = false; // 修改选项, 不只解码边界
        opts.inSampleSize = scale > 1 ? scale : 1; // 修改选项, 加载图片时的缩放比例
        return BitmapFactory.decodeFile(path, opts); // 加载图片(得到缩放后的图片)
    }

    /**
     * 压缩图片清晰度，到指定大小
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        int maxLength = 1024 * 1024; // (byte)

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length > maxLength) { // 循环判断如果压缩后图片是否大于1mb,大于继续压缩
            options -= 10;// 每次都减少10
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 指定分辨率和清晰度的图片压缩方法
     *
     * @param fromFile
     * @param toFile
     * @param reqWidth
     * @param reqHeight
     * @param quality
     */
    public static void transImage(String fromFile, String toFile, int reqWidth, int reqHeight, int quality) {
        Bitmap bitmap = BitmapFactory.decodeFile(fromFile);
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        // 缩放的尺寸
        float scaleWidth = (float) reqWidth / bitmapWidth;
        float scaleHeight = (float) reqHeight / bitmapHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 产生缩放后的Bitmap对象
        Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);
        // 保存到文件
        bitmap2File(toFile, quality, resizeBitmap);
        if (!bitmap.isRecycled()) {
            // 释放资源，以防止OOM
            bitmap.recycle();
        }
        if (!resizeBitmap.isRecycled()) {
            resizeBitmap.recycle();
        }
    }

    /**
     * 保存Bitmap为png图片到指定位置
     *
     * @param path
     * @param bitmap
     * @param bitName 图片名称
     * @throws IOException
     */
    public static void saveBitmapToPath(String path, Bitmap bitmap, String bitName) {

        FileUtils.makeRootDirectory(path);

        File file = new File(path + bitName);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Bitmap转换为文件
     *
     * @param toFile
     * @param quality
     * @param bitmap
     * @return
     */
    public static File bitmap2File(String toFile, int quality, Bitmap bitmap) {
        File captureFile = new File(toFile);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(captureFile);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return captureFile;
    }

    /**
     * Drawable转换为Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitamp(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    // Bitmap、Drawable、InputStream、byte[] 之间转换

    /**********************************************************/
    // 1. Bitmap to InputStream
    public static InputStream bitmap2Input(Bitmap bitmap, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public static InputStream bitmap2Input(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    // 2. Bitmap to byte[]
    public static byte[] bitmap2ByteArray(Bitmap bitmap, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos);
        return baos.toByteArray();
    }

    public static byte[] bitmap2ByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    // 3. Drawable to byte[]
    public static byte[] drawable2ByteArray(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        return out.toByteArray();
    }

    // 4. byte[] to Bitmap
    public static Bitmap byteArray2Bitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
