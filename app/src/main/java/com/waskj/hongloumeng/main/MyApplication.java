package com.waskj.hongloumeng.main;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class MyApplication extends Application {

	public static SharedPreferences sharedPrefs;
	public static Editor spEditor;
	private boolean firstStart = true;

	@Override
	public void onCreate() {
		super.onCreate();
		sharedPrefs = getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		spEditor = sharedPrefs.edit();
	}

	public boolean isFirstStart() {
		return firstStart;
	}

	public void setFirstStart(boolean firstStart) {
		this.firstStart = firstStart;
	}
}