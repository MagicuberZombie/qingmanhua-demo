package com.qf.demo.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/11/3.
 */
public class Http {
    public static String CALLBACKOK = "1";
    public static String IOERR = "-999";
    public static String SEVERERR = "-998";
    public static final String IMAGE_UNSPECIFIED = "image/*";
    public static final String LOADUPHEADIMGHTTP = "http://u.nikankan.com/api.php?" +
            "m=QmUser" +
            "&a=alteruserphoto" +
            "&uid=";
    private static Http mInstance = null;

    private Http() {

    }

    public static Http getInstance() {
        if (mInstance == null) {
            mInstance = new Http();
        }
        return mInstance;
    }


    public String httpForGet(String getUrl) {
        try {
            URL url = new URL(getUrl);
            HttpURLConnection conn = (HttpURLConnection)
                    url.openConnection();
            conn.setConnectTimeout(50000);
            if (conn.getResponseCode() == 200) {
                InputStream in = conn.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int hasRead = 0;
                byte[] buf = new byte[1024];
                while ((hasRead = in.read(buf)) != -1) {
                    bos.write(buf, 0, hasRead);
                }
                return new String(bos.toByteArray());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            return IOERR;
        }
        return SEVERERR;
    }

    public String upLoadFile(String uid, String filePath) {
        DataOutputStream dos = null;
        FileInputStream fin = null;
        InputStream in = null;
        try {
            URL url = new URL(LOADUPHEADIMGHTTP + uid);
            HttpURLConnection conn = (HttpURLConnection)
                    url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + "******");

            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes("--" + "******" + "\r\n");
            dos.writeBytes("Content-Disposition:form-data;name=\"uploadedfile\";filename=\"" + uid + ".jpg" + "\"" + "\r\n");
            dos.writeBytes("\r\n");
            fin = new FileInputStream(new File(filePath));
            int hasRead = 0;
            byte[] buf = new byte[1024];
            while ((hasRead = fin.read(buf)) != -1) {
                dos.write(buf, 0, hasRead);
            }
            dos.writeBytes("\r\n");
            dos.writeBytes("--" + "******" + "--" + "\r\n");
            dos.flush();
            in = conn.getInputStream();
            StringBuffer sb = new StringBuffer();
            while ((hasRead = in.read(buf)) != -1) {
                sb.append(new String(buf, 0, hasRead));
            }
            return sb.toString().trim();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
