
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;
import android.os.Parcelable;

import com.tencent.mttpacketcapture.Utility;

import jp.co.taosoftware.android.packetcapture.PacketCaptureService;


public class Notify extends Activity{

	public static final String mActionStart = "com.startCaputre";
	public static final String mActionFinish = "com.finishCapture";
	
	private Intent mItent = null;
	public static Parcelable sVpnConfig = null;

	private String mStorePath = Utility.genPacketPath() ;
	
	private Intent getServiceIntent(){
		if( mItent == null ){
			mItent = new Intent(this, PacketCaptureService.class);
			mItent.putExtra("PcapPath", mStorePath);
		}
		return mItent;
	}
	
	private void sdkR14StartCapture(Intent intent)
	{
		mStorePath = (intent.getStringExtra("storepath") != null) ? intent.getStringExtra("storepath") :Utility.genPacketPath();
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
			mItent.putExtra("stopcapture", "true");
			startService(mItent);
			finish();
		}
	}
	
	@Override
    protected void onActivityResult(int request, int result, Intent data) {
        if (result == RESULT_OK) {
            startService(getServiceIntent());
        }

        finish();
	}
	
}
