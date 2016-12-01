package com.qf.demo.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 工具类
 * Created by ZombieFan on 2016/11/6.
 */
public class Utils {
    private static Utils mInstance = null;

    private Utils() {

    }

    public static Utils getInstance() {
        if (mInstance == null) {
            mInstance = new Utils();
        }
        return mInstance;
    }

    /**
     * 判断文本内容是否为空
     *
     * @param text
     * @return
     */
    public boolean textIsNull(String... text) {
        for (int i = 0; i < text.length; i++) {
            if (TextUtils.isEmpty(text[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将文本Toast出来
     */
    public void toast(String text, Context context) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 将时间戳转化为时间
     *
     * @param timeVal
     * @return
     */
    public String getTime(String timeVal) {
        long time = Long.parseLong(timeVal + "000");
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd hh:mm", Locale.CHINA);
        String timeStr = simpleDateFormat.format(date);
        return timeStr;
    }

}
