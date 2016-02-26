package com.tencent.rocksnzhang.utils;

/**
 * Created by rock on 16-2-25.
 */
public abstract class DetectTask
{
    protected static final int MSG_FINISH = 0;
    protected static final int MSG_ERROR = 1;

    protected String mHost;
    protected boolean mIsSuccess = false;
    protected String mDetectResultData;
    protected  DetectResultListener mDetectListener;

    public DetectTask(DetectResultListener listener, String host)
    {
        mDetectListener = listener;
        mHost = host;
    }

    public abstract  void  startDetect();
    public abstract  String detectName();

    public final String detectResultData()
    {
        return mDetectResultData;
    }

    public final boolean isSuccess()
    {
        return mIsSuccess;
    }
}
