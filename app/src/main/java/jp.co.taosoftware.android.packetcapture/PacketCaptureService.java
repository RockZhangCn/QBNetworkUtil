package jp.co.taosoftware.android.packetcapture;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.VpnService;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.widget.Toast;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Iterator;
import com.tencent.mttpacketcapture.Notify;

public class PacketCaptureService extends VpnService implements Handler.Callback, Runnable {
    private static final String TAG = "mttVpnService";

    private Handler mHandler;
    private Thread mThread;

    private ParcelFileDescriptor mInterface;
    private String mQUA = "";
    private String mQGUID = "";
    
    public native void startCapture( int fd );
    public native void stopCapture();
    public native void setPCapFileName( String filename );
    
    public String mPcapFile = "qqpacketcapture.pcap";
    
    static {
    	System.loadLibrary("tPacketCapture");
    }
    
    public static boolean isCapturing(Context ctx){
    	ActivityManager am = (ActivityManager)ctx.getSystemService(ACTIVITY_SERVICE);
    	Iterator<ActivityManager.RunningServiceInfo> amit = am.getRunningServices(Integer.MAX_VALUE).iterator();
    	while( amit.hasNext() ){
    		ActivityManager.RunningServiceInfo info = amit.next();
    		if( PacketCaptureService.class.getCanonicalName().equals(info.service.getClassName()) ){
    			return true;
    		}
    	}
    	return false;
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	
    	mPcapFile = intent.getStringExtra("PcapPath");
        mQUA = intent.getStringExtra("qua");
        mQGUID = intent.getStringExtra("qguid");
        
        if( mQGUID!= null && mQGUID.equals("rocksnzhang"))
        {
        	
        	Toast.makeText(PacketCaptureService.this, "一切都结束了。", Toast.LENGTH_LONG).show();

        	stopCapture();
        	stopSelf();
        	return 0;
        	
        }
        // The handler is only used to show messages.
        if (mHandler == null) {
            mHandler = new Handler(this);
        }

        // Stop the previous session by interrupting the thread.
        if (mThread != null) {
            mThread.interrupt();
        }
       
        // Start a new session by creating a new thread.
        mThread = new Thread(this, "mttVpnSrvThread");
        mThread.start();
        
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mThread != null) {
            mThread.interrupt();
        }
    }
    
    @Override
    public void onRevoke()
    {
//    	try
//		{
//        	//clear the notification bar.
//			final NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//			nm.cancel(MainActivity.NOTIFY_ID);
//		}
//		catch (SecurityException e)
//		{
//			e.printStackTrace();
//		}
    	
    	stopCapture();
    	stopSelf();
    }

    @Override
    public boolean handleMessage(Message message) {
        if (message != null) {
            Toast.makeText(this, message.what, Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    
    @Override
    public synchronized void run() {
        try {
        	Builder builder = new Builder();
        	//builder.setMtu(1500);
        	builder.addAddress("10.8.0.1", 32);
        	builder.addRoute("0.0.0.0",0);
        	mInterface = builder.setSession("抓包插件").establish();
        	
			if (android.os.Build.VERSION.SDK_INT < 19) {
				// hack the android version < 4.4
				try {	
					Field vpnCfg = builder.getClass().getDeclaredField(
							"mConfig");
					if (vpnCfg != null) {
						vpnCfg.setAccessible(true);
						Parcelable vc = (Parcelable) vpnCfg.get(builder);
						Field userField = vc.getClass()
								.getDeclaredField("user");
						
						userField.setAccessible(true);
						userField.set(vc, "com.tencent.mttpacketcapture");
						Notify.sVpnConfig = vc;
					}
				} catch (Exception e) {
				}
			}
			
        	File file = new File(mPcapFile);
        	if(file.exists())
        	{
        		file.delete();
        	}
        	        	
        	setPCapFileName(mPcapFile);
            startCapture( mInterface.getFd() );
            
        } catch (Exception e) {
         
        } finally {
            try {
            
            	mInterface.close();
            } catch (Exception e) {
             
            }
        }

    }
    
 }
