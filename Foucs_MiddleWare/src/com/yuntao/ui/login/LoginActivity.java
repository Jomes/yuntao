package com.yuntao.ui.login;

import android.os.Bundle;
import android.view.KeyEvent;

import com.yuntao.R;
import com.yuntao.base.BaseActivity;

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
