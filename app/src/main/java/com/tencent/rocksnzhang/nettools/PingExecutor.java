package com.tencent.rocksnzhang.nettools;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tencent.rocksnzhang.utils.DebugToast;
import com.tencent.rocksnzhang.utils.DetectResultListener;
import com.tencent.rocksnzhang.utils.DetectTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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
    public void startDetect()
    {
        mDetectListener.onDetectStarted(this);
        new PingThread().start();
    }

    @Override
    public String detectName()
    {
        return "Ping Detector";
    }


    protected Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MSG_FINISH:
                    mDetectResultData = (String) msg.obj;
                    mIsSuccess = true;
                    mDetectListener.onDetectFinished(PingExecutor.this);
                    break;

                case MSG_ERROR:
                    mDetectResultData = (String) msg.obj;
                    mIsSuccess = false;
                    mDetectListener.onDetectFinished(PingExecutor.this);
                    break;

            }

        }
    };

    private class PingThread extends Thread
    {
        @Override
        public void run()
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
//                    try
//                    {
//                        Pattern Regex = Pattern.compile("(T|t){2}(L|l)",
//                                Pattern.CANON_EQ);
//                        Matcher RegexMatcher = Regex.matcher(line);
//                        FoundMatch = RegexMatcher.find();
//                        if (FoundMatch)
//                        {
//                            pro.destroy();
//                        }
//                    }
//                    catch (PatternSyntaxException ex)
//                    {
//                        // Syntax error in the regular expression
//                        ex.getMessage();
//                    }
                    line = in.readLine();
                }

                Message msg = mHandler.obtainMessage();
                msg.what = MSG_FINISH;
                msg.obj = sb.toString();
                Log.e("TAG", "ping out put is " + sb.toString() );
                mHandler.sendMessage(msg);
            }
            catch (IOException e)
            {
                Message exceptMsg = mHandler.obtainMessage();
                exceptMsg.obj = e.toString();
                exceptMsg.what = MSG_ERROR;
                mHandler.sendMessage(exceptMsg);
                e.printStackTrace();
            }

        }
    }
}
