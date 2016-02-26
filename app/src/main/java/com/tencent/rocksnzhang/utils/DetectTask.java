package com.tencent.rocksnzhang.utils;

/**
 * Created by rock on 16-2-25.
 */
public abstract class DetectTask
{
    protected boolean mIsSuccess = false;
    protected String mDetectResultData;
    protected  DetectResultListener mDetectListener;

    public DetectTask(DetectResultListener listener)
    {
        mDetectListener = listener;
    }

    public abstract  void  startDetect();
    public abstract  String detectName();
    public abstract  String detectResultData();
    public abstract  boolean isSuccess();
}
