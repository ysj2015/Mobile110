package com.inforun.safecitypolice;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;

public class LogUtil {
	private static String file;
	public static void setFile(String f) {
		file = f;
	}
	public static String getFile() {
		return file;
	}
	public static void write(String content) {
//        if (content == null) content = "";
//
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(content.getBytes());
//
//            fos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
		FileWriter writer = null;
		try {
			writer = new FileWriter(file, true);
			writer.write(content);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try{
				if(writer != null) {
					writer.close();
				}
			} catch(IOException e) {}
		}
    }
}
