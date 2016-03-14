package com.inforun.safecitypolice.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.lidroid.xutils.util.LogUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Bitmap与DrawAble与byte[]与InputStream之间的转换,图片压缩，工具类
 *
 * @author Administrator
 */
public class ImageUtils {

    /**
     * 将byte[]转换成InputStream
     *
     * @param b
     * @return
     */
    public static InputStream Byte2InputStream(byte[] b) {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        return bais;
    }

    /**
     * 将InputStream转换成byte[]
     *
     * @param is
     * @return
     */
    public static byte[] InputStream2Bytes(InputStream is) {
        String str = "";
        byte[] readByte = new byte[1024];
        int readCount = -1;
        try {
            while ((readCount = is.read(readByte, 0, 1024)) != -1) {
                str += new String(readByte).trim();
            }
            return str.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Bitmap转换成InputStream
     *
     * @param bm
     * @return
     */
    public static InputStream Bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    /**
     * 将Bitmap转换成InputStream
     *
     * @param bm
     * @param quality
     * @return
     */
    public static InputStream Bitmap2InputStream(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    /**
     * 将InputStream转换成Bitmap
     *
     * @param is
     * @return
     */
    public static Bitmap InputStream2Bitmap(InputStream is) {
        return BitmapFactory.decodeStream(is);
    }

    /**
     * Drawable转换成InputStream
     *
     * @param d
     * @return
     */
    public static InputStream Drawable2InputStream(Drawable d) {
        Bitmap bitmap = drawable2Bitmap(d);
        return Bitmap2InputStream(bitmap);
    }

    /**
     * InputStream转换成Drawable
     *
     * @param is
     * @return
     */
    public static Drawable InputStream2Drawable(InputStream is) {
        Bitmap bitmap = InputStream2Bitmap(is);
        return bitmap2Drawable(bitmap);
    }

    /**
     * Drawable转换成byte[]
     *
     * @param d
     * @return
     */
    public static byte[] Drawable2Bytes(Drawable d) {
        Bitmap bitmap = drawable2Bitmap(d);
        return Bitmap2Bytes(bitmap);
    }

    /**
     * byte[]转换成Drawable
     *
     * @param b
     * @return
     */
    public static Drawable Bytes2Drawable(byte[] b) {
        Bitmap bitmap = Bytes2Bitmap(b);
        return bitmap2Drawable(bitmap);
    }

    /**
     * Bitmap转换成byte[]
     *
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * byte[]转换成Bitmap
     *
     * @param b
     * @return
     */
    public static Bitmap Bytes2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return null;
    }

    /**
     * Drawable转换成Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565
                );
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
        
    }

    /**
     * Bitmap转换成Drawable
     *
     * @param bitmap
     * @return
     */
    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        Drawable d = (Drawable) bd;
        return d;
    }


    private static final int  BITMAP_SIZE = 960;//图片压缩后尺寸,长度或者宽度不大于 BITMAP_SIZE
    /**
     * 压缩图片
     *
     * @param path 图片路径
     */
    public static void compressBitmap(String path) {
        if (TextUtils.isEmpty(path)) {
            LogUtils.d("image not found,can't compress");
            return;
        }
        File file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            return;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options,
                BITMAP_SIZE);
        options.inScaled = true;
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(path, options);
        float newSize = bm.getWidth() >= bm.getHeight() ? bm.getWidth() : bm.getHeight();

        Bitmap tempBit = null;
        if (newSize > BITMAP_SIZE) {
            Matrix matrix = new Matrix();
            float scans = (float) BITMAP_SIZE / (float) newSize;
            matrix.setScale(scans, scans);
            tempBit = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            if (bm != null && !bm.isRecycled()) {
                bm.recycle();
            }
        } else {
            tempBit = bm;
        }
        // 覆盖原有图片
        if (file.exists()) {
            file.delete();
        }
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            tempBit.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (tempBit != null && !tempBit.isRecycled()) {
                tempBit.recycle();
            }
        }
    }

    /**
     * 计算需要压缩的比例
     *
     * @param options
     * @param size
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int size) {
        int inSampleSize = 1;
        int width = options.outWidth;
        int height = options.outHeight;

        int maxCompareSize = width > height ? width : height;
        if (maxCompareSize > size) {
            int result = Math.round(maxCompareSize / size);
            inSampleSize = ((double) maxCompareSize / (double) size) > result ? result + 1
                    : result;
        }
        return inSampleSize;
    }

    /**
     * 保存图片到SDCard
     *
     * @param bitmap
     * @param dirPath
     * @return
     */
    public static boolean saveImage(Bitmap bitmap, String dirPath) {
        if (bitmap == null) {
            return false;
        }
        byte[] buffer = Bitmap2Bytes(bitmap);
        boolean b = FileUtils.writeFile(buffer, dirPath);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return b;
    }
}
