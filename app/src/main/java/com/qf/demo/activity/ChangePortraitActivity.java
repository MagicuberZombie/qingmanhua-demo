package com.qf.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qf.demo.R;
import com.qf.demo.http.Http;
import com.qf.demo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 修改头像的页面
 */
public class ChangePortraitActivity extends Activity implements View.OnClickListener {

    private TextView openAlbumTv;
    private TextView openCameraTv;

    private MyApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_portrait);
        app = (MyApp) getApplication();
        init();
    }

    /**
     * 控制初始化
     */
    private void init() {
        initView();
        initData();
        setListener();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        imgUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg"));
    }

    /**
     * 设置监听
     */
    private void setListener() {
        openAlbumTv.setOnClickListener(this);
        openCameraTv.setOnClickListener(this);
    }

    /**
     * 寻找控件
     */
    private void initView() {
        openAlbumTv = (TextView) findViewById(R.id.tv_open_album);
        openCameraTv = (TextView) findViewById(R.id.tv_open_camera);
    }


    private static final int PICK_RESULT = 0;
    private static final int CAPTURE_RESULT = 1;
    private static final int PIC_BACK = 2;
    //用来保存拍摄图片的
    private Uri imgUri;

    /**
     * 处理点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_open_album:
                Intent getPicIntent = new Intent(Intent.ACTION_PICK, MediaStore
                        .Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(getPicIntent, PICK_RESULT);
                break;
            case R.id.tv_open_camera:
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                startActivityForResult(captureIntent, CAPTURE_RESULT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_RESULT:
                if (resultCode == RESULT_OK && data != null) {
                    Uri uri = data.getData();
                    startPhotoXZoom(uri);
                }
                break;
            case CAPTURE_RESULT:
                if (resultCode == RESULT_OK) {
                    startPhotoXZoom(imgUri);
                }
                break;
            case PIC_BACK:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap bitmap = bundle.getParcelable("data");
                        if (bitmap != null) {
                            try {
                                String picPath = Environment.getExternalStorageDirectory() + "/temp2.png";
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(
                                        new File(picPath)));
                                sendToHttp(picPath);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        }
    }

    /**
     * 用于裁剪图片
     *
     * @param uri
     */
    private void startPhotoXZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PIC_BACK);
    }


    /**
     * 上传图片
     *
     * @param picPath
     */
    private void sendToHttp(final String picPath) {
        final String id = app.getPreferences().getString("id", "");
        new Thread() {
            @Override
            public void run() {
                String result = Http.getInstance().upLoadFile(id, picPath);
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.optInt("result") == 1) {
                        mHandler.sendEmptyMessage(0x123);
                    } else {
                        mHandler.sendEmptyMessage(0x100);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private Handler mHandler = new MyHandler();

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x123:
                    Utils.getInstance().toast("上传头像成功", ChangePortraitActivity.this);
                    setResult(RESULT_OK);
                    finish();
                    break;
                case 0x100:
                    Utils.getInstance().toast("上传头像失败", ChangePortraitActivity.this);
                    break;
            }
        }
    }
}
