package com.tencent.rocksnzhang.qbnetworkutil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.rocksnzhang.detectitem.DNSResolver;
import com.tencent.rocksnzhang.detectitem.HandShakeExecutor;
import com.tencent.rocksnzhang.detectitem.NetConnectable;
import com.tencent.rocksnzhang.detectitem.PingExecutor;
import com.tencent.rocksnzhang.detectitem.SPDYPing;
import com.tencent.rocksnzhang.detectitem.TraceRoute;
import com.tencent.rocksnzhang.filemanager.FileStoreManager;
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
    private Spinner spinner;
    private ArrayAdapter<String> portsAdapter;

    private static String[] portslist = {"80", "8080", "443"};

    public static final int CONNECT   =   0;
    public static final int DNS   =       1;
    public static final int PING   =      2;
    public static final int ROUTE   =     3;
    public static final int HANDSHAKE   = 4;
    public static final int SPDYPING   =  5;
    public static final int DETECT_CNT   =  6;

    public static final  String SAPARATOR_LINE = "\n===============================================\n";

    private int mCurrentDetectType = -1;

    private String[] detectResults = new String[]{ "", "", "", "", "", ""};

    public NetDetectorFragment()
    {
    }

    @Override
    protected String saveFileName()
    {
        return FileStoreManager.NET_DETECT_FILENAME;
    }

    @Override
    protected String contentToSave()
    {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < DETECT_CNT; i++)
        {
            if(detectResults[i] != null)
            {
                builder.append(detectResults[i]);
                if(!detectResults[i].trim().equals(""))
                    builder.append(SAPARATOR_LINE);
            }
        }

        return  builder.toString();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.networktool, container, false);

        mDetectResultView = (TextView) view.findViewById(R.id.result_tv);

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

        spinner = (Spinner) view.findViewById(R.id.portspinner);
        portsAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, portslist);
        portsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(portsAdapter);
        spinner.setSelection(2);

        netavailablebtn = (Button) view.findViewById(R.id.netavaiable);
        netavailablebtn.setOnClickListener(this);

        pingbtn = (Button) view.findViewById(R.id.pingaction);
        pingbtn.setOnClickListener(this);

        dnsresolvebtn = (Button) view.findViewById(R.id.resolveaction);
        dnsresolvebtn.setOnClickListener(this);

        traceroutebtn = (Button) view.findViewById(R.id.traceroute);
        traceroutebtn.setOnClickListener(this);

        handshakebtn = (Button) view.findViewById(R.id.handshake);
        handshakebtn.setOnClickListener(this);

        spdypingbtn = (Button) view.findViewById(R.id.spdyping);
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

        if (!NetworkUtils.isNetworkConnected())
        {
            DebugToast.showToast("没有可用的网络连接");
            return;
        }

        mDetectResultView.setText("");

        switch (view.getId())
        {
            case R.id.netavaiable:
                mCurrentDetectType = CONNECT;
                NetConnectable netConnectable = new NetConnectable(this, mStrDomainIP);
                netConnectable.startDetect();
                break;

            case R.id.pingaction:
                mCurrentDetectType = PING;
                if (!checkDomainIPValidate())
                {
                    Toast.makeText(mContext, "输入不合法，请重新输入", Toast.LENGTH_LONG).show();
                    return;
                }
                PingExecutor pingExecutor = new PingExecutor(this, mStrDomainIP);
                pingExecutor.startDetect();
                break;

            case R.id.resolveaction:
                mCurrentDetectType = DNS;
                if (!checkDomainIPValidate())
                {
                    Toast.makeText(mContext, "输入不合法，请重新输入", Toast.LENGTH_LONG).show();
                    return;
                }
                DNSResolver dnsResolver = new DNSResolver(this, mStrDomainIP);
                dnsResolver.startDetect();
                break;

            case R.id.traceroute:
                mCurrentDetectType = ROUTE;
                if (!checkDomainIPValidate())
                {
                    Toast.makeText(mContext, "输入不合法，请重新输入", Toast.LENGTH_LONG).show();
                    return;
                }
                TraceRoute traceRoute = new TraceRoute(this, mStrDomainIP);
                traceRoute.startDetect();
                break;

            case R.id.handshake:
                mCurrentDetectType = HANDSHAKE;
                if (!checkDomainIPValidate())
                {
                    Toast.makeText(mContext, "输入不合法，请重新输入", Toast.LENGTH_LONG).show();
                    return;
                }

                String port = (String)spinner.getSelectedItem();
                new HandShakeExecutor(this, mStrDomainIP, port).startDetect();
                break;

            case R.id.spdyping:
                mCurrentDetectType = SPDYPING;
                if (!checkDomainIPValidate())
                {
                    Toast.makeText(mContext, "输入不合法，请重新输入", Toast.LENGTH_LONG).show();
                    return;
                }
                new SPDYPing(this, mStrDomainIP).startDetect();
                break;
        }
    }

    @Override
    public void onDetectStarted(DetectTask task)
    {
        if (mIProgressChangedListener != null)
        {
            mIProgressChangedListener.showProgress();
        }
        Toast.makeText(mContext, "Detect Task " + task.detectName() + " detect started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetectFinished(DetectTask task)
    {
        if (mIProgressChangedListener != null)
        {
            mIProgressChangedListener.hideProgress();
        }

        mDetectResultView.setText(task.detectResultData());
        detectResults[mCurrentDetectType] += task.detectResultData();
        Toast.makeText(mContext,  task.detectName() + " detect " + (task.isSuccess() ? "successed" : "failed"), Toast.LENGTH_SHORT).show();
    }


    private boolean checkDomainIPValidate()
    {
        mStrDomainIP = domainipedit.getText().toString();
        return IPDomainValidator.isValidDomainOrIPAddr(mStrDomainIP);
    }


}

