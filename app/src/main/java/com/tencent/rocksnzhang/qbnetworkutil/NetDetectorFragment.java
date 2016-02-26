package com.tencent.rocksnzhang.qbnetworkutil;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.rocksnzhang.nettools.DNSResolver;
import com.tencent.rocksnzhang.utils.IPDomainVlidator;

/**
 * Created by rock on 16-2-19.
 */
public class NetDetectorFragment extends CommonFragment implements View.OnClickListener
{
    private Context mContext;

    private String mStrDomainIP;
    private EditText domainipedit;
    private Button netavailablebtn;
    private Button pingbtn;
    private Button dnsresolvebtn;
    private Button handshakebtn;
    private Button traceroutebtn;

    public NetDetectorFragment( )
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
        pingbtn = (Button) view.findViewById(R.id.pingaction);
        pingbtn.setOnClickListener(this);

        dnsresolvebtn = (Button)view.findViewById(R.id.resolveaction);
        dnsresolvebtn.setOnClickListener(this);
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
        switch (view.getId())
        {
            case R.id.resolveaction:
                //new DNSResolver();
                if (!checkDomainIPValidate())
                {
                    Toast.makeText(mContext, "输入不合法，请重新输入", Toast.LENGTH_LONG).show();
                    return;
                }
                DNSResolver dnsResolver = new DNSResolver(mStrDomainIP);
                dnsResolver.detectStart();
                break;
        }
    }

    private boolean checkDomainIPValidate()
    {
        mStrDomainIP = domainipedit.getText().toString();
        return  IPDomainVlidator.isValidDomainOrIPAddr(mStrDomainIP);
    }
}
