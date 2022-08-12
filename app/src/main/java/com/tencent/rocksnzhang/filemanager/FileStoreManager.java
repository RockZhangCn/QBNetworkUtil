package com.tencent.rocksnzhang.filemanager;

import android.content.Context;
import android.os.Environment;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by rock on 16-3-18.
 */
public class FileStoreManager {
    public static final String BASIC_INFO_FILENAME = "basic_info.txt";
    public static final String NET_DETECT_FILENAME = "net_detect.txt";
    public static final String OTHER_INFO_FILENAME = "other_info.txt";
    private static final String APPZIPDATAFILE = "compressData.zip";
    public static volatile FileStoreManager s_singleInstance = null;
    private final File mAppZipDataFile;
    private Context mContext;
    private File mAppRootStoreDir = null;
    private File mAppTmpStoreDirFile = null;
    private boolean mIsSdcardExist = false;

    private FileStoreManager(Context context) {
        mContext = context;
        mIsSdcardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在

        //无论做什么都需要先创建保存的根目录。
        createAppRootStoreDirFile();
        //创建保存的ZIP File。
        mAppZipDataFile = new File(mAppRootStoreDir, APPZIPDATAFILE);
        //清空临时存储的目录。
        //clearExistStoreFile();
        //创建临时存储的目录。
        createAppTmpStoreDirFile();
    }

    public static FileStoreManager getInstance(Context context) {
        if (s_singleInstance == null) {
            synchronized (FileStoreManager.class) {
                if (s_singleInstance == null)
                    s_singleInstance = new FileStoreManager(context);
            }
        }

        return s_singleInstance;
    }

    public File getAppStoreDirFile() {
        return mAppRootStoreDir;
    }

    public File getAppZipDataFile() {
        return mAppZipDataFile;
    }

    public File getAppTmpStoreDirFile() {
        return mAppTmpStoreDirFile;
    }

    private void createAppRootStoreDirFile() {
        if (mIsSdcardExist) {
            mAppRootStoreDir = Environment.getExternalStorageDirectory();//获取跟目录
        } else {
            mAppRootStoreDir = mContext.getExternalCacheDir();
        }

        mAppRootStoreDir = new File(mAppRootStoreDir.getPath() + "/TrippleF");

        if (!mAppRootStoreDir.exists()) {
            mAppRootStoreDir.mkdir();
        }
    }

    private void clearExistStoreFile() {
        mAppTmpStoreDirFile = new File(mAppRootStoreDir.getPath() + "/.tmp");
        if (mAppTmpStoreDirFile.exists()) {
            FileUtils.deleteQuietly(mAppTmpStoreDirFile);
        }

        try {
            FileUtils.forceDelete(mAppZipDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createAppTmpStoreDirFile() {
        if (mAppRootStoreDir == null)
            createAppRootStoreDirFile();

        mAppTmpStoreDirFile = new File(mAppRootStoreDir.getPath() + "/.tmp");
        if (!mAppTmpStoreDirFile.exists()) {
            mAppTmpStoreDirFile.mkdir();
        }
    }

}
