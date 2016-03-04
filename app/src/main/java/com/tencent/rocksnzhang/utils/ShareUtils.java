package com.tencent.rocksnzhang.utils;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.support.annotation.Nullable;

import com.tencent.rocksnzhang.qbnetworkutil.NetworkUtilApp;

import java.util.List;

public class ShareUtils
{
    public static final int SHARE_THROUGH_MOBILEQQ = 1001;
    public static final int SHARE_THROUGH_WECHAT = 1002;


    /*  微信和手Q的分享Activity。
    com.tencent.mobileqq.activity.qfileJumpActivity
    cooperation.qqfav.widget.QfavJumpActivity
    com.tencent.mm.ui.tools.AddFavoriteUI
    com.tencent.mobileqq.activity.JumpActivity
    com.tencent.mm.ui.tools.ShareImgUI
    */

    @Nullable
    public static ActivityInfo getWeChatOrMobileQQShareActivity(int appName)
    {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        List<ResolveInfo> resolveInfos = NetworkUtilApp.getInstance().getPackageManager().queryIntentActivities(shareIntent, 0);
        if (!resolveInfos.isEmpty())
        {
            for (ResolveInfo info : resolveInfos)
            {
                ActivityInfo activityInfo = info.activityInfo;

                if ((SHARE_THROUGH_MOBILEQQ == appName) && activityInfo.name.equals("com.tencent.mobileqq.activity.JumpActivity"))
                {
                    return activityInfo;
                }

                if ((SHARE_THROUGH_WECHAT == appName) && activityInfo.name.equals("com.tencent.mm.ui.tools.ShareImgUI"))
                {
                    return activityInfo;
                }
            }
        }

        return null;
    }


}
