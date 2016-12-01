package com.qf.demo.entities;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/11/3.
 */
public class UserData {
    private boolean isLogin = false;
    private int vip;
    private Bitmap mPic = null;
    private String user_id;
    private String account;
    private String user_nickname;
    private String user_pic;
    private String user_desc;
    private String session;
    private String token;
    private String weibo;
    private String weixin;
    private String qq;
    private String mobile;
    private String email;
    private String city;

    public UserData() {
    }

    public UserData(String account, String city, String email, boolean isLogin, String mobile, Bitmap mPic, String qq, String session, String token, String user_desc, String user_id, String user_nickname, String user_pic, int vip, String weibo, String weixin) {
        this.account = account;
        this.city = city;
        this.email = email;
        this.isLogin = isLogin;
        this.mobile = mobile;
        this.mPic = mPic;
        this.qq = qq;
        this.session = session;
        this.token = token;
        this.user_desc = user_desc;
        this.user_id = user_id;
        this.user_nickname = user_nickname;
        this.user_pic = user_pic;
        this.vip = vip;
        this.weibo = weibo;
        this.weixin = weixin;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Bitmap getmPic() {
        return mPic;
    }

    public void setmPic(Bitmap mPic) {
        this.mPic = mPic;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_desc() {
        return user_desc;
    }

    public void setUser_desc(String user_desc) {
        this.user_desc = user_desc;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "account='" + account + '\'' +
                ", isLogin=" + isLogin +
                ", vip=" + vip +
                ", mPic=" + mPic +
                ", user_id='" + user_id + '\'' +
                ", user_nickname='" + user_nickname + '\'' +
                ", user_pic='" + user_pic + '\'' +
                ", user_desc='" + user_desc + '\'' +
                ", session='" + session + '\'' +
                ", token='" + token + '\'' +
                ", weibo='" + weibo + '\'' +
                ", weixin='" + weixin + '\'' +
                ", qq='" + qq + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
