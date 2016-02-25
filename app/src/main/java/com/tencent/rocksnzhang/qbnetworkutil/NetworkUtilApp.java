package com.tencent.rocksnzhang.qbnetworkutil;

import android.app.Application;
import android.content.Context;

/**
 * Created by rock on 16-2-25.
 */
public class NetworkUtilApp extends Application
{
    public static Context mAppContext;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mAppContext = this;
    }

    public Context getApplicationContext()
    {
        return mAppContext;
    }
}
