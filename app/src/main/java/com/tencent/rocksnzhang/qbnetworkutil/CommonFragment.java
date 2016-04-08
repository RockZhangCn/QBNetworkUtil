package com.tencent.rocksnzhang.qbnetworkutil;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tencent.rocksnzhang.utils.IDataPersist;
import com.tencent.rocksnzhang.utils.IProgressChangedListener;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by rock on 16-2-26.
 */
public abstract class CommonFragment extends Fragment implements IDataPersist
{
    protected Context mContext;
    protected IProgressChangedListener mIProgressChangedListener;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public final File saveToFile()
    {
        File tmpSaveDir = NetworkUtilApp.getInstance().getSingleFileStoreManager().getAppTmpStoreDirFile();
        File saveFile = new File(tmpSaveDir,  saveFileName());
		
		//DebugToast.showToast("TmpSaveDir is " + tmpSaveDir.getAbsolutePath() + " ave File is " + saveFile.getAbsolutePath());
        try
        {
            FileUtils.writeStringToFile(saveFile, contentToSave(), "utf-8");
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

    protected abstract String saveFileName();

    protected abstract String contentToSave();
}
