package com.tencent.rocksnzhang.qbnetworkutil;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tencent.rocksnzhang.qbnetworkutil.netinfo.NetBasicInfo;

/**
 * Created by rock on 16-2-19.
 */
public class BasicInfoFragment extends Fragment
{
    private Context mContext;
    private NetBasicInfo mNetBasicInfo;

    public BasicInfoFragment()
    {

    }

    private String mTitle;
    private TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.basicinfo, container, false);
        mTextView = (TextView) view.findViewById(R.id.basicinfo_tv);
        mTextView.setText(mNetBasicInfo.getApnInfo()
                + "\r\nMac address : \r\n"
                + "wlan0 :\t" + mNetBasicInfo.getMacAddress("wlan0")
                + "\np2p0 :\t " + mNetBasicInfo.getMacAddress("p2p0"));
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mNetBasicInfo = NetBasicInfo.getInstance(mContext);
        Bundle arguments = getArguments();
        if (arguments != null)
        {
            mTitle = arguments.getString("title");
        }
        else
        {
            mTitle = "Default content";
        }

    }
}
