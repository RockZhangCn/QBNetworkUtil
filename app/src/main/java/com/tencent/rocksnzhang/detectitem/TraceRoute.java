package com.tencent.rocksnzhang.detectitem;

import com.og.tracerouteping.network.TraceRouteWithPing;
import com.tencent.rocksnzhang.utils.DetectResultListener;
import com.tencent.rocksnzhang.utils.DetectTask;

/**
 * Created by rock on 16-2-26.
 */
public class TraceRoute extends DetectTask
{
    public static final int MAX_TTL = 40;

    public TraceRoute(DetectResultListener l, String host)
    {
        super(l, host);
    }

    @Override
    public int detectTaskID()
    {
        return TASK_TRACEROUTE;
    }

    @Override
    public String detectName()
    {
        return "Trace Route";
    }

    @Override
    public void taskRun()
    {
        TraceRouteWithPing traceRouteWithPing = new TraceRouteWithPing(mHost, this);
        traceRouteWithPing.executeTraceRoute();
    }


}
