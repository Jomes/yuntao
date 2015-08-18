package com.yuntao.ui.login;

import android.os.Bundle;

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
}
