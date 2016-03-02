package com.tencent.rocksnzhang.utils;

import android.content.ClipData;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebView;

import com.tencent.rocksnzhang.qbnetworkutil.NetworkUtilApp;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rock on 16-3-2.
 */
public class MttLogOpenHelper
{
    //Field openid        value string. security check token.
    private static final String OPEN_ID = "openid";
    // Field timeout       value int seconds, default automatically close and upload the log in 120 seconds.
    private static final String TIMEOUT = "timeout";
    // Field cleardns      value true, default is false. clear dns cache.
    private static final String CLEARDNS = "cleardns";
    // Field clearcookie   value true, default is false.
    private static final String CLEARCOOKIE = "clearcookie";
    // Field clearcache    value true, default is false.
    private static final String CLEARCACHE = "clearcache";
    // Field proxy         value reverse | true | false, default is keep old value, no change.
    private static final String PROXY = "proxy";
    //Field openurl       value url string, the url which will show the bug.
    private static final String OPENURL = "openurl";

    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String PROXY_REVERSE = "reverse";


    private static String generateSafeToken()
    {
        Date date = new Date();
        int minutes = date.getMinutes();

        int halfHour = minutes / 30;
        Log.e("TAG", " halfHour is " + halfHour);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:0");
        String time = dateFormat.format(date);
        String seeds = time + String.valueOf(halfHour);

        String generatedSafeToken = Encript(seeds);
        Log.e("TAG", "generatedSafeToken is " + generatedSafeToken);

        return generatedSafeToken;
    }

    private static String Encript(String seed)
    {
        String finalResult = null;
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(seed.getBytes());
            byte[] m = md5.digest();

            EncryptionDecryption sEncription = new EncryptionDecryption();

            byte[] encodeUrl = Base64.encode(sEncription.encrypt(m), Base64.DEFAULT);
            String finalUrl = new String(encodeUrl);
            finalResult = finalUrl.substring(2, 8) + finalUrl.substring(10, 13);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return finalResult;
    }


    public static void testMethod()
    {
        String mUrl = "http://debugx5.qq.com/?openid=lPrk3dHFR&timeout=121&cleardns=true&clearcookie=true&clearcache=true&proxy=reverse&openurl=https://m.baidu.com";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            android.content.ClipboardManager cmb = (android.content.ClipboardManager) NetworkUtilApp.getInstance().getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setPrimaryClip(new ClipData("datalabel", new String[]{"text/plain"}, new ClipData.Item(generateSafeToken())));
        }
        else
        {
            android.text.ClipboardManager cmb = (android.text.ClipboardManager) NetworkUtilApp.getInstance().getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(generateSafeToken());
        }


        if (mUrl.contains("openid="))
        {
            URL netUrl = null;
            try
            {
                netUrl = new URL(mUrl);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                DebugToast.showToast("URL 不合法，请检查后重新输入");
                return;
            }

            Map<String, String> instructions = new HashMap<>();
            String queryStr = netUrl.getQuery();
            String[] commands = queryStr.split("&");
            for (String command : commands)
            {
                String[] cmdParams = command.split("=");

                String cmdName = cmdParams[0];
                String cmdValue = cmdParams[1];
                Log.e("TAG", "command is " + cmdName + " = " + cmdValue);

                if (cmdName != null && (cmdName.length() > 0) && cmdValue != null && (cmdValue.length() > 0))
                {
                    instructions.put(cmdParams[0], cmdParams[1]);
                }
            }

            if (instructions.containsKey(OPEN_ID) && instructions.get(OPEN_ID).equals(generateSafeToken()))
            {
                if (instructions.containsKey(TIMEOUT))
                {
                    int time = 120;
                    try
                    {
                        time = Integer.parseInt(instructions.get(TIMEOUT));
                    }
                    catch (NumberFormatException e)
                    {

                    }
                    Log.e("TAG", "timeout = " + time);

                }

                if (instructions.containsKey(CLEARCACHE) && instructions.get(CLEARCACHE).equals(TRUE))
                {
                    Log.e("TAG", "CLEARCACHE = ");
                }

                if (instructions.containsKey(CLEARDNS) && instructions.get(CLEARDNS).equals(TRUE))
                {
                    Log.e("TAG", "CLEARDNS = ");
                }

                if (instructions.containsKey(CLEARCOOKIE) && instructions.get(CLEARCOOKIE).equals(TRUE))
                {
                    Log.e("TAG", "CLEARCOOKIE = ");
                }

                if (instructions.containsKey(PROXY))
                {
                    String value = instructions.get(PROXY);

                    if (value.equals(PROXY_REVERSE))
                    {
                        Log.e("TAG", "PROXY = reverse ");
                    }

                    if (value.equals(TRUE))
                    {
                        Log.e("TAG", "PROXY = true ");
                    }

                    if (value.equals(FALSE))
                    {
                        Log.e("TAG", "PROXY =  false");
                    }

                }

                if (instructions.containsKey(OPENURL))
                {
                    boolean validUrl = true;
                    String issueUrl = instructions.get(OPENURL);
                    try
                    {
                        new URL(issueUrl);
                    }
                    catch (Exception e)
                    {
                        validUrl = false;
                    }

                    if (validUrl)
                    {
                        // delay ten seconds.
                        Log.e("TAG", "open url = " + issueUrl);
                    }

                }
            }
            else
            {
                return;
            }
        }
    }
}
