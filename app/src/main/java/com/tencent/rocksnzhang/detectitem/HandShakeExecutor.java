package com.tencent.rocksnzhang.detectitem;

import com.tencent.rocksnzhang.utils.DetectResultListener;
import com.tencent.rocksnzhang.utils.DetectTask;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

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


        if(port == 443)
        {
            sslHandShake(mHost);
        }
        else
        {
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


    private void sslHandShake(String host)
    {
        SocketFactory sf =  SSLSocketFactory.getDefault();
        SSLSocket sslSock = null;
        try {
            sslSock = (SSLSocket)sf.createSocket();
            sslSock.setSoTimeout(30 * 1000);
            sslSock.connect(new InetSocketAddress(host, 443), 22 * 1000);
            finishedTask(true, "sslConnect握手成功。\n" + sslSock.toString());
        } catch (IOException e) {
            e.printStackTrace();
            finishedTask(false, "sslConnect握手失败。\n" + e.toString());
        }

    }
}
