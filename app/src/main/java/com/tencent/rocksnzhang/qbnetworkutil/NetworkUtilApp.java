package com.tencent.rocksnzhang.qbnetworkutil;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.tencent.rocksnzhang.utils.NetworkUtils;

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
        Log.e("TAG", "onCreate() + instance is " + m_singleInstance);
        mAppContext = m_singleInstance;
        NetworkUtils.setApplicationContext(mAppContext);
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
