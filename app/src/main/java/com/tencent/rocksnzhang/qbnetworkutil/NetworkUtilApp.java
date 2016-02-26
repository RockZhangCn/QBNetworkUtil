package com.tencent.rocksnzhang.qbnetworkutil;

import android.app.Application;
import android.content.Context;

/**
 * Created by rock on 16-2-25.
 */
public class NetworkUtilApp extends Application
{
    private static Context mAppContext;
    private static NetworkUtilApp m_singleInstance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        m_singleInstance = this;
        mAppContext = this.getApplicationContext();
    }

    public Context getApplicationContext()
    {
        return mAppContext;
    }

    public static NetworkUtilApp getInstance()
    {
        return m_singleInstance;
    }
}
