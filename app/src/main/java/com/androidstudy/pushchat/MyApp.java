package com.androidstudy.pushchat;

import android.app.Application;

public class MyApp extends Application
{
	public static DbHelper dbHelper;
	private static boolean activityVisible;

	public static boolean isActivityVisible() {
		return activityVisible;
	}

	public static void activityResumed() {
		activityVisible = true;
	}

	public static void activityPaused() {
		activityVisible = false;
	}

	public void onCreate()
	{
		super.onCreate();

		dbHelper = new DbHelper(this);
		DeviceUtil.init(this);
	}
}