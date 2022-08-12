package com.tencent.rocksnzhang.qbnetworkutil.netinfo;

import android.os.Build;

/**
 * Created by rock on 16-2-25.
 */
public class SystemBasicInfo {
    public static String getBuildInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("Product:" + Build.PRODUCT);
        builder.append("\r\nTags:" + Build.TAGS);
        builder.append("\r\nCPU_ABI:" + Build.CPU_ABI);
        builder.append("\r\nVERSION_CODES.BASE:" + Build.VERSION_CODES.BASE);
        builder.append("\r\nMODEL:" + Build.MODEL);
        builder.append("\r\nSDK:" + Build.VERSION.SDK);
        builder.append("\r\nVERSION.RELEASE:" + Build.VERSION.RELEASE);
        builder.append("\r\nDEVICE:" + Build.DEVICE);
        builder.append("\r\nBrand:" + Build.BRAND);
        builder.append("\r\nBoard:" + Build.BOARD);
        builder.append("\r\nFINGERPRINT:" + Build.FINGERPRINT);
        builder.append("\r\nID:" + Build.ID);
        builder.append("\r\nManufacturer:" + Build.MANUFACTURER);
        builder.append("\r\nUser:" + Build.USER);

        return builder.toString();
    }
}
