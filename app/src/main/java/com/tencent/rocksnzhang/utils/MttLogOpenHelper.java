package com.tencent.rocksnzhang.utils;

import android.util.Base64;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rock on 16-3-2.
 */
public class MttLogOpenHelper {
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


    public static String generateSafeToken() {
        Date date = new Date();
        int hour = date.getHours();

        int evenHour = hour / 2;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 0");
        String time = dateFormat.format(date);
        String seeds = time + String.valueOf(evenHour) + ":11";//padding

        String generatedSafeToken = Encript(seeds);

        return generatedSafeToken;
    }

    private static String Encript(String seed) {
        String finalResult = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(seed.getBytes());
            byte[] m = md5.digest();

            EncryptionDecryption sEncription = new EncryptionDecryption();

            byte[] encodeUrl = Base64.encode(sEncription.encrypt(m), Base64.DEFAULT);
            String finalUrl = new String(encodeUrl);
            finalResult = finalUrl.substring(2, 8) + finalUrl.substring(10, 13);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return finalResult;
    }

}
