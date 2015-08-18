package com.yuntao.base;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.yuntao.R;
import com.yuntao.base.core.BaseFactoryFragment;
import com.yuntao.utils.TitleHelperUtils;

/**
 * 
 * @author jomeslu
 * 
 */
public class BaseFragment extends BaseFactoryFragment {

	protected View mRefreshView;
	protected View mFailedView;
	protected View mSucceedView;
	protected Animation displayanimation;
	protected Animation dismissanimation;
	@Override
	public void onActivityCreated( Bundle savedInstanceState ) {
		super.onActivityCreated( savedInstanceState );
//		initTitle();
		displayanimation = new AlphaAnimation(0.1f, 1.0f);
        displayanimation.setDuration(500);
        dismissanimation = new AlphaAnimation(1.0f, 0.1f);
        dismissanimation.setDuration(500);
        
	}


	/**
	 * 界面是否需要加载title
	 */
	protected void initTitle(View view) {
		TitleHelperUtils mTitleHelper = new TitleHelperUtils( view ,mContext);
		initTitleView( mTitleHelper );
	}


	/**
	 * 子类重写，处理title上的view和click事件
	 */
	protected void initTitleView( TitleHelperUtils mTitleHelper ) {

	}


	@Override
	public void onResume() {

		super.onResume();
	}


	@Override
	public void onPause() {

		super.onPause();
	}


	protected void showToast( String s ) {
//		CommonUtil.makeToast(mContext, s, Toast.LENGTH_SHORT );
		Toast.makeText(mContext, s, Toast.LENGTH_SHORT ).show();
	}
	
	protected void finishCurrent() {
		if(mContext!=null){
			mContext.finish();
			mContext.overridePendingTransition(0, R.anim.slide_out_right);
		}
	}
	

	/**
	 * 子类重写，处理title上的view和click事件
	 */
	protected void initTitleView() {

	}

	
	protected void addParentTransition() {

		mContext.overridePendingTransition(R.anim.slide_in_right,
				R.anim.wave_scale_out);
	}

	protected void addTransition() {
		mContext.overridePendingTransition(R.anim.slide_in_right, R.anim.wave_scale_out);
	}


}
