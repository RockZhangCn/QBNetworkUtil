package com.tencent.rocksnzhang.qbnetworkutil.netinfo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.tencent.rocksnzhang.utils.DebugToast;

import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;

/**
 * Created by rock on 16-2-25.
 */
public class NetBasicInfo
{
    public static final String WIFI_NETINTERFACE = "wlan0";
    public static final String MOBILE_NETINTERFACE = "p2p0";
    public final static String APN_CMWAP = "cmwap";
    public final static String APN_CMNET = "cmnet";
    public final static String APN_UNIWAP = "uniwap";
    public final static String APN_UNINET = "uninet";
    public final static String APN_UNI3gWAP = "3gwap";
    public final static String APN_UNI3gNET = "3gnet";
    public final static String APN_CTWAP = "ctwap";
    public final static String APN_CTNET = "ctnet";
    public final static String APN_CTLTE = "ctlte";
    /**
     * Network type is unknown
     */
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    /**
     * Current network is GPRS
     */
    public static final int NETWORK_TYPE_GPRS = 1;
    /**
     * Current network is EDGE
     */
    public static final int NETWORK_TYPE_EDGE = 2;
    /**
     * Current network is UMTS
     */
    public static final int NETWORK_TYPE_UMTS = 3;
    /**
     * Current network is CDMA: Either IS95A or IS95B
     */
    public static final int NETWORK_TYPE_CDMA = 4;
    /**
     * Current network is EVDO revision 0
     */
    public static final int NETWORK_TYPE_EVDO_0 = 5;
    /**
     * Current network is EVDO revision A
     */
    public static final int NETWORK_TYPE_EVDO_A = 6;
    /**
     * Current network is 1xRTT
     */
    public static final int NETWORK_TYPE_1xRTT = 7;
    /**
     * Current network is HSDPA
     */
    public static final int NETWORK_TYPE_HSDPA = 8;
    /**
     * Current network is HSUPA
     */
    public static final int NETWORK_TYPE_HSUPA = 9;
    /**
     * Current network is HSPA
     */
    public static final int NETWORK_TYPE_HSPA = 10;
    /**
     * Current network is iDen
     */
    public static final int NETWORK_TYPE_IDEN = 11;
    /**
     * Current network is EVDO revision B
     */
    public static final int NETWORK_TYPE_EVDO_B = 12;
    /**
     * Current network is LTE
     */
    public static final int NETWORK_TYPE_LTE = 13;
    /**
     * Current network is eHRPD
     */
    public static final int NETWORK_TYPE_EHRPD = 14;
    /**
     * Current network is HSPA+
     */
    public static final int NETWORK_TYPE_HSPAP = 15;
    public static final int NETWORK_TYPE_SCDMA = 17;
    public static final int NETWOR_TYPE_TDS_HSDPA = 18;  //china mobile 3G
    /**
     * Unknown network class. {@hide}
     */
    public static final int NETWORK_CLASS_UNKNOWN = 0;
    /**
     * Class of broadly defined "2G" networks. {@hide}
     */
    public static final int NETWORK_CLASS_2_G = 1;
    /**
     * Class of broadly defined "3G" networks. {@hide}
     */
    public static final int NETWORK_CLASS_3_G = 2;
    /**
     * Class of broadly defined "4G" networks. {@hide}
     */
    public static final int NETWORK_CLASS_4_G = 3;
    private static volatile NetBasicInfo m_Instance = null;
    private String mNetInterface = WIFI_NETINTERFACE;
    private Context mContext;

    private NetBasicInfo(Context context)
    {
        mContext = context;
    }

    public static NetBasicInfo getInstance(Context context)
    {
        if (m_Instance == null)
        {
            synchronized (NetBasicInfo.class)
            {
                if (m_Instance == null)
                {
                    m_Instance = new NetBasicInfo(context);
                }
            }
        }

        return m_Instance;
    }

    public static int getNetworkClass(int networkType)
    {
        switch (networkType)
        {
            case NETWORK_TYPE_GPRS:
            case NETWORK_TYPE_EDGE:
            case NETWORK_TYPE_CDMA:
            case NETWORK_TYPE_1xRTT:
            case NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2_G;
            case NETWORK_TYPE_UMTS:
            case NETWORK_TYPE_EVDO_0:
            case NETWORK_TYPE_EVDO_A:
            case NETWORK_TYPE_HSDPA:
            case NETWORK_TYPE_HSUPA:
            case NETWORK_TYPE_HSPA:
            case NETWORK_TYPE_EVDO_B:
            case NETWORK_TYPE_EHRPD:
            case NETWORK_TYPE_HSPAP:
            case NETWORK_TYPE_SCDMA:
            case NETWOR_TYPE_TDS_HSDPA:
                return NETWORK_CLASS_3_G;
            case NETWORK_TYPE_LTE:
                return NETWORK_CLASS_4_G;
            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }

    public String getMacAddress(String netInterface)
    {
        String strMacAddr = "";
        byte[] b;
        try
        {
            NetworkInterface NIC = NetworkInterface.getByName(netInterface);

            if (NIC == null)
            {
                NIC = NetworkInterface.getByName("rmnet0");//小米关掉Wifi后只剩下此网卡。
                strMacAddr = "没有 " + netInterface + " 网卡";
            }

            if (NIC != null)
            {
                b = NIC.getHardwareAddress();

                if (b == null)
                {
                    return strMacAddr;
                }

                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < b.length; i++)
                {
                    if (i != 0)
                    {
                        buffer.append(':');
                    }
                    String str = Integer.toHexString(b[i] & 0xFF);
                    buffer.append(str.length() == 1 ? 0 + str : str);
                }
                strMacAddr = buffer.toString().toUpperCase();
            }
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }


        return strMacAddr;
    }

