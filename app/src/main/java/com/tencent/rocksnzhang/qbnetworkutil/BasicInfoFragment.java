package com.tencent.rocksnzhang.qbnetworkutil;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.tencent.rocksnzhang.filemanager.FileStoreManager;
import com.tencent.rocksnzhang.qbnetworkutil.netinfo.NetBasicInfo;
import com.tencent.rocksnzhang.qbnetworkutil.netinfo.SystemBasicInfo;

/**
 * Created by rock on 16-2-19.
 */
public class BasicInfoFragment extends CommonFragment
{
    public static final String GATEWAY_IP_URL = "http://1212.ip138.com/ic.asp";

    private NetBasicInfo mNetBasicInfo;
    private TextView mBasicInfoTextView;
    private WebView mGatewayInfoTextView;
    public BasicInfoFragment()
    {
    }

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

        mGatewayInfoTextView = (WebView) view.findViewById(R.id.gateway_tv);
        Log.e("TAG", "Before set webview client");

        mGatewayInfoTextView.setWebViewClient(new WebViewClient()
        {
            private boolean mError;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                Log.e("TAG", "onPageStarted ");
                super.onPageStarted(view, url, favicon);
                mError = false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error)
            {
                mError = true;
                //super.onReceivedError(view, request, error);
                Log.e("TAG", "Received error " + error + " request is " + request);
                mGatewayInfoTextView.loadData("<h1> 获取网关地址失败，请退出重新进入</h1>", "text/html", "utf-8");
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse)
            {
                //super.onReceivedHttpError(view, request, errorResponse);
                mError = true;
                Log.e("TAG", "Received errorResponse " + errorResponse + " request is " + request);
                mGatewayInfoTextView.loadData("<h1> 获取网关地址失败，请退出重新进入</h1>", "text/html", "utf-8");
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                Log.e("TAG", "onPageFinished ");
                if (mError)
                {
                    mGatewayInfoTextView.loadData("<h1> 获取网关地址失败，请退出重新进入</h1>", "text/html", "utf-8");
                }

                mError = false;
                //super.onPageFinished(view, url);
            }
        });

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
    protected String contentToSave()
    {
        return mBasicInfoTextView.getText().toString();
    }

    @Override
    protected String saveFileName()
    {
        return FileStoreManager.BASIC_INFO_FILENAME;
    }
}
