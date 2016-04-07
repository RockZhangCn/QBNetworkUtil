package com.taosoftware.android.packetcapture;

import android.os.ParcelFileDescriptor;


public interface IVPNService {
	public void uploadData(String aFile, String sQUA, String sGUID);
	public boolean capture();
	public void onRevoke();
	public ParcelFileDescriptor buildInterface();
}
