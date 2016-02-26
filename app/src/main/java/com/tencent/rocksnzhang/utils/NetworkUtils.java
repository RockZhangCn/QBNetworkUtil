package com.tencent.rocksnzhang.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

public class NetworkUtils
{
    private static Context mContext;

    private NetworkUtils()
    {
    }

    public static void setApplicationContext(Context c)
    {
        mContext = c;
    }

    public static boolean isNetworkConnected()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo         activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getLocalHost()
    {
        try
        {
            InetAddress iAdd = InetAddress.getLocalHost();
            String line = "";
            String hostName = iAdd.getHostName();
            if (hostName != null)
            {
                InetAddress[] adds = InetAddress.getAllByName(hostName);
                for (int i = 0; i < adds.length; i++)
                {
                    iAdd = adds[i];
                    line = "HostName=" + iAdd.getHostName() + "\n";
                    textView.append(line);
                    line = "CanonicalHostName=" + iAdd.getCanonicalHostName()
                            + "\n";
                    textView.append(line);
                    line = "HostAddress=" + iAdd.getHostAddress() + "\n";
                    textView.append(line);
                    textView.append("\n");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    StringBuilder textView = new StringBuilder();


    public static boolean postStrContent(String content)
    {
        try
        {
            // 建立连接
            URL url = new URL("http://szsdren.com/upload/dataup.php");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            // 设置连接属性
            httpConn.setDoOutput(true);// 使用 URL 连接进行输出
            httpConn.setDoInput(true);// 使用 URL 连接进行输入
            httpConn.setUseCaches(false);// 忽略缓存
            httpConn.setRequestMethod("POST");// 设置URL请求方法

            // 设置请求属性
            // 获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
            httpConn.setRequestProperty("Content-length", "" + content.length());
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Charset", "UTF-8");

            DataOutputStream wr = new DataOutputStream(httpConn.getOutputStream());
            wr.writeBytes(content);
            wr.flush();
            wr.close();

            // 获得响应状态
            int responseCode = httpConn.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception ex)
        {
            return false;
        }
    }


    boolean uploadFile(String uploadUrl)
    {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        try
        {
            URL url = new URL("http://szsdren.com/upload/upload.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url
                    .openConnection();
            // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
            // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
            httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
            // 允许输入输出流
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            // 使用POST方法
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            DataOutputStream dos = new DataOutputStream(
                    httpURLConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"userfile\"; filename=\""
                    + uploadUrl.substring(uploadUrl.lastIndexOf("/") + 1)
                    + "\""
                    + end);
            dos.writeBytes(end);

            FileInputStream fis = new FileInputStream(uploadUrl);
            byte[] buffer = new byte[8192]; // 8k
            int count = 0;
            // 读取文件
            while ((count = fis.read(buffer)) != -1)
            {
                dos.write(buffer, 0, count);
            }

            fis.close();
            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();
            dos.close();

            int responseCode = httpURLConnection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode)
            {
                return true;
            }
            else
            {
                return false;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }


}
