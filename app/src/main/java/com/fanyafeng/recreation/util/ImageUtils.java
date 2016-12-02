package com.fanyafeng.recreation.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {


    public static boolean save(Context context, String path, Bitmap src, int quality, Bitmap.CompressFormat format) {

        boolean ret = false;
        File file = new File(path);

        FileOutputStream out;

        try {
            out = new FileOutputStream(file);
            ret = src.compress(format, quality, out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return ret;
    }


    public static Bitmap getRoundImage(Bitmap oriImg, boolean recycleOld) {

        Bitmap targetBitmap = null;

        if (oriImg != null && !oriImg.isRecycled()) {
            int size = Math.min(oriImg.getWidth(), oriImg.getHeight());
            int targetWidth = size;
            int targetHeight = size;

            targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(targetBitmap);

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawCircle(targetWidth / 2f, targetHeight / 2f, targetWidth / 2f, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(oriImg, new Rect(0, 0, size, size), new Rect(0, 0, targetWidth, targetHeight), paint);

            if (recycleOld) {
                oriImg.recycle();
            }
        }
        return targetBitmap;
    }

    public static Bitmap getRoundImageFromCenter(Bitmap oriImg, boolean recycleOld) {

        Bitmap targetBitmap = null;

        if (oriImg != null && !oriImg.isRecycled()) {
            int width = oriImg.getWidth();
            int height = oriImg.getHeight();
            Log.d("TAG", "width" + width + "height" + height);
            int size = Math.min(width, height);
//            if (size == height) {
//                oriImg = Bitmap.createBitmap(oriImg, (width / 2 - height / 2), 0, size, size,null,false);
//            } else if (size == width) {
//                oriImg = Bitmap.createBitmap(oriImg, 0, (height / 2 - width / 2), size, size,null,false);
//            }
            targetBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(targetBitmap);

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setDither(true);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(oriImg, new Rect((oriImg.getWidth() - size) / 2, (oriImg.getHeight() - size) / 2, (oriImg.getWidth() + size) / 2, (oriImg.getHeight() + size) / 2), new Rect(0, 0, size, size), paint);

            if (recycleOld) {
                oriImg.recycle();
            }
        }
        return targetBitmap;
    }


    /**
     * 第二个参数为圆角的角度
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap getRoundAngleImage(Bitmap bitmap, int pixels, boolean recycleOld) {
        Bitmap output = null;
        if (bitmap != null && !bitmap.isRecycled()) {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = pixels;

            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            if (recycleOld)
                bitmap.recycle();
            return output;
        }
        return output;
    }

    public static Bitmap clipImage(Bitmap top, Bitmap bottom, Bitmap background) {
        Bitmap targetBitmap = null;

        if (top != null && !top.isRecycled() && bottom != null && !bottom.isRecycled()) {
            int w1 = bottom.getWidth();
            int h1 = bottom.getHeight();

            int w2 = top.getWidth() * background.getHeight() / top.getHeight();
            int h2 = background.getHeight();

            targetBitmap = Bitmap.createBitmap(w2, h2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(targetBitmap);

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);

            int x = h1 * w2 / h2;

            if (x > w1) {
                x = w1;
            }

            canvas.drawBitmap(bottom, new Rect(0, 0, x, bottom.getHeight()), new Rect(0, 0, w2, h2), paint);
            canvas.drawBitmap(top, null, new Rect(0, 0, w2, h2), paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

            canvas.drawBitmap(background, 0, 0, paint);
//            top.recycle();
//            bottom.recycle();
        }
        return targetBitmap;
    }

    public static Bitmap loadLocalBitmap(Context context, String imgUrl) {
        Bitmap bitmap = null;
        try {
            File file = new File(imgUrl);
            if (file.exists())
                bitmap = BitmapFactory.decodeFile(imgUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
