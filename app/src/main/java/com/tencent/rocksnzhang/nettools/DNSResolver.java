package com.tencent.rocksnzhang.nettools;

import android.os.Handler;
import android.os.Message;

import com.tencent.rocksnzhang.utils.DetectResultListener;
import com.tencent.rocksnzhang.utils.DetectTask;

import java.net.InetAddress;

/**
 * Created by rock on 16-2-25.
 */
public class DNSResolver extends DetectTask
{
    private String mHost;
    private String mResult;

    private static final int MSG_FINISH = 0;
    private static final int MSG_ERROR = 1;

    protected Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MSG_FINISH:
                    mResult = (String) msg.obj;
                    mIsSuccess = true;
                    mDetectListener.onDetectFinished(DNSResolver.this);
                    break;

                case MSG_ERROR:
                    mResult = (String) msg.obj;
                    mIsSuccess = false;
                    mDetectListener.onDetectFinished(DNSResolver.this);
                    break;

            }

        }
    };

    public DNSResolver(DetectResultListener listener, String host)
    {
        super(listener);
        mHost = host;

    }

    @Override
    public void startDetect()
    {
        mDetectListener.onDetectStarted(this);
        new ResolverThread().start();
    }

    @Override
    public String detectName()
    {
        return "DNS Resolve";
    }

    @Override
    public String detectResultData()
    {
        return mResult;
    }

    @Override
    public boolean isSuccess()
    {
        return mIsSuccess;
    }

    private class ResolverThread extends Thread
    {
        @Override
        public void run()
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

                Message msg = mHandler.obtainMessage();
                msg.what = MSG_FINISH;
                msg.obj = sb.toString();
                mHandler.sendMessage(msg);

            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Message exceptMsg = mHandler.obtainMessage();
                exceptMsg.obj = e.toString();
                exceptMsg.what = MSG_ERROR;
                mHandler.sendMessage(exceptMsg);
            }

        }
    }

}
