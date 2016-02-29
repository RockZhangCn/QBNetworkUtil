package com.tencent.rocksnzhang.qbnetworkutil;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.tencent.rocksnzhang.utils.NetworkUtils;

import java.io.File;

/**
 * Created by rock on 16-2-25.
 */
public class NetworkUtilApp extends Application
{
    private static Context        mAppContext;
    private static NetworkUtilApp m_singleInstance;
    private        File           mAppStoreDir;

    @Override
    public void onCreate()
    {
        super.onCreate();
        m_singleInstance = this;
        Log.e("TAG", "onCreate() + instance is " + m_singleInstance);
        mAppContext = m_singleInstance;
        NetworkUtils.setApplicationContext(mAppContext);
        checkStorageDirectory();
    }

    public Context getApplicationContext()
    {
        return mAppContext;
    }

    public static NetworkUtilApp getInstance()
    {
        return m_singleInstance;
    }

    private void checkStorageDirectory()
    {
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在

        File sdRootDir = null;
        if (sdCardExist)
        {
            sdRootDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        else
        {
            sdRootDir = mAppContext.getExternalCacheDir();
        }

        mAppStoreDir = new File(sdRootDir.getPath() + "/TrippleF");
        if (!mAppStoreDir.exists())
            mAppStoreDir.mkdir();
    }

    public File getAppStoreDirFile()
    {
        return mAppStoreDir;
    }
}
