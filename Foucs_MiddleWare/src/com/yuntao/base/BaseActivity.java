package com.yuntao.base;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import com.yuntao.R;
import com.yuntao.base.core.BaseFragmentActivity;
import com.yuntao.utils.CommonUtil;
import com.yuntao.utils.TitleHelperUtils;

/**
 *  基类 ，正常来说 所有Activity继承该类
 * @author jomeslu
 * 
 */
public class BaseActivity extends BaseFragmentActivity   {


    protected View mRefreshView;
    protected View mFailedView;
    protected View mSucceedView;
    protected AlphaAnimation displayanimation;
    protected AlphaAnimation dismissanimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
		displayanimation = new AlphaAnimation(0.1f, 1.0f);
        displayanimation.setDuration(500);
        dismissanimation = new AlphaAnimation(1.0f, 0.1f);
        dismissanimation.setDuration(500);
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		initTitle();
	}
	/**
	 * 界面是否需要加载title
	 */
	private  void initTitle() {
	     TitleHelperUtils mTitleHelper = new TitleHelperUtils(this);
		 initTitleView(mTitleHelper);
	}

	
	/**
	 * 子类重写，处理title上的view和click事件
	 */
	protected void initTitleView(TitleHelperUtils mTitleHelper) {

	}

	protected void showToast(String s) {
		CommonUtil.makeToast(getApplicationContext(), s, Toast.LENGTH_SHORT);
	}
	
	protected void showToast(int resId) {
		CommonUtil.makeToast(getApplicationContext(), getApplicationContext().getResources().getString(resId), Toast.LENGTH_SHORT);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finishCurrent();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	protected void finishCurrent() {
		finish();
		overridePendingTransition(0, R.anim.slide_out_right);
	}

	protected void addParentTransition() {

		getParent().overridePendingTransition(R.anim.slide_in_right,
				R.anim.wave_scale_out);
	}

	protected void addTransition() {
		overridePendingTransition(R.anim.slide_in_right, R.anim.wave_scale_out);
	}


}
