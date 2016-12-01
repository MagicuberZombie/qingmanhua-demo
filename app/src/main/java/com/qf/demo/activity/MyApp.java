package com.qf.demo.activity;

import android.app.Application;
import android.content.SharedPreferences;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by ZombieFan on 2016/11/6.
 */
public class MyApp extends Application {
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    public void onCreate() {
        super.onCreate();
        ShareSDK.initSDK(getApplicationContext(), "18d205868d050");
        mPreferences = getSharedPreferences("account", MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public SharedPreferences.Editor getEditor() {
        return mEditor;
    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }
}
