package com.tencent.rocksnzhang.qbnetworkutil.netinfo;

import android.os.Build;

/**
 * Created by rock on 16-2-25.
 */
public class SystemBasicInfo
{
    public static String getBuildInfo()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Product:" + Build.PRODUCT);
        builder.append("\nTags:" + Build.TAGS);
        builder.append("\nCPU_ABI:" + Build.CPU_ABI);
        builder.append("\nVERSION_CODES.BASE:" + Build.VERSION_CODES.BASE);
        builder.append("\nMODEL:" + Build.MODEL);
        builder.append("\nSDK:" + Build.VERSION.SDK);
        builder.append("\nVERSION.RELEASE:" + Build.VERSION.RELEASE);
        builder.append("\nDEVICE:" + Build.DEVICE);
        builder.append("\nBrand:" + Build.BRAND);
        builder.append("\nBoard:" + Build.BOARD);
        builder.append("\nFINGERPRINT:" + Build.FINGERPRINT);
        builder.append("\nID:" + Build.ID);
        builder.append("\nManufacturer:" + Build.MANUFACTURER);
        builder.append("\nUser:" + Build.USER);

        return builder.toString();
    }
}
