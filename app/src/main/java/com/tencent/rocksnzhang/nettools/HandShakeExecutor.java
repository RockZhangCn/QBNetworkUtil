package com.tencent.rocksnzhang.nettools;

import com.tencent.rocksnzhang.utils.DetectResultListener;
import com.tencent.rocksnzhang.utils.DetectTask;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by rock on 16-2-26.
 */
public class HandShakeExecutor extends DetectTask
{
    public HandShakeExecutor(DetectResultListener l, String host)
    {
        super(l, host);
    }

    @Override
    public int detectTaskID()
    {
        return TASK_HANDSHAKE;
    }

    @Override
    public String detectName()
    {
        return "HandShake Detector";
    }

    @Override
    public void taskRun()
    {
        Socket socket = new Socket();

        try{
            InetAddress addr = InetAddress.getByName(mHost);
            socket.connect(new InetSocketAddress(addr, 80), 30000);
            finishedTask(true, "三次握手成功。\n" + socket.toString());
        }
        catch (Exception e)
        {
            finishedTask(false, "三次握手失败，原因如下\n" + e.toString());
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch (IOException e)
            {

            }
        }
    }
}
