package com.yuntao;

import android.app.Application;

import com.igexin.sdk.PushManager;

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
