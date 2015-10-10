package com.yuntao.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.igexin.sdk.PushManager;
import com.sohu.focus.framework.loader.FocusResponseError;
import com.yuntao.Constants;
import com.yuntao.MyApplication;
import com.yuntao.R;
import com.yuntao.base.BaseActivity;
import com.yuntao.http.ParamManage;
import com.yuntao.http.Request;
import com.yuntao.http.ResponseListener;
import com.yuntao.http.UrlManage;
import com.yuntao.mode.BounleMode;
import com.yuntao.mode.Homepic;
import com.yuntao.service.LoadingService;
import com.yuntao.ui.login.LoginActivity;
import com.yuntao.ui.login.MainActivity;
import com.yuntao.utils.CommonUtil;
import com.yuntao.utils.PreferenceManager;
import com.yuntao.widget.FileUtils;

/**
 * 启动页面
 */
public class SplashActivity extends BaseActivity {
    private Handler mHandler = new Handler();
    private ImageView mimagview;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        initdata();
        mimagview = (ImageView) findViewById(R.id.imagview);
        getPic();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        findViewById(R.id.splash_content).startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }


            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                jumpToAnotherActivity();
            }
        });
    }

    private void initdata() {
        PushManager.getInstance().initialize(this.getApplicationContext());
        String clientId = PushManager.getInstance().getClientid(this);
        if (TextUtils.isEmpty(clientId)) ;
        MyApplication.getInstance().setAlia();

    }

    private void jumpToAnotherActivity() {
        mHandler.postDelayed(new Runnable() {
            @SuppressLint("NewApi")
            @Override
            public void run() {
                if (isFirstUse()) {
//                    showWelcomePage();
                    goHome();
                } else {
                    goHome();

                }
            }
        }, 200);
    }


    /**
     * 主页
     */
    private void goHome() {
        Intent mIntent = new Intent(this, LoginActivity.class);
        startActivity(mIntent);
        finish();

//        Intent mIntent = new Intent(this, MainActivity.class);
//        startActivity(mIntent);
//        finish();

    }


    private boolean isFirstUse() {
        if (PreferenceManager.getInstance(this).getBoolData(Constants.PREFERENCE_KEY_APP_IS_FIRSTUSE, true)) {
            PreferenceManager.getInstance(this).saveData(Constants.PREFERENCE_KEY_APP_IS_FIRSTUSE, false);
            return true;
        }

        return false;
    }


    private void getPic() {

        int w= CommonUtil.getScreenWidth(this);
        int h=CommonUtil.getScreenHeight(this);
        new Request<Homepic>(mContext)
                .url(ParamManage.getSpash(w,h)).cache(false)
                .clazz(Homepic.class)
                .listener(new ResponseListener<Homepic>() {

                    @Override
                    public void loadFromCache(Homepic response, long dataTime) {


                    }

                    @Override
                    public void loadFinish(Homepic response, long dataTime) {

                        if (response != null) {
                            String url = response.getMessage();
                            MyApplication.getInstance().setAdvGoUrl(response.getAdvJumpUrl());
                            MyApplication.getInstance().setAdvImgUrl(response.getAdvPicUrl());

                            Intent getTokenIntent = new Intent(mContext, LoadingService.class);
                            getTokenIntent.putExtra("Intent_url", url);
                            startService(getTokenIntent);

                        }

                    }

                    @Override
                    public void loadError(FocusResponseError.CODE errorCode) {


                    }
                }).exec();

        FileUtils fileUtils = new FileUtils();
        if (fileUtils.isFileExist(fileUtils.fileName)) {
            mimagview.setImageBitmap(fileUtils.getDiskBitmap(fileUtils.fileName));
        } else {
            //显示本地图片
            mimagview.setBackgroundResource(R.drawable.ic_launcher);
        }


    }





}




