package com.tencent.rocksnzhang.nettools;

import android.util.Log;

import com.og.tracerouteping.network.TracerouteWithPing;
import com.tencent.rocksnzhang.utils.DetectResultListener;
import com.tencent.rocksnzhang.utils.DetectTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by rock on 16-2-26.
 */
public class TraceRoute extends DetectTask
{
    private static final int MAX_TTL = 40;
    public TraceRoute(DetectResultListener l, String host)
    {
        super(l, host);
    }

    @Override
    public String detectName()
    {
        return "Trace Route";
    }

    @Override
    public void taskRun()
    {
        TracerouteWithPing  tracerouteWithPing = new TracerouteWithPing(mHost);
        tracerouteWithPing.executeTraceroute();
    }


}
