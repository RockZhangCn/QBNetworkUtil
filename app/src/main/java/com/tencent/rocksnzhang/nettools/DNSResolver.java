package com.tencent.rocksnzhang.nettools;

import com.tencent.rocksnzhang.utils.DetectResultListener;
import com.tencent.rocksnzhang.utils.DetectTask;

import java.net.InetAddress;

/**
 * Created by rock on 16-2-25.
 */
public class DNSResolver extends DetectTask
{
    @Override
    public void taskRun()
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            InetAddress aaa = InetAddress.getByName(mHost);
            InetAddress[] addrs = InetAddress.getAllByName(mHost);
            sb.append("Begin: \n" + aaa.toString() + "\nEnd\n");
            for (InetAddress adr : addrs)
            {
                sb.append(adr.toString() + "\n");
            }

            finishedTask(true, sb.toString());
        }
        catch (Exception e)
        {
            finishedTask(false, e.toString());
        }
    }

    public DNSResolver(DetectResultListener listener, String host)
    {
        super(listener, host);
    }

    @Override
    public int detectTaskID()
    {
        return TASK_DNSPARSE;
    }

    @Override
    public String detectName()
    {
        return "DNS Resolve";
    }
}
