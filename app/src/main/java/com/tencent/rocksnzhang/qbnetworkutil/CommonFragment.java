package com.tencent.rocksnzhang.qbnetworkutil;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.tencent.rocksnzhang.utils.IDataPersist;

import java.io.File;
import java.io.IOException;

/**
 * Created by rock on 16-2-26.
 */
public abstract class CommonFragment extends Fragment implements IDataPersist
{
    protected Context mContext;

    CommonFragment(Context c)
    {
        mContext = c;
        Log.e("TAG", "mContext is " + mContext);
    }

    @Override
    public final File saveToFile()
    {
        File saveFile = new File(mContext.getFilesDir(), saveFileName());

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


    public abstract String saveFileName();
    public abstract String contentToSave();
}
