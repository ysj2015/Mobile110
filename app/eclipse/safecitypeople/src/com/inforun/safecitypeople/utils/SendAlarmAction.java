package com.inforun.safecitypeople.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.inforun.safecitypeople.Constants;
import com.inforun.safecitypeople.SessionManager;

public class SendAlarmAction {
	public void send() {
		try {
			//String simpleFile = pictureList.get(0);
			String end = "\r\n";
			String twoHyphens = "--";
			String boundary = "******";
			URL url = new URL(Constants.SEND_ALARM);
			HttpURLConnection conn = (HttpURLConnection)(url.openConnection());
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setRequestProperty("Connection", "Keep-Alive");
		    conn.setRequestProperty("Charset", "UTF-8");
		    conn.setRequestProperty("Content-Type",
		          "multipart/form-data;boundary=" + boundary);
		    conn.setRequestProperty("Cookie", SessionManager.getInstance().getSessionId());
		    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
//		    for(int i = 0;i < pictureList.size();i ++) {
//			    dos.writeBytes(twoHyphens + boundary + end);
//			    dos.writeBytes("Content-Disposition: form-data; name=\"files\"; filename=\""  
//			              + pictureList.get(i).substring(pictureList.get(i).lastIndexOf("/") + 1)  
//			              + "\"" + end);
//			    dos.writeBytes(end);
//			    
//			    FileInputStream fis = new FileInputStream(simpleFile);
//			    byte[] buffer = new byte[8192];
//			    int count = 0;
//			    while ((count = fis.read(buffer)) != -1){
//			    	dos.write(buffer, 0, count);
//			    }
//			    fis.close();
//			    System.out.println("file send to server............");  
//			    dos.writeBytes(end);
//		    }
		    
		    
		    dos.writeBytes(twoHyphens + boundary + end);
		    dos.writeBytes("Content-Disposition: form-data; name=\"text\"" + end);
		    dos.writeBytes(end);
		    dos.writeBytes("10000元被偷");
		    dos.writeBytes(end);
		    
		    
		    dos.writeBytes(twoHyphens + boundary + end);
		    dos.writeBytes("Content-Disposition: form-data; name=\"coordStr\"" + end);
		    dos.writeBytes(end);
		    dos.writeBytes("121.591188 31.191934");
		    dos.writeBytes(end);
		    
		    
		    dos.writeBytes(twoHyphens + boundary + twoHyphens + end);  
		    dos.flush();
		    dos.close();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}			

	}
}
