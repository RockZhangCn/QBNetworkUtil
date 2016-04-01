package com.tencent.rocksnzhang.detectitem;

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
    private String mPorts = "8080";
    public HandShakeExecutor(DetectResultListener l, String host, String port)
    {
        super(l, host);
        mPorts = port;

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
        int port = 8080;
        try
        {
            port = Integer.parseInt(mPorts);
        }catch (NumberFormatException e)
        {
            port = 8080;
        }

        try
        {
            InetAddress addr = InetAddress.getByName(mHost);
            socket.connect(new InetSocketAddress(addr, port), 30000);
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
