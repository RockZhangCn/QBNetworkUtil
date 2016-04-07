package com.tencent.rocksnzhang.qbnetworkutil;

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
import com.tencent.rocksnzhang.utils.MttLogOpenHelper;

/**
 * Created by rock on 16-2-19.
 */
public class OtherToolFragment extends CommonFragment
{
    private TextView mTokenUrl;
    private Button mTokenShare;

    private Button mStartCapture;
    private Button mStopCapture;

    public OtherToolFragment()
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
                notificationIntentStart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                notificationIntentStart.setAction("com.tencent.mttpacketcapture.startCaputre");
                startActivity(notificationIntentStart);
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

            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


}
