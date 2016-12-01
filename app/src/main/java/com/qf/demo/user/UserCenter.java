package com.qf.demo.user;

import android.content.Context;
import android.net.Uri;
import android.telephony.TelephonyManager;

import com.google.gson.Gson;
import com.qf.demo.entities.UserData;
import com.qf.demo.http.BasicAttributes;
import com.qf.demo.http.Http;
import com.qf.demo.utils.Encryption;
import com.qf.demo.utils.PhoneTools;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by Administrator on 2016/11/3.
 */
public class UserCenter {
    public static final String ACCOUNTERR = "-1";
    public static final String LOGINERR = Http.SEVERERR;
    public static final String LOGINOK = "1";
    public static final String PASSWORKERR = "-2";
    private static UserCenter mInstance = null;
    private UserData mUserData = new UserData();

    public static UserCenter getInstance() {
        if (mInstance == null) {
            mInstance = new UserCenter();
        }
        return mInstance;
    }

    private String jsonTokenerHttpCallBack(String params) {
        if ((params != null) && (params.startsWith(""))) {
            params = params.trim();
        }
        return params;
    }

    private static final int REGISTER = 0;
    private static final int LOGIN = 1;
    private static final int OTHER_LOGIN = 2;

    private void registerUserAccount(String accountStr, String password, Context context) {
        long currentTime = System.currentTimeMillis();
        String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (deviceId == null || deviceId.equals("")) {
            deviceId = PhoneTools.getInstance().getMacAddress(context);
        }
        String sign = Encryption.md5("4.0.5" + currentTime + accountStr + password
                + "android" + deviceId + BasicAttributes.SIGNKEY);
        String account = Uri.encode(accountStr);
        String requestUrl = BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME
                + BasicAttributes.MODELNAME + "&a=appregister" + "&version=" + "4.0.5"
                + "&apitime=" + currentTime + "&account=" + account + "&password="
                + password + "&from=" + "android" + "&mac=" + deviceId
                + "&sign=" + sign + "&from_os=" + "android";
        String rel = Http.getInstance().httpForGet(requestUrl);
        if ((rel.equals(Http.IOERR)) || (rel.equals(Http.SEVERERR))) {
            System.out.println(rel);
            mUserRegisterCallBack.onRegisterErr();
            return;
        }
        parserUserData(rel, REGISTER);
    }

    private void requestUserDataForAccount(String accountStr, String password, Context context) {
        long currentTime = System.currentTimeMillis();
        String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (deviceId == null || deviceId.equals("")) {
            deviceId = PhoneTools.getInstance().getMacAddress(context);
        }
        String sign = Encryption.md5(accountStr + password + "4.0.5" + currentTime
                + "android" + deviceId + BasicAttributes.SIGNKEY);
        String account = Uri.encode(accountStr);
        String requestUrl = BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME
                + BasicAttributes.MODELNAME + "&a=applogin" + "&version=" + "4.0.5"
                + "&apitime=" + currentTime + "&account=" + account + "&password="
                + password + "&from=" + "android" + "&mac=" + deviceId
                + "&sign=" + sign + "&from_os=" + "android";
        String rel = Http.getInstance().httpForGet(requestUrl);
        if ((rel.equals(Http.IOERR)) || (rel.equals(Http.SEVERERR))) {
            mUserLoginCallBack.onLoginErr(LOGINERR);
            return;
        }
        parserUserData(rel, LOGIN);
    }

