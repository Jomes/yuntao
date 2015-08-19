package com.yuntao.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.igexin.sdk.PushManager;
import com.yuntao.Constants;
import com.yuntao.R;
import com.yuntao.base.BaseActivity;
import com.yuntao.ui.login.LoginActivity;
import com.yuntao.utils.PreferenceManager;

/**
 * 启动页面
 */
public class SplashActivity extends BaseActivity {
    private Handler mHandler = new Handler();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        initdata();
        startLoading();
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
     * 检查Token，异步Service请求Token和CityList
     */
    private void startLoading() {

    }

    /**
     * 主页
     */
    private void goHome() {
        Intent mIntent = new Intent(this, LoginActivity.class);
        startActivity(mIntent);
        finish();
    }


    /**
     * 引导页
     */
    private void showWelcomePage() {

    }

    private boolean isFirstUse() {
        if (PreferenceManager.getInstance(this).getBoolData(Constants.PREFERENCE_KEY_APP_IS_FIRSTUSE, true)) {
            PreferenceManager.getInstance(this).saveData(Constants.PREFERENCE_KEY_APP_IS_FIRSTUSE, false);
            return true;
        }


        return false;
    }

}
