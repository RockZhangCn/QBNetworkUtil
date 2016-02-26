package com.tencent.rocksnzhang.nettools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class NetInfoUtils
{

    Context context;

    public NetInfoUtils(Context c)
    {
        context = c;
    }

    private boolean canGetHtmlCode(String httpUrl)
    {
        String htmlCode = "";
        try
        {
            InputStream in;
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/4.0");
            connection.connect();
            in = connection.getInputStream();
            byte[] buffer = new byte[60];
            in.read(buffer);
            htmlCode = new String(buffer);
            Log.e("ROCK", "HTML code is " + htmlCode);
        }
        catch (Exception e)
        {
        }
        if (htmlCode == null || htmlCode.equals(""))
        {
            return false;
        }
        return true;
    }

    private static String pingIP(String IP)
    {
        BufferedReader in = null;
        Runtime rt = Runtime.getRuntime();
        boolean FoundMatch = false;
        String pingCommand = "ping " + IP + " -w " + 3000;
        try
        {
            Process pro = rt.exec(pingCommand);
            in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            String line = in.readLine();
            while (line != null)
            {
                Log.e("ROCK", "line is : " + line);
                try
                {
                    Pattern Regex = Pattern.compile("(T|t){2}(L|l)",
                            Pattern.CANON_EQ);
                    Matcher RegexMatcher = Regex.matcher(line);
                    FoundMatch = RegexMatcher.find();
                    if (FoundMatch)
                    {
                        pro.destroy();
                        return IP.trim();
                    }
                }
                catch (PatternSyntaxException ex)
                {
                    // Syntax error in the regular expression
                    ex.getMessage();
                }
                line = in.readLine();
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }
        return null;
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

    public void initNetworkInfo()
    {
        ConnectivityManager mag = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 此处输出当前可用网络
        textView.append("\nActive:\n");
        NetworkInfo info = mag.getActiveNetworkInfo();
        textView.append("ExtraInfo=" + info.getExtraInfo() + "\n");
        textView.append("SubtypeName=" + info.getSubtypeName() + " SubType = " + info.getSubtype() + "\n");
        textView.append("TypeName=" + info.getTypeName() + " Type = " + info.getType() + "\n");

        NetworkInfo mobInfo = mag.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        textView.append("\nMobile:\n");
        textView.append("ExtraInfo=" + mobInfo.getExtraInfo() + "\n");
        textView.append("SubtypeName=" + mobInfo.getSubtypeName() + " SubType = " + mobInfo.getSubtype() + "\n");
        textView.append("TypeName=" + mobInfo.getTypeName() + " Type = " + mobInfo.getType() + "\n");
        Log.e("NETWORK", textView.toString());

    }


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
