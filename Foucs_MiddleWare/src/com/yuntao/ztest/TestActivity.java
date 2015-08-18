package com.yuntao.ztest;

import android.os.Bundle;
import android.view.View;

import com.yuntao.R;
import com.yuntao.base.BaseActivity;
import com.yuntao.utils.TitleHelperUtils;

public class TestActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_customer);
	}
	@Override
	protected void initTitleView( TitleHelperUtils mTitleHelper ) {
		super.initTitleView( mTitleHelper );
		mTitleHelper.setLeftText( "test左边" );
		mTitleHelper.setRightText( "右边" );
		mTitleHelper.setLeftOnClickLisenter( this );
	}
	@Override
	public void onClick( View v ) {
	
		super.onClick( v );
		
		switch( v.getId() ) {
		case R.id.title_left:
			Bundle mBundle=new Bundle();
			mBundle.putString( "test", "test budle" );
			goToOthers( Test2Actvity.class,mBundle );
			addTransition();
			break;

		default:
			break;
		}
	}

}
