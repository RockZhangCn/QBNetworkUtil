package com.tencent.mttpacketcapture;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import android.os.Environment;
import android.util.Log;

public class Utility {
	public final static String TAG="Utility";

	public static void runShell(String cmd){
		try{
			//Log.i(TAG, "Runshell: "+cmd);
			Process proc = Runtime.getRuntime().exec("su");
			DataOutputStream outStream = new DataOutputStream(proc.getOutputStream());
			outStream.writeBytes(cmd+"\n");
			outStream.flush();
			outStream.writeBytes("exit\n");
			outStream.flush();
			outStream.close();			
		}
		catch(Exception ex){
			Log.e(TAG, ex.toString());
		}
	}
	
	public static void runShellBlock(String cmd){
		try{
			//Log.i(TAG, "Runshell: "+cmd);
			Process proc = Runtime.getRuntime().exec("su");
			DataOutputStream outStream = new DataOutputStream(proc.getOutputStream());
			outStream.writeBytes(cmd+"\n");
			outStream.flush();
			outStream.close();
			proc.waitFor();
		}
		catch(Exception ex){
			Log.e(TAG, ex.toString());
		}
	}
	
	public static int checkIsTcpDumping(){
		try {
			Process proc = Runtime.getRuntime().exec("ps tcpdump-tc\n");
			DataInputStream inStream = new DataInputStream(
					proc.getInputStream());
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(inStream));
			
			String oneLine = null;
			while ( (oneLine = bufferRead.readLine()) != null ){
				if( oneLine.startsWith("root") ){
					Log.i(TAG, oneLine);
					oneLine = oneLine.replaceAll("^root *([0-9]*).*", "$1");
					int pid = Integer.parseInt(oneLine);
					return pid;
				}
			}
			return 0;
		} catch (Exception ex) {
			Log.e(TAG, ex.toString());
			return 0;
		}
	}
	 
	
	public static void delFolder(String folderPath) 
    {
        try 
        {
			delAllFile(folderPath); //删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); //删除空文件夹
        }
        catch (Exception e) 
        {
        }
    }
    
    public static void delAllFile(String path) 
    {
		File file = new File(path);
		if (!file.exists()) 
		    return;
		if (!file.isDirectory()) 
			return;
		
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++)
		{
		    if (path.endsWith(File.separator)) 
		    	temp = new File(path + tempList[i]);
		    else 
		        temp = new File(path + File.separator + tempList[i]);
		    
		    try
		    {
			    if (temp.isFile())
			        temp.delete();
		    }
		    catch (Exception e) 
	        {
	        	return ;
	        }
		    
		    if (temp.isDirectory())
		    {
		        delAllFile(path+"/"+ tempList[i]);//先删除文件夹里面的文件
		        delFolder(path+"/"+ tempList[i]);//再删除空文件夹
		    }
		}
    }
	
	
	
	public static String genPacketPath(){
		final String logDirInSdcard = "QQBrowser/.logTmp";
		final String logFileName = "qqpacketcapture.pcap";
		final int maxSize = 100 * 1024; // 100KB
		// 1. make directories if not exist.
		File dir = new File(Environment.getExternalStorageDirectory() + "/" + logDirInSdcard);
		if (!dir.exists())
		{
			dir.mkdirs();
		}

		String pcapPath = dir.getAbsolutePath() + "/" + logFileName;
		// 2. make sure the target file is a normal file and is not too large,
		// if so, delete it first.
		File targetFile = new File(pcapPath);
		if (targetFile.exists() && (!targetFile.isFile() || targetFile.length() > maxSize))
		{
			targetFile.delete();
		}
		return pcapPath;
	}
}
