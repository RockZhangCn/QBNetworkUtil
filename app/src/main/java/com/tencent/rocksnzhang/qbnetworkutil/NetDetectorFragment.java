package com.tencent.rocksnzhang.qbnetworkutil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.rocksnzhang.nettools.DNSResolver;
import com.tencent.rocksnzhang.nettools.HandShakeExecutor;
import com.tencent.rocksnzhang.nettools.NetConnectable;
import com.tencent.rocksnzhang.nettools.PingExecutor;
import com.tencent.rocksnzhang.utils.DebugToast;
import com.tencent.rocksnzhang.utils.DetectResultListener;
import com.tencent.rocksnzhang.utils.DetectTask;
import com.tencent.rocksnzhang.utils.IPDomainValidator;
import com.tencent.rocksnzhang.utils.NetworkUtils;

/**
 * Created by rock on 16-2-19.
 */
public class NetDetectorFragment extends CommonFragment implements View.OnClickListener, DetectResultListener
{
    private TextView mDetectResultView;
    private String mStrDomainIP;
    private EditText domainipedit;
    private Button netavailablebtn;
    private Button pingbtn;
    private Button dnsresolvebtn;
    private Button handshakebtn;
    private Button traceroutebtn;
    private Button spdypingbtn;

    public NetDetectorFragment()
    {
    }

    @Override
    public String saveFileName()
    {
        return "net_detector.txt";
    }

    @Override
    public String contentToSave()
    {
        return "this is the content";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.networktool, container, false);

        mDetectResultView = (TextView)view.findViewById(R.id.result_tv);

        domainipedit = (EditText) view.findViewById(R.id.ipdomain);
        domainipedit.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (domainipedit.hasFocus() == false)
                {
                    mStrDomainIP = domainipedit.getText().toString();
                }
            }
        });

        netavailablebtn = (Button)view.findViewById(R.id.netavaiable);
        netavailablebtn.setOnClickListener(this);

        pingbtn = (Button) view.findViewById(R.id.pingaction);
        pingbtn.setOnClickListener(this);

        dnsresolvebtn = (Button) view.findViewById(R.id.resolveaction);
        dnsresolvebtn.setOnClickListener(this);

        traceroutebtn = (Button) view.findViewById(R.id.traceroute);
        traceroutebtn.setOnClickListener(this);

        handshakebtn = (Button)view.findViewById(R.id.connectable);
        handshakebtn.setOnClickListener(this);

        spdypingbtn = (Button)view.findViewById(R.id.spdyping);
        spdypingbtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onClick(View view)
    {
  
        if(!NetworkUtils.isNetworkConnected())
        {
            DebugToast.showToast("没有可用的网络连接");
            return;
        }
		
		mDetectResultView.setText("");
        
        switch (view.getId())
        {
            case R.id.netavaiable:
                NetConnectable netConnectable = new NetConnectable(this, mStrDomainIP);
                netConnectable.startDetect();
                break;

            case R.id.pingaction:
                if (!checkDomainIPValidate())
                {
                    Toast.makeText(mContext, "输入不合法，请重新输入", Toast.LENGTH_LONG).show();
                    return;
                }
                PingExecutor pingExecutor = new PingExecutor(this, mStrDomainIP);
                pingExecutor.startDetect();
                break;

            case R.id.resolveaction:
                if (!checkDomainIPValidate())
                {
                    Toast.makeText(mContext, "输入不合法，请重新输入", Toast.LENGTH_LONG).show();
                    return;
                }
                DNSResolver dnsResolver = new DNSResolver(this, mStrDomainIP);
                dnsResolver.startDetect();
                break;

            case R.id.traceroute:
                if (!checkDomainIPValidate())
                {
                    Toast.makeText(mContext, "输入不合法，请重新输入", Toast.LENGTH_LONG).show();
                    return;
                }

                break;

            case R.id.connectable:
                if (!checkDomainIPValidate())
                {
                    Toast.makeText(mContext, "输入不合法，请重新输入", Toast.LENGTH_LONG).show();
                    return;
                }
                new HandShakeExecutor(this, mStrDomainIP).startDetect();
                break;

            case R.id.spdyping:
                if (!checkDomainIPValidate())
                {
                    Toast.makeText(mContext, "输入不合法，请重新输入", Toast.LENGTH_LONG).show();
                    return;
                }
                break;
        }
    }

    @Override
    public void onDetectStarted(DetectTask task)
    {
        if(mIProgressChangedListener != null)
            mIProgressChangedListener.showProgress();
        Toast.makeText(mContext, "Detect Task " + task.detectName() + " detect started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetectFinished(DetectTask task)
    {
        if(mIProgressChangedListener != null)
            mIProgressChangedListener.hideProgress();
        mDetectResultView.setText(task.detectResultData());
        Toast.makeText(mContext, "Detect Task " + task.detectName() + " detect " + (task.isSuccess() ? "successed" : "failed"), Toast.LENGTH_SHORT).show();
    }


    private boolean checkDomainIPValidate()
    {
        mStrDomainIP = domainipedit.getText().toString();
        return IPDomainValidator.isValidDomainOrIPAddr(mStrDomainIP);
    }
}

