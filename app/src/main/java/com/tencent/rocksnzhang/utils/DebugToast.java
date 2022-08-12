package com.tencent.rocksnzhang.utils;

import android.widget.Toast;

import com.tencent.rocksnzhang.qbnetworkutil.NetworkUtilApp;

/**
 * Created by rock on 16-2-26.
 */
public class DebugToast {
    public static void showToast(String info) {
        if (info == null || info.equals("")) {
            return;
        } else {
            Toast.makeText(NetworkUtilApp.getInstance().getApplicationContext(), info, Toast.LENGTH_SHORT).show();
        }

    }
}
