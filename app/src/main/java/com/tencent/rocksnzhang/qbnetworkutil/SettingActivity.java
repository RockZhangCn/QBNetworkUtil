package com.tencent.rocksnzhang.qbnetworkutil;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by rockzhang on 16/3/16.
 */
public class SettingActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
    }
}
