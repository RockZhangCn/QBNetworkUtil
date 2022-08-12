package com.tencent.rocksnzhang.detectitem;

import com.tencent.rocksnzhang.utils.DetectResultListener;
import com.tencent.rocksnzhang.utils.DetectTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rock on 16-2-26.
 */
public class NetConnectable extends DetectTask {
    private static final String HTTP_BAIDU = "https://m.baidu.com";

    public NetConnectable(DetectResultListener l, String host) {
        super(l, host);
    }

    @Override
    public int detectTaskID() {
        return TASK_CONNECT;
    }

    @Override
    public String detectName() {
        return "Net Connectivity Detector";
    }

    @Override
    public void taskRun() {
        String htmlCode = "";
        HttpURLConnection connection = null;
        try {
            InputStream in;
            URL url = new URL(HTTP_BAIDU);
            connection = (HttpURLConnection) url
                    .openConnection();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/4.0");
            connection.connect();
            in = connection.getInputStream();
            byte[] buffer = new byte[120];
            in.read(buffer);
            htmlCode = new String(buffer);
            connection.disconnect();
        } catch (Exception e) {
            finishedTask(false, e.toString());
            connection.disconnect();
            return;
        }

        if (htmlCode == null || htmlCode.equals("")) {
            finishedTask(false, "网络不通，获取了空的内容。");
            return;
        }

        finishedTask(true, htmlCode + "\r\n恭喜，网络通畅。");
    }
}
