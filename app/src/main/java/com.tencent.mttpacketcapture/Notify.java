package com.tencent.mttpacketcapture;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.VpnService;
import android.os.Bundle;
import android.os.Parcelable;

import com.taosoftware.android.packetcapture.PacketCaptureService;

import java.util.List;



public class Notify extends Activity{

	public static final String mActionStart = "com.tencent.mttpacketcapture.startCaputre";
	public static final String mActionFinish = "com.tencent.mttpacketcapture.finishCapture";
	
	private Intent mItent = null;
	public static Parcelable sVpnConfig = null;
	
	private Intent getServiceIntent(){
		if( mItent == null ){
			mItent = new Intent(this, PacketCaptureService.class);
			mItent.putExtra("PcapPath", Utility.genPacketPath());
		}
		return mItent;
	}
	
	private void sdkR14StartCapture(Intent intent)
	{
		Intent vpnintent = VpnService.prepare(this);
		if (vpnintent != null) {
			try{
				startActivityForResult(vpnintent, 0);
			}
			catch (ActivityNotFoundException ex)
			{
				ex.printStackTrace();
			}
		} else {
			onActivityResult(0, RESULT_OK, intent);
		}
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		super.onNewIntent(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final String action = getIntent().getAction();
		
		if ( action.equalsIgnoreCase(mActionStart) ){
			sdkR14StartCapture(getIntent());
		}
		else if(action.equalsIgnoreCase(mActionFinish))
		{
			Intent mItent = new Intent(this, PacketCaptureService.class);
			mItent.putExtra("qguid", "rocksnzhang");
			startService(mItent);
			finish();
		}
	}
	
	@Override
    protected void onActivityResult(int request, int result, Intent data) {
        if (result == RESULT_OK) {
            startService(getServiceIntent());
        }
		  
		// 通知调用方（QB）开始抓包了
        try {
			Intent in = new Intent();
			in.setAction("com.tencent.QQBrowser.action.NETPACKETCAPTURE");
			in.putExtra("result", "NOTIFY_CLIENT_CAPTURE_START");
			
			final PackageManager packageManager = getPackageManager();
		    List<ResolveInfo> resolveInfo =  
		            packageManager.queryIntentActivities(in,  
		                    PackageManager.MATCH_DEFAULT_ONLY);
		    
		    //检查是否存在此Action，其实就是判断是否是QB-Blink  启动QB
		    if (resolveInfo.size() > 0) {
				startActivity(in);
		    }
        }
        catch ( Exception e ) {
        	e.printStackTrace();
        }
        
        finish();
	}
	
}