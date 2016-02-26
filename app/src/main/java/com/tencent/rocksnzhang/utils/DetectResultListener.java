package com.tencent.rocksnzhang.utils;

/**
 * Created by rock on 16-2-26.
 */
public interface DetectResultListener
{
    public void onDetectStarted(DetectTask task);
    public void onDetectFinished(DetectTask taska);

}