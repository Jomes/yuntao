package com.yuntao.ui.login;

import android.annotation.TargetApi;
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
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sohu.focus.framework.util.LogUtils;
import com.yuntao.Constants;
import com.yuntao.R;
import com.yuntao.base.BaseFragment;
import com.yuntao.utils.CommonUtil;
import com.yuntao.utils.TitleHelperUtils;
import com.yuntao.widget.ScrollWebView;

/**
 * Created by jomeslu on 2015/3/5.
 */
public class LoginFragmentWebView extends BaseFragment {

	private ScrollWebView mWebView;
	private String oldUid;

	private String urlSuccess = "http://m.yyyt.com";// 正式环境
//http://m.yyyt.com/passport/login
	private String defaultLoginUrl = urlSuccess + "/passport/login";
	private static final int LOGIN_WEB_SECCEED = 110;
	private static final int LOGIN_WEB_REGIST = 111;
	private static final int LOGIN_WEB_LOGIN = 112;
	private SimpleProgressDialog mProgressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = View.inflate(mContext, R.layout.activity_login_web, null);
		mProgressDialog = new SimpleProgressDialog(mContext,
				R.style.myProgressdialog);
		initData();
		initView(view);
		initTitle(view);
		initData(defaultLoginUrl);

		return view;
	}

	private void initData() {

		Bundle bundle = getArguments();
	}

	@Override
	protected void initTitleView(TitleHelperUtils mTitleHelper) {
		mTitleHelper.setLeftOnClickLisenter(this);
		mTitleHelper.setLeftText("登录");

	}


	public void initView(View view) {
		mWebView = (ScrollWebView) view.findViewById(R.id.login_webview);
		mWebView.requestFocus();
		mWebView.setInitialScale(100);
		mWebView.getSettings().setUseWideViewPort(true);
		mWebView.getSettings().setBuiltInZoomControls(false);
		mWebView.getSettings().setBlockNetworkImage(true);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.setWebViewClient(new ViewClient());
		mWebView.setOnCustomScroolChangeListener(new ScrollWebView.ScrollInterface() {
			@Override
			public void onSChanged(int l, int t, int oldl, int oldt) {
			}
		});
		mWebView.getSettings().setLoadWithOverviewMode(true);

	}

	private boolean isLoading = false;

	private class ViewClient extends WebViewClient {

		public ViewClient() {

		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			LogUtils.i("url__" + url);

			if (!isLoading) {
				isLoading = true;
				String cookes = CookieManager.getInstance().getCookie(url);
				if (isHasCookies(cookes)) {
					Message msg = webHandler.obtainMessage(LOGIN_WEB_SECCEED);
					String str = CookieManager.getInstance().getCookie(url);
					str = str + Constants.GROUP_SPIT_KEY + url;
					msg.obj = str;
					LogUtils.i("jomeslu", str);
					msg.sendToTarget();
				} else {
					view.loadUrl(url);
				}

			}

			// String cookes = CookieManager.getInstance().getCookie(url);
			// if (isHasCookies(cookes)) {
			// Message msg = webHandler.obtainMessage(LOGIN_WEB_SECCEED);
			// String str = CookieManager.getInstance().getCookie(url);
			// str = str + Constants.GROUP_SPIT_KEY + url;
			// msg.obj = str;
			// LogUtils.i("jomeslu", str);
			// msg.sendToTarget();
			//
			// } else {
			// view.loadUrl(url);
			// }

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
			mProgressDialog.dismiss();
			if (!TextUtils.isEmpty(url) && url.contains("register")) {
				Message msg = webHandler.obtainMessage(LOGIN_WEB_REGIST);
				msg.sendToTarget();
			} else {
				Message msg = webHandler.obtainMessage(LOGIN_WEB_LOGIN);
				msg.sendToTarget();
			}

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
				if (isHasCookies(cookes)) {
					Message msg = webHandler.obtainMessage(LOGIN_WEB_SECCEED);
					String str = CookieManager.getInstance().getCookie(url);
					str = str + Constants.GROUP_SPIT_KEY + url;
					msg.obj = str;
					LogUtils.i("jomeslu", str);
					msg.sendToTarget();

				}

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

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onPause() {
		super.onPause();
		if (CommonUtil.getOSVersion() >= 11)
			mWebView.onPause();
		mWebView.pauseTimers();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onResume() {
		super.onResume();
		if (CommonUtil.getOSVersion() >= 11)
			mWebView.onResume();
		mWebView.resumeTimers();
	}

	private Handler webHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == LOGIN_WEB_SECCEED) {
				String cookiesMear = (String) msg.obj;
				LogUtils.i(cookiesMear);
				String cookies = cookiesMear.split(Constants.GROUP_SPIT_KEY)[0];
				String url = cookiesMear.split(Constants.GROUP_SPIT_KEY)[1];
				int mode = 0;
				if (url.contains("register"))
					mode = 1;
				requestToken(cookies, mode);

			} else if (msg.what == LOGIN_WEB_REGIST) {

			} else if (msg.what == LOGIN_WEB_LOGIN) {
				
				
				
			}
		}
	};

	/**
	 * @param cookies
	 * @param mode
	 *            1表示跳转登录页
	 */
	private void requestToken(final String cookies, final int mode) {

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		CookieManager.getInstance().removeAllCookie();
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	/**
	 * 成功后
	 */
	private void setContent() {

	}

	private boolean isHasCookies(String cookes) {

		if (TextUtils.isEmpty(cookes))
			return false;
		return cookes.contains("uid") ? true : false;

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
