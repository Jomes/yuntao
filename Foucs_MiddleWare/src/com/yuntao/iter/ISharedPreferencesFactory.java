package com.yuntao.iter;


import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * sharedPreferences操作工厂
 * @author jomeslu
 *
 */
public interface ISharedPreferencesFactory
{

	public SharedPreferences getSharedPreferences();

	public Editor getEditor();

}
