package com.tencent.rocksnzhang.qbnetworkutil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.tencent.rocksnzhang.qbnetworkutil.netinfo.NetBasicInfo;
import com.tencent.rocksnzhang.qbnetworkutil.netinfo.SystemBasicInfo;

/**
 * Created by rock on 16-2-19.
 */
public class BasicInfoFragment extends CommonFragment
{
    public static final String GATEWAY_IP_URL = "http://1212.ip138.com/ic.asp";

    private NetBasicInfo mNetBasicInfo;

    public BasicInfoFragment()
    {
    }

    private TextView mBasicInfoTextView;
    private WebView mGatewayInfoTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.basicinfo, container, false);
        mBasicInfoTextView = (TextView) view.findViewById(R.id.basicinfo_tv);
        mBasicInfoTextView.setTextIsSelectable(true);

        mBasicInfoTextView.setText(mNetBasicInfo.getApnInfo()
                + "\r\nMac address : \r\n"
                + "wlan0 :\t" + mNetBasicInfo.getMacAddress("wlan0")
                + "\np2p0 :\t " + mNetBasicInfo.getMacAddress("p2p0")
                + "\n\n" + SystemBasicInfo.getBuildInfo()
                + "\n");

        mGatewayInfoTextView = (WebView)view.findViewById(R.id.gateway_tv);
        mGatewayInfoTextView.loadUrl(GATEWAY_IP_URL);

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
        return mBasicInfoTextView.getText().toString();
    }

    @Override
    public String saveFileName()
    {
        return "basic_info.txt";
    }
}
