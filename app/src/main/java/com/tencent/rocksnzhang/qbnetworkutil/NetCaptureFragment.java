package com.tencent.rocksnzhang.qbnetworkutil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.mttpacketcapture.Notify;
import com.tencent.rocksnzhang.filemanager.FileStoreManager;
import com.tencent.rocksnzhang.utils.DebugToast;
import com.tencent.rocksnzhang.utils.MttLogOpenHelper;

/**
 * Created by rock on 16-2-19.
 */
public class NetCaptureFragment extends CommonFragment
{
    private TextView mTokenUrl;
    private Button mTokenShare;

    private Button mStartCapture;
    private Button mStopCapture;

    public NetCaptureFragment()
    {

    }

    @Override
    protected String saveFileName()
    {
        return FileStoreManager.OTHER_INFO_FILENAME;
    }

    @Override
    protected String contentToSave()
    {
        return "Temp content to save";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.othertools, container, false);

        initView(view);

        return view;
    }

    private void initView(View view)
    {
        mTokenUrl = (TextView) view.findViewById(R.id.token_url);
        mTokenShare = (Button) view.findViewById(R.id.token_share);
        mTokenShare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                MttLogOpenHelper.testMethod();
            }
        });

        mStartCapture = (Button)view.findViewById(R.id.startcapture);
        mStartCapture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent notificationIntentStart = new Intent(getActivity(), Notify.class);
                //notificationIntentStart.putExtra("storepath", "/sdcard/QQBrowser/.logTmp/x5debugcapture.pcap");
                notificationIntentStart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                notificationIntentStart.setAction("com.tencent.mttpacketcapture.startCaputre");
                startActivity(notificationIntentStart);
                mStartCapture.setEnabled(false);
                mStopCapture.setEnabled(true);
            }
        });


        mStopCapture = (Button)view.findViewById(R.id.stopcapture);
        mStopCapture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent notificationIntentStart = new Intent(getActivity(), Notify.class);
                notificationIntentStart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                notificationIntentStart.setAction("com.tencent.mttpacketcapture.finishCapture");
                startActivity(notificationIntentStart);
                mStartCapture.setEnabled(true);
                mStopCapture.setEnabled(false);

            }
        });

        mStartCapture.setEnabled(true);
        mStopCapture.setEnabled(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    public static class CaptureReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if(intent.getAction().equals("com.tencent.x5.tcpdump.start"))
            {
                String path = intent.getStringExtra("storepath");
                DebugToast.showToast("开始网络抓包");
                Intent notificationIntentStart = new Intent(context, Notify.class);
                notificationIntentStart.putExtra("storepath", path);
                notificationIntentStart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                notificationIntentStart.setAction("com.tencent.mttpacketcapture.startCaputre");
                context.startActivity(notificationIntentStart);
            }

            if(intent.getAction().equals("com.tencent.x5.tcpdump.stop"))
            {
                DebugToast.showToast("停止网络抓包");
                Intent notificationIntentStart = new Intent(context, Notify.class);
                notificationIntentStart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                notificationIntentStart.setAction("com.tencent.mttpacketcapture.finishCapture");
                context.startActivity(notificationIntentStart);
            }
        }
    }
}
