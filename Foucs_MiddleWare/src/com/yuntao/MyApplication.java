package com.yuntao;

import android.app.Application;
import android.text.TextUtils;

import com.igexin.sdk.PushManager;
import com.sohu.focus.framework.loader.FocusResponseError;
import com.sohu.focus.framework.util.LogUtils;
import com.yuntao.http.ParamManage;
import com.yuntao.http.Request;
import com.yuntao.http.ResponseListener;
import com.yuntao.mode.BounleMode;
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

    public void bindleInfo() {
        String token = PreferenceManager.getInstance(this).getStringData(Constants.pre_token, "");
        if (TextUtils.isEmpty(token))
            return;
        String clientId = PushManager.getInstance().getClientid(this);
        if (TextUtils.isEmpty(clientId))
            return;
        new Request<BounleMode>(this)
                .url(ParamManage.getBound(clientId, token)).cache(false)
                .clazz(BounleMode.class)
                .listener(new ResponseListener<BounleMode>() {
                    @Override
                    public void loadFromCache(BounleMode response, long dataTime) {


                    }

                    @Override
                    public void loadFinish(BounleMode response, long dataTime) {


                    }

                    @Override
                    public void loadError(FocusResponseError.CODE errorCode) {


                    }
                }).exec();

    }
}
