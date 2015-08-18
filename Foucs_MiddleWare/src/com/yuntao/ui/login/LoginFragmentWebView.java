package com.yuntao.ui.login;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sohu.focus.framework.util.LogUtils;
import com.yuntao.Constants;
import com.yuntao.R;
import com.yuntao.base.BaseFragment;
import com.yuntao.utils.CommonUtil;
import com.yuntao.utils.PreferenceManager;
import com.yuntao.utils.TitleHelperUtils;
import com.yuntao.widget.ScrollWebView;

/**
 * Created by jomeslu on 2015/3/5.
 */
public class LoginFragmentWebView extends BaseFragment {

    private WebView mWebView;
    private String oldUid;

    private String urlSuccess = "http://m.yyyt.com";// 正式环境
    //http://m.yyyt.com/passport/login
    private String defaultLoginUrl = urlSuccess + "/passport/login";
    private SimpleProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = View.inflate(mContext, R.layout.activity_login_web, null);
        mProgressDialog = new SimpleProgressDialog(mContext,
                R.style.myProgressdialog);
        iniCookes();
        initView(view);
        initTitle(view);
        initData(defaultLoginUrl);

        return view;
    }


    @Override
    protected void initTitleView(TitleHelperUtils mTitleHelper) {
        mTitleHelper.setLeftOnClickLisenter(this);
        mTitleHelper.setLeftText("登录");

    }


    public void initView(View view) {
        mWebView = (WebView) view.findViewById(R.id.login_webview);
        mWebView.requestFocus();
        mWebView.setInitialScale(100);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setBlockNetworkImage(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.setWebViewClient(new ViewClient());
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);



        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(urlSuccess, cookes);//cookies是在HttpClient中获得的cookie
//        synCookies(mContext, urlSuccess, cookes);

    }

    private boolean isLoading = false;

    private class ViewClient extends WebViewClient {

        public ViewClient() {

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return true;

        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);

        }

        @Override
        public void onPageFinished(WebView view, final String url) {
            LogUtils.i("onPageFinished__" + url);
            super.onPageFinished(view, url);
            mWebView.getSettings().setBlockNetworkImage(false);
            isLoading = false;
            String cookes = CookieManager.getInstance().getCookie(url);
            saveCookes(cookes);
            mProgressDialog.dismiss();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            LogUtils.e("onPageStarted" + url);
            super.onPageStarted(view, url, favicon);
            if (!mContext.isFinishing())
                mProgressDialog.show();
            if (!isLoading) {
                isLoading = true;
                String cookes = CookieManager.getInstance().getCookie(url);
                LogUtils.e("onPageStarted:" + cookes);
                saveCookes(cookes);

            }

            // onRefresh();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (mWebView != null) {
                mWebView.stopLoading();
            }
            mProgressDialog.dismiss();
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view,
                                              HttpAuthHandler handler, String host, String realm) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }

        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
        }
    }

    private void initData(String url) {
        mWebView.loadUrl(url);
    }

//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (CommonUtil.getOSVersion() >= 11)
//            mWebView.onPause();
//        mWebView.pauseTimers();
//    }
//
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (CommonUtil.getOSVersion() >= 11)
//            mWebView.onResume();
//        mWebView.resumeTimers();
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        CookieManager.getInstance().removeAllCookie();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private boolean isHasCookies(String cookes) {

        if (TextUtils.isEmpty(cookes))
            return false;
        return cookes.contains("ASPXAUTH") ? true : false;

    }


    /**
     * **只保存一次**
     */
    private boolean isSaved = false;

    private void saveCookes(String cookes) {
        if (!isSaved) {
            if (isHasCookies(cookes)) {
                isSaved=true;
                PreferenceManager.getInstance(mContext).saveData(Constants.pre_cookies,cookes);

            }

        }

    }

    private String cookes="";

    /**
     * 初始话Cookes
     */
    private void iniCookes() {
        cookes = PreferenceManager.getInstance(mContext).getStringData(Constants.pre_cookies, "");
        LogUtils.i("init "+cookes);
    }

    public static void synCookies(Context context, String url,String cookes) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, cookes);
        CookieSyncManager.getInstance().sync();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {

            case R.id.title_left:
                finishCurrent();
                break;

        }

    }
}
