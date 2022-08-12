package com.tencent.rocksnzhang.qbnetworkutil;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class NetInfoFragment extends CommonFragment {
    public static final String GATEWAY_IP_URL = "http://2022.ip138.com/";

    private NetBasicInfo mNetBasicInfo;
    private TextView mBasicInfoTextView;
    private WebView mGatewayInfoTextView;

    public NetInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.basicinfo, container, false);
        mBasicInfoTextView = (TextView) view.findViewById(R.id.basicinfo_tv);
        mBasicInfoTextView.setTextIsSelectable(true);

        mBasicInfoTextView.setText(mNetBasicInfo.getApnInfo()
                + "\r\nMac address : \r\n"
                + "wlan0 :\t" + mNetBasicInfo.getMacAddress("wlan0")
                + "\r\np2p0  :\t " + mNetBasicInfo.getMacAddress("p2p0")
                + "\r\n\r\n" + SystemBasicInfo.getBuildInfo()
                + "\r\n");

        mGatewayInfoTextView = (WebView) view.findViewById(R.id.gateway_tv);
        mGatewayInfoTextView.getSettings().setDefaultTextEncodingName("UTF-8");

        //WebSettings settings = mGatewayInfoTextView.getSettings();
        //settings.setTextZoom(90);

        mGatewayInfoTextView.setWebViewClient(new WebViewClient() {
            private boolean mError;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mError = false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                mError = true;
                //super.onReceivedError(view, request, error);
                mGatewayInfoTextView.loadDataWithBaseURL(GATEWAY_IP_URL, "<h1> 获取网关地址失败，请退出重新进入</h1>", "text/html", "utf-8", null);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                //super.onReceivedHttpError(view, request, errorResponse);
                mError = true;
                mGatewayInfoTextView.loadDataWithBaseURL(GATEWAY_IP_URL, "<h1> 获取网关地址失败，请退出重新进入</h1>", "text/html", "utf-8", null);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (mError) {
                    mGatewayInfoTextView.loadDataWithBaseURL(GATEWAY_IP_URL, "<h1> 获取网关地址失败，请退出重新进入</h1>", "text/html", "utf-8", null);
                }

                mError = false;
                //super.onPageFinished(view, url);
            }
        });

        mGatewayInfoTextView.loadUrl(GATEWAY_IP_URL);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNetBasicInfo = NetBasicInfo.getInstance(mContext);
    }


    @Override
    protected String contentToSave() {
        return mBasicInfoTextView.getText().toString();
    }

    @Override
    protected String saveFileName() {
        return FileStoreManager.BASIC_INFO_FILENAME;
    }
}