    private void changeUserInfo() {
        long currentTime = System.currentTimeMillis();
        String user_id = mUserData.getUser_id();
        String user_nickname = mUserData.getUser_nickname();
        String weixin = mUserData.getWeixin();
        String weibo = mUserData.getWeibo();
        String user_desc = mUserData.getUser_desc();
        String city = mUserData.getCity();
        String email = mUserData.getEmail();
        String qq = mUserData.getQq();
        String mobile = mUserData.getMobile();

        String sign = Encryption.md5(user_id + currentTime + BasicAttributes.SIGNKEY);

        String requestUrl = BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME
                + BasicAttributes.MODELNAME + "&a=changeuserinfo" + "&uid="
                + user_id + "&user_nickname=" + Uri.encode(user_nickname) + "&weixin="
                + Uri.encode(weixin) + "&weibo=" + Uri.encode(weibo) + "&user_desc="
                + Uri.encode(user_desc) + "&city=" + Uri.encode(city) + "&email="
                + Uri.encode(email) + "&qq=" + Uri.encode(qq) + "&mobile=" + Uri.encode(mobile)
                + "&apitime=" + currentTime + "&sign=" + sign + "&from_os=" + "android";
        String rel = Http.getInstance().httpForGet(requestUrl);
        if ((rel.equals(Http.IOERR)) || (rel.equals(Http.SEVERERR))) {
            changeUserInfoCallBack.changeError();
            return;
        }
        JSONTokener jsonTokener = new JSONTokener(rel);
        try {
            String result = new JSONObject(jsonTokener).optString("result");
            changeUserInfoCallBack.changeSuccess(result);
            return;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        changeUserInfoCallBack.changeError();
    }

    private void changeUserPwd(String oldPwd, String newPwd) {
        String user_id = mUserData.getUser_id();
        long currentTime = System.currentTimeMillis();
        String sign = Encryption.md5(user_id + currentTime + BasicAttributes.SIGNKEY);
        String requestUrl = BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME + BasicAttributes.MODELNAME
                + "&a=changepassword" + "&uid=" + user_id + "&oldpassword=" + oldPwd
                + "&newpassword=" + newPwd + "&apitime=" + currentTime + "&sign=" + sign
                + "&from_os=" + "android";
        JSONTokener jsonTokener = new JSONTokener(Http.getInstance().httpForGet(requestUrl));
        try {
            JSONObject json = new JSONObject(jsonTokener);
            if (json.optString("code").equals("1")) {
                mUserChangePwdCallBack.onChangePwdComplete();
                return;
            }
            mUserChangePwdCallBack.onChangePwdErr("-1");
            return;
        } catch (JSONException e) {
            e.printStackTrace();
            mUserChangePwdCallBack.onChangePwdErr("-1");
        }
    }

    private void thirdLogin(String uid, String name, String portrait, String target
            , Context context) {
        long currentTime = System.currentTimeMillis();
        String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (deviceId == null || deviceId.equals("")) {
            deviceId = PhoneTools.getInstance().getMacAddress(context);
        }
        String sign = Encryption.md5("4.0.5" + uid + name + target + "android" +
                portrait + deviceId + currentTime + BasicAttributes.SIGNKEY);
        String requestUrl = BasicAttributes.HTTPNAME + BasicAttributes.PHPNAME
                + BasicAttributes.MODELNAME + "&a=outsidelogin"
                + "&version=" + "4.0.5" + "&unique_id=" + uid
                + "&user_nick_name=" + Uri.encode(name) + "&target="
                + target + "&from=" + "android" + "&user_photo="
                + Uri.encode(portrait) + "&mac=" + deviceId + "&apitime="
                + currentTime + "&sign=" + sign + "&from_os=" + "android";
        String result = Http.getInstance().httpForGet(requestUrl);
        if ((result.equals(Http.IOERR)) || (result.equals(Http.SEVERERR))) {
            otherLoginCallBack.otherLoginError();
            return;
        }
        parserUserData(result, OTHER_LOGIN);
    }

    public void parserUserData(String rel, int loginOrReg) {
        String jsonStr = jsonTokenerHttpCallBack(rel);
        JSONTokener jsonTokener = new JSONTokener(jsonStr);
        int result = 0;
        try {
            JSONObject json = new JSONObject(jsonTokener);
            result = json.optInt("result");
            if (result == 1) {
                Gson gson = new Gson();
                mUserData = gson.fromJson(jsonStr, UserData.class);
                mUserData.setIsLogin(true);
                if (mUserLoginCallBack == null && loginOrReg == LOGIN) {
                    return;
                } else if (mUserRegisterCallBack == null && loginOrReg == REGISTER) {
                    return;
                } else if (otherLoginCallBack == null && loginOrReg == OTHER_LOGIN) {
                    return;
                }
                if (loginOrReg == LOGIN) {
                    mUserLoginCallBack.onLoginComplete(mUserData);
                } else if (loginOrReg == REGISTER) {
                    mUserRegisterCallBack.onRegisterSuccess(mUserData);
                } else if (loginOrReg == OTHER_LOGIN) {
                    otherLoginCallBack.otherLoginSuccess(mUserData);
                }
            } else if (result == -1) {
                if (mUserLoginCallBack != null) {
                    mUserLoginCallBack.onLoginErr("-1");
                    if (loginOrReg == REGISTER) {
                        mUserRegisterCallBack.onRegisterErr();
                    } else if (loginOrReg == OTHER_LOGIN) {
                        otherLoginCallBack.otherLoginError();
                    }
                }
            } else if (result == -2) {
                if (mUserLoginCallBack != null) {
                    mUserLoginCallBack.onLoginErr("-2");
                }
            }
        } catch (JSONException e) {
            if (mUserLoginCallBack != null) {
                mUserLoginCallBack.onLoginErr(LOGINERR);
            }
        }
    }

    public void clearUserData() {
        mUserData.setUser_id("0");
        mUserData.setAccount("");
        mUserData.setCity("");
        mUserData.setEmail("");
        mUserData.setMobile("");
        mUserData.setmPic(null);
        mUserData.setUser_pic("");
        mUserData.setSession("");
        mUserData.setToken("");
        mUserData.setIsLogin(false);
        mUserData.setQq("");
        mUserData.setMobile("");
    }

    public void otherLogin(final String uid, final String name, final String portrait, final String target
            , final Context context) {
        new Thread() {
            @Override
            public void run() {
                UserCenter.this.thirdLogin(uid, name, portrait, target, context);
            }
        }.start();

    }


    public void editPwd(final String oldPwd, final String newPwd) {
        new Thread() {
            @Override
            public void run() {
                UserCenter.this.changeUserPwd(oldPwd, newPwd);
            }
        }.start();
    }

    public void editUserInfo() {
        new Thread() {
            @Override
            public void run() {
                UserCenter.this.changeUserInfo();
            }
        }.start();
    }

    public void userLogin(final String account, final String password, final Context context) {
        new Thread() {
            @Override
            public void run() {
                UserCenter.this.requestUserDataForAccount(account, password, context);
            }
        }.start();
    }

    public void userRegister(final String account, final String password, final Context context) {
        new Thread() {
            @Override
            public void run() {
                UserCenter.this.registerUserAccount(account, password, context);
            }
        }.start();
    }

    public UserData getUserData() {
        return mUserData;
    }

    public void setUserData(UserData mUserData) {
        this.mUserData = mUserData;
    }

    private UserChangePwdCallBack mUserChangePwdCallBack = null;

    public void setUserChangePwdCallBack(UserChangePwdCallBack callBack) {
        mUserChangePwdCallBack = callBack;
    }

    private UserLoginCallBack mUserLoginCallBack = null;

    public void setUserLoginListener(UserLoginCallBack callBack) {
        mUserLoginCallBack = callBack;
    }

    public interface UserChangePwdCallBack {
        void onChangePwdComplete();

        void onChangePwdErr(String str);
    }

    private OtherLoginCallBack otherLoginCallBack = null;

    public void setOtherLoginCallBack(OtherLoginCallBack otherLoginCallBack) {
        this.otherLoginCallBack = otherLoginCallBack;
    }

    public interface OtherLoginCallBack {
        void otherLoginSuccess(UserData data);

        void otherLoginError();
    }

    private UserRegisterCallBack mUserRegisterCallBack = null;

    public void setUserRegisterCallBack(UserRegisterCallBack mUserRegisterCallBack) {
        this.mUserRegisterCallBack = mUserRegisterCallBack;
    }

    public interface UserRegisterCallBack {
        void onRegisterErr();

        void onRegisterSuccess(UserData data);
    }

    public interface UserLoginCallBack {
        void onLoginComplete(UserData data);

        void onLoginErr(String str);
    }

    private ChangeUserInfoCallBack changeUserInfoCallBack = null;

    public void setChangeUserInfoCallBack(ChangeUserInfoCallBack changeUserInfoCallBack) {
        this.changeUserInfoCallBack = changeUserInfoCallBack;
    }

    public interface ChangeUserInfoCallBack {
        void changeSuccess(String rel);

        void changeError();
    }
}
