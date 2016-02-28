package com.tencent.rocksnzhang.qbnetworkutil;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tencent.rocksnzhang.utils.IDataPersist;
import com.tencent.rocksnzhang.utils.IProgressChangedListener;

import java.io.File;
import java.io.IOException;

/**
 * Created by rock on 16-2-26.
 */
public abstract class CommonFragment extends Fragment implements IDataPersist
{
    protected Context mContext;
    protected  IProgressChangedListener mIProgressChangedListener;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public final File saveToFile()
    {
        //getFileDir() no permission to read.
        File saveFile = new File(mContext.getExternalCacheDir(), saveFileName());

        try
        {
            org.apache.commons.io.FileUtils.writeStringToFile(saveFile, contentToSave(), "utf-8");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        return saveFile;

    }



    public void setProgressChangedListener(IProgressChangedListener iProgressChangedListener)
    {
        mIProgressChangedListener = iProgressChangedListener;
    }

    public abstract String saveFileName();
    public abstract String contentToSave();
}
