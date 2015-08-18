package com.yuntao;

import android.app.Application;

public class MyApplication extends Application
{
	private static MyApplication instance = null;

	@Override
	public void onCreate()
	{
		super.onCreate();
		
	}



	public static MyApplication getInstance()
	{
		return instance;
	}
}
