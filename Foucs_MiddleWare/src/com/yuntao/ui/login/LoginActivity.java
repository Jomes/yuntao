package com.yuntao.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.yuntao.Constants;
import com.yuntao.MyApplication;
import com.yuntao.R;
import com.yuntao.base.BaseActivity;
import com.yuntao.base.core.BaseFragmentActivity;
import com.yuntao.mode.BindReslut;

/**
 * 添加预约界面
 * 
 * @author jomeslu
 * 
 */
public class LoginActivity extends BaseActivity {


  @Override
  public void setBundleView(Bundle bundle) {
    super.setBundleView(bundle);
    setContentView(R.layout.lib_container);
//      LoginNewFragment bindStoreFragment = new LoginNewFragment();
    LoginFragmentWebView MLoginFragmentWebView = new LoginFragmentWebView();
    MLoginFragmentWebView.setArguments(bundle);
    addFragment(R.id.ll_container, MLoginFragmentWebView, true);
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);

    Bundle bundleExtra = intent.getBundleExtra(BaseFragmentActivity.PARAM_INTENT);
    if(bundleExtra==null)
      return;
    String modeStr = bundleExtra.getString("url");
      BindReslut pushMessage= new BindReslut();
      pushMessage.setUrl(modeStr);
      MyApplication.getInstance().onBindAndAppoinmentSuccess(pushMessage, Constants.EVENT_PUSH_RECOMMOD);


  }

  private long pressedTime;

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      if (pressedTime == 0
              || System.currentTimeMillis() - pressedTime > 2000) {
        pressedTime = System.currentTimeMillis();
        showToast(getResources().getString(R.string.string_exit_remind));
        return false;
      } else if (pressedTime > 0
              && System.currentTimeMillis() - pressedTime < 2000) {
        System.exit(0);
        return false;
      }
    }
    return false;
  }


}
