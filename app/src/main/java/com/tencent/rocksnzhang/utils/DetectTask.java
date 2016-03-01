package com.tencent.rocksnzhang.utils;

import android.os.Handler;
import android.os.Message;

/**
 * Created by rock on 16-2-25.
 */
public abstract class DetectTask
{
    protected static final int MSG_FINISH = 0;
    protected static final int MSG_ERROR = 1;

    public static final int TASK_CONNECT            = 0;
    public static final int TASK_ICMPPING           = 1;
    public static final int TASK_DNSPARSE           = 2;
    public static final int TASK_TRACEROUTE         = 3;
    public static final int TASK_HANDSHAKE          = 4;
    public static final int TASK_SPDYPING           = 5;

    protected String mHost;
    protected boolean mIsSuccess = false;
    protected String mDetectResultData;
    protected  DetectResultListener mDetectListener;

    public DetectTask(DetectResultListener listener, String host)
    {
        mDetectListener = listener;
        mHost = host;
    }

    public final void  startDetect()
    {
        mDetectListener.onDetectStarted(this);
        new DetectThread().start();
    }

    private class DetectThread extends Thread
    {
        @Override
        public void run()
        {
            DetectTask.this.taskRun();
        }
    }

    public abstract int detectTaskID();
    public abstract  String detectName();
    public abstract  void taskRun();

    public final String detectResultData()
    {
        return mDetectResultData;
    }

    public final boolean isSuccess()
    {
        return mIsSuccess;
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
                    mDetectListener.onDetectFinished(DetectTask.this);
                    break;

                case MSG_ERROR:
                    mDetectResultData = (String) msg.obj;
                    mIsSuccess = false;
                    mDetectListener.onDetectFinished(DetectTask.this);
                    break;

            }

        }
    };

    protected void finishedTask(boolean isSuccess, String resultData)
    {
        Message msg = mHandler.obtainMessage();
        msg.what = isSuccess ? MSG_FINISH : MSG_ERROR;
        msg.obj = resultData;
        mHandler.sendMessage(msg);
    }
}
