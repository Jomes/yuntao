package com.yuntao;

import android.app.Application;
import android.text.TextUtils;

import com.igexin.sdk.PushManager;
import com.sohu.focus.framework.util.LogUtils;
import com.yuntao.utils.PreferenceManager;

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    public void setAlia() {
        String token = PreferenceManager.getInstance(this).getStringData(Constants.pre_token, "");
        if (!TextUtils.isEmpty(token)) {
            PushManager.getInstance().bindAlias(this, token);
        }
        LogUtils.i("clientId____________:" + PushManager.getInstance().getClientid(this));


    }


    public static MyApplication getInstance() {
        return instance;
    }
}
