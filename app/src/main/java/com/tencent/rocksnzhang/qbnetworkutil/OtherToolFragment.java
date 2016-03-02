package com.tencent.rocksnzhang.qbnetworkutil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by rock on 16-2-19.
 */
public class OtherToolFragment extends CommonFragment
{
    public OtherToolFragment()
    {

    }

    @Override
    public String saveFileName()
    {
        return "OtherTool.txt";
    }

    @Override
    public String contentToSave()
    {
        return "Temp content to save";
    }

    private TextView mTokenUrl;
    private Button mTokenShare;
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 0:
                    mTokenUrl.setText("upload text");
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.othertools, container, false);
        mTokenUrl = (TextView)view.findViewById(R.id.token_url);
        mTokenShare = (Button)view.findViewById(R.id.token_share);
        mTokenShare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                MttLogOpenHelper.testMethod();
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
