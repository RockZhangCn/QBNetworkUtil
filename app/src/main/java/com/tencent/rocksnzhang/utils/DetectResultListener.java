package com.tencent.rocksnzhang.utils;

/**
 * Created by rock on 16-2-26.
 */
public interface DetectResultListener {
    void onDetectStarted(DetectTask task);

    void onDetectFinished(DetectTask task);
}