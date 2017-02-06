package com.tencent.rocksnzhang.detectitem;

import android.util.Log;

import com.tencent.rocksnzhang.utils.DetectResultListener;
import com.tencent.rocksnzhang.utils.DetectTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by rock on 16-2-26.
 */
public class SPDYPing extends DetectTask
{
    public SPDYPing(DetectResultListener l, String host)
    {
        super(l, host);
    }

    @Override
    public int detectTaskID()
    {
        return TASK_SPDYPING;
    }

    @Override
    public String detectName()
    {
        return "SPDY Ping";
    }

    @Override
    public void taskRun()
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://raw.github.com/square/okhttp/master/README.md").build();

        try{
            Response response = client.newCall(request).execute();
        }catch (IOException e)
        {

        }



    }


}
