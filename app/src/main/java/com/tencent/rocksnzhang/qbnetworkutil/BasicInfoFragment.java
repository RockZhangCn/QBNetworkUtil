package com.tencent.rocksnzhang.qbnetworkutil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tencent.rocksnzhang.qbnetworkutil.netinfo.NetBasicInfo;
import com.tencent.rocksnzhang.qbnetworkutil.netinfo.SystemBasicInfo;

/**
 * Created by rock on 16-2-19.
 */
public class BasicInfoFragment extends CommonFragment
{
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
        mTextView.setTextIsSelectable(true);

        mTextView.setText(mNetBasicInfo.getApnInfo()
                + "\r\nMac address : \r\n"
                + "wlan0 :\t" + mNetBasicInfo.getMacAddress("wlan0")
                + "\np2p0 :\t " + mNetBasicInfo.getMacAddress("p2p0")
                + "\n\n" + SystemBasicInfo.getBuildInfo());
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mNetBasicInfo = NetBasicInfo.getInstance(mContext);
    }


    @Override
    public String contentToSave()
    {
        return mTextView.getText().toString();
    }

    @Override
    public String saveFileName()
    {
        return "basic_info.txt";
    }
}
