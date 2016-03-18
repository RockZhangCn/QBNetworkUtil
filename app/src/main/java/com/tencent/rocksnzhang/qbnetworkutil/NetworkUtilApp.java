package com.tencent.rocksnzhang.qbnetworkutil;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.tencent.rocksnzhang.filemanager.FileStoreManager;
import com.tencent.rocksnzhang.utils.NetworkUtils;

import java.io.File;

/**
 * Created by rock on 16-2-25.
 */
public class NetworkUtilApp extends Application
{
    private static Context mAppContext;
    private static NetworkUtilApp m_singleInstance;
    private File mAppStoreDir;

    public static NetworkUtilApp getInstance()
    {
        return m_singleInstance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        m_singleInstance = this;
        mAppContext = m_singleInstance;
        NetworkUtils.setApplicationContext(mAppContext);
        getSingleFileStoreManager();
    }

    public Context getApplicationContext()
    {
        return mAppContext;
    }

    public FileStoreManager getSingleFileStoreManager()
    {
        return FileStoreManager.getInstance(mAppContext);
    }

}
