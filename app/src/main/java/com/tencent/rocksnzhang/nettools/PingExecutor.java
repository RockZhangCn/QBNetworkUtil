package com.tencent.rocksnzhang.nettools;

import android.util.Log;

import com.tencent.rocksnzhang.utils.DetectResultListener;
import com.tencent.rocksnzhang.utils.DetectTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by rock on 16-2-26.
 */
public class PingExecutor extends DetectTask
{
    public PingExecutor(DetectResultListener l, String host)
    {
        super(l, host);
    }

    @Override
    public int detectTaskID()
    {
        return TASK_ICMPPING;
    }

    @Override
    public String detectName()
    {
        return "Ping Detector";
    }

    @Override
    public void taskRun()
    {
        BufferedReader in = null;
        Runtime rt = Runtime.getRuntime();
        boolean FoundMatch = false;
        String pingCommand = "/system/bin/ping -c 3 " + mHost;
        Log.e("TAG", "ping thread is running");
        try
        {
            Process pro = rt.exec(pingCommand);
            in = new BufferedReader(new InputStreamReader(pro.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = in.readLine();

            while (line != null)
            {
                sb.append(line + "\n");
                line = in.readLine();
            }

            finishedTask(true, sb.toString());

        }
        catch (IOException e)
        {
            finishedTask(false, e.toString());
        }
    }


}
