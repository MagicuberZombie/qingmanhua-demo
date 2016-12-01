package com.qf.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

/**
 * Created by Administrator on 2016/11/3.
 */
public class PhoneTools {
    private static PhoneTools mInstance = null;

    private PhoneTools() {

    }

    public static PhoneTools getInstance() {
        if (mInstance == null) {
            mInstance = new PhoneTools();
        }
        return mInstance;
    }

    /**
     * 获取手机Mac地址
     *
     * @param context
     * @return
     */
    public String getMacAddress(Context context) {
        String str = "000000000000";
        try {
            WifiManager wifiManager = (WifiManager) context.
                    getSystemService(Context.WIFI_SERVICE);
            WifiInfo info;
            if (wifiManager == null) {
                info = wifiManager.getConnectionInfo();
                if (!TextUtils.isEmpty(info.getMacAddress())) {
                    str = info.getMacAddress().replace(":", "");
                }
            }
        } catch (Exception e) {
            return str;
        }
        return str;
    }

    /**
     * 获取手机屏幕大小
     *
     * @param context
     * @return
     */
    public Point getPhoneScreenSize(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) (context)).getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        Point point = new Point();
        point.x = metrics.widthPixels;
        point.y = metrics.heightPixels;
        return point;
    }

    /**
     * 将dp单位转换为像素单位
     *
     * @param context
     * @param dipVal
     * @return
     */
    public int dip2px(Context context, float dipVal) {
        return (int) (0.5f + dipVal * context.getResources().getDisplayMetrics().density);
    }


    /**
     * 图片的二次采样
     *
     * @param filePath
     * @param newHeight
     * @param newWidth
     * @return
     */
    public Bitmap createImageThumbnail(String filePath,
                                       int newHeight, int newWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int oldHeight = options.outHeight;
        int oldWidth = options.outWidth;

        int ratioHeight = oldHeight / newHeight;
        int ratioWidth = oldWidth / newWidth;

        options.inSampleSize = ratioHeight > ratioWidth ? ratioWidth : ratioHeight;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        return bitmap;
    }
}
