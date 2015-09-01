package com.yuntao;

import android.app.Application;
import android.text.TextUtils;

import com.igexin.sdk.PushManager;
import com.sohu.focus.framework.loader.FocusResponseError;
import com.sohu.focus.framework.util.LogUtils;
import com.yuntao.http.ParamManage;
import com.yuntao.http.Request;
import com.yuntao.http.ResponseListener;
import com.yuntao.iter.OnBindAndAppoinmentListener;
import com.yuntao.mode.BindReslut;
import com.yuntao.mode.BounleMode;
import com.yuntao.utils.PreferenceManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyApplication extends Application {
    private static MyApplication instance;
    private HashMap<String, OnBindAndAppoinmentListener> mBindList;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mBindList = new HashMap<String, OnBindAndAppoinmentListener>();

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


    /**
     * 在oncreate调用
     *
     * @param mOnBindAndAppoinmentListener
     */
    public void registBindAndAppoinmentListener(
            OnBindAndAppoinmentListener mOnBindAndAppoinmentListener) {
        if (mOnBindAndAppoinmentListener != null) {
            mBindList.put(mOnBindAndAppoinmentListener.getClass().toString(), mOnBindAndAppoinmentListener);
//      mBindList.add(mOnBindAndAppoinmentListener);
        }
    }

    /**
     * 在detory 调用
     *
     * @param mOnBindAndAppoinmentListener
     */
    public void unRegisterBindAndAppoinmentListener(
            OnBindAndAppoinmentListener mOnBindAndAppoinmentListener) {
        if (mOnBindAndAppoinmentListener != null) {
            mBindList.remove(mOnBindAndAppoinmentListener.getClass().toString());
        }
    }

    public void onBindAndAppoinmentSuccess(BindReslut result, int mode) {
        LogUtils.i("jomeslu", "HashMap的大小：" + mBindList.size());
        Iterator<Map.Entry<String, OnBindAndAppoinmentListener>> iter = mBindList.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, OnBindAndAppoinmentListener> bindle = iter.next();
            bindle.getValue().onBindResult(result, mode);
            LogUtils.i("jomeslu", "HashMap Key：" + bindle.getKey());
        }

    }
}
