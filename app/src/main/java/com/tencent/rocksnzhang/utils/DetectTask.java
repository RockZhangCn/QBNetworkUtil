package com.tencent.rocksnzhang.utils;

/**
 * Created by rock on 16-2-25.
 */
public abstract class DetectTask
{
    protected boolean mIsSuccess = false;

    public abstract  String detectName();
    public abstract  void detectStart();
    public abstract  boolean isSuccess();
}