    public String getDnsServerInfo()
    {
        Class systemProperties = null;
        Method getMethod = null;
        String sCurrentDnsServer = null;
        try
        {
            systemProperties = Class.forName("android.os.SystemProperties");
            getMethod = systemProperties.getDeclaredMethod("get", String.class);
            getMethod.setAccessible(true);

            sCurrentDnsServer = (String)getMethod.invoke(null, "net.dns1");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            sCurrentDnsServer = "get_dns_failed";
        }

        return sCurrentDnsServer;
    }

    public String getApnInfo()
    {
        TelephonyManager tel = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String simOperator = tel.getSimOperator();
        //int state = tel.getSimState();
        String opCode = "-1";
        if(simOperator == null || simOperator.length() < 5 )
        {
            opCode = "-1";
        }
        else
        {
            opCode = simOperator.substring(3, 5);
        }

        String operatorName = "unkown";
        if (opCode.equals("01"))
        {
            operatorName = "联通";
        }
        else if (opCode.equals("02"))
        {
            operatorName = "移动";
        }
        else if (opCode.equals("03"))
        {
            operatorName = "电信";
        }
        else
        {
            operatorName = "SIM Card not found";
        }

        ConnectivityManager mag = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobInfo = mag.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiInfo = mag.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        //NetworkInfo mobInfo = mag.getActiveNetworkInfo();

        StringBuilder mOutputString = new StringBuilder();

        mOutputString.append("MNC Code Info:\r\n");
        mOutputString.append("IMSI=" + opCode + "(" + operatorName + ")\r\n");
        mOutputString.append("DNS Server：" + getDnsServerInfo() + "\r\n");
        mOutputString.append("\r\nMobile Network Info:\r\n");

        //通过ExtraInfo 获取移动网络运营商信息
        if (mobInfo.getExtraInfo() != null)
        {
            mOutputString.append("运营商类型：");
            if (mobInfo.getExtraInfo().equals(APN_CMWAP) || mobInfo.getExtraInfo().equals(APN_CMNET))
            {
                mOutputString.append("移动");
            }
            else if (mobInfo.getExtraInfo().equals(APN_UNIWAP) || mobInfo.getExtraInfo().equals(APN_UNINET)
                    || mobInfo.getExtraInfo().equals(APN_UNI3gWAP) || mobInfo.getExtraInfo().equals(APN_UNI3gNET))
            {
                mOutputString.append("联通");
            }
            else if (mobInfo.getExtraInfo().equals(APN_CTWAP) || mobInfo.getExtraInfo().equals(APN_CTNET)
                    || mobInfo.getExtraInfo().equals(APN_CTLTE))
            {
                mOutputString.append("电信");
            }
            else
            {
                mOutputString.append(operatorName+"\r\n");
            }

            if (mobInfo.getExtraInfo().contains("wap"))
            {
                mOutputString.append("--Wap\r\n");
            }
            else if (mobInfo.getExtraInfo().contains("net"))
            {
                mOutputString.append("--Net\r\n");
            }
            else
            {
                mOutputString.append("--Unkown\r\n");
            }


            mOutputString.append("\r\n网络类型：\r\n");

            int netType = getNetworkClass(mobInfo.getSubtype());
            switch (netType)
            {
                case NETWORK_CLASS_2_G:
                    mOutputString.append("2G\r\n");
                    break;
                case NETWORK_CLASS_3_G:
                    mOutputString.append("3G\r\n");
                    break;
                case NETWORK_CLASS_4_G:
                    mOutputString.append("4G\r\n");
                    break;
                default:
                    mOutputString.append("未知\r\n");
                    break;
            }
        }


        mOutputString.append("ExtraInfo=" + mobInfo.getExtraInfo() + "\r\n");
        mOutputString.append("SubtypeName=" + mobInfo.getSubtypeName() + " SubType = " + mobInfo.getSubtype() + "\r\n");
        mOutputString.append("TypeName=" + mobInfo.getTypeName() + " Type = " + mobInfo.getType() + "\r\n");

        mOutputString.append("\r\nWIFI Network Info:\r\n");
        mOutputString.append("ExtraInfo=" + wifiInfo.getExtraInfo() + "\r\n");
        mOutputString.append("SubtypeName=" + wifiInfo.getSubtypeName() + " SubType = " + wifiInfo.getSubtype() + "\r\n");
        mOutputString.append("TypeName=" + wifiInfo.getTypeName() + " Type = " + wifiInfo.getType() + "\r\n");

        return mOutputString.toString();
    }

}
