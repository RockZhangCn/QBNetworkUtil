package com.tencent.rocksnzhang.nettools;

import com.tencent.rocksnzhang.utils.DetectResultListener;
import com.tencent.rocksnzhang.utils.DetectTask;

/**
 * Created by rock on 16-2-26.
 */
public class SPDYPing extends DetectTask
{
    public SPDYPing(DetectResultListener l, String host)
    {
        super(l, host);
    }

    @Override
    public int detectTaskID()
    {
        return TASK_SPDYPING;
    }

    @Override
    public String detectName()
    {
        return "SPDY Ping";
    }

    @Override
    public void taskRun()
    {

    }


}
