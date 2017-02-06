package com.tencent.rocksnzhang.utils;

import android.content.ClipData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.tencent.rocksnzhang.qbnetworkutil.NetworkUtilApp;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

public class SystemUtils
{
    private static Context mContext;
    StringBuilder textView = new StringBuilder();

    private SystemUtils()
    {
    }

    public static void setApplicationContext(Context c)
    {
        mContext = c;
    }

    public static void SetClipString(String data)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            android.content.ClipboardManager cmb = (android.content.ClipboardManager) NetworkUtilApp.getInstance().getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setPrimaryClip(new ClipData("datalabel", new String[]{"text/plain"}, new ClipData.Item(data)));
        }
        else
        {
            android.text.ClipboardManager cmb = (android.text.ClipboardManager) NetworkUtilApp.getInstance().getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(data);
        }
    }
}
