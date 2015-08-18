package com.yuntao.ztest;

import android.os.Bundle;

import com.yuntao.R;
import com.yuntao.base.BaseActivity;


public class Test2Actvity extends BaseActivity{
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.lib_failed_view);
	}

	@Override
	protected void initIntentData( Bundle bundle ) {
		super.initIntentData( bundle );
		if(bundle!=null){
			showToast( bundle.getString( "test" ) );
		}
	}
}
