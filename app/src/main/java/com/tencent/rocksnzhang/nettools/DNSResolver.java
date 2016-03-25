package com.tencent.rocksnzhang.nettools;

import android.util.Log;

import com.tencent.rocksnzhang.utils.DetectResultListener;
import com.tencent.rocksnzhang.utils.DetectTask;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;

/**
 * Created by rock on 16-2-25.
 */
public class DNSResolver extends DetectTask
{
    public DNSResolver(DetectResultListener listener, String host)
    {
        super(listener, host);
    }

    @Override
    public void taskRun()
    {
        //nativeDnsResolve();
        HttpDnsResolve();
    }

    private void nativeDnsResolve()
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

    private void HttpDnsResolve()
    {
            final String serverUrl = "http://182.254.116.116/d?dn=";
            String htmlCode = "";
            try {
                InputStream in;
                URL url = new java.net.URL(serverUrl + mHost );
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                connection.connect();

                in = connection.getInputStream();
                htmlCode = IOUtils.toString(in, "UTF-8");
                Log.e("ROCK", serverUrl + mHost  + " ==> " + htmlCode);
            } catch (Exception e)
            {
                finishedTask(false, e.toString());
                return;
            }

            if (htmlCode == null || htmlCode.equals("")) {
                finishedTask(false, "empty content");
                return ;
            }

            finishedTask(true, htmlCode);
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
