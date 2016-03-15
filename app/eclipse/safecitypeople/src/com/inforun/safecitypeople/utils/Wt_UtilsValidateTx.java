package com.inforun.safecitypeople.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

//正则表达式工具类，0 代表爲空， 1代表輸入合法，其他数字代表不合法
public class Wt_UtilsValidateTx {
	private static final int ISEMPTYSTATE = 0;
	private static final int ISUSEABLE = 1;
	private static final int OTHERSTATE = 2;
//	public static final String IP_REG = "^((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\\.){3}"
//			+ "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])$";
//	public static final String MAC_REG = "^(([0-9a-fA-F]{2}):){5}([0-9a-fA-F]){2}$";
	//验证时间字符串(例如08:00、09:30)
	public static boolean isHM(String str){
		Pattern pattern = Pattern.compile("^([0-1][0-9]|2[0-3]):([0-5][0-9])$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	//验证IP地址
//	public static boolean isIP(String str){
//		Pattern pattern = Pattern.compile(IP_REG);
//		Matcher matcher = pattern.matcher(str);
//		return matcher.matches();
//	}
	//验证MAC地址
//	public static boolean isMAC(String str){
//		Pattern pattern = Pattern.compile(MAC_REG);
//		Matcher matcher = pattern.matcher(str);
//		return matcher.matches();
//	}
	public static final String SHENFEN_REG = "^([0-9]{17})[0-9x]$";
	public static final String REGEX_CHINESE = "^([\u4e00-\u9fa5]{1,10})$";
	public static final String MOBILE = "^(1[3|4|5|8][0-9]{9})$";
	public static boolean isChinese(String str) {
		Pattern pattern = Pattern.compile(REGEX_CHINESE);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	public static boolean isSFZ(String str) {
		Pattern pattern = Pattern.compile(SHENFEN_REG);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	public static int isMobile(String str) {
		Pattern pattern = Pattern.compile(MOBILE);
		Matcher matcher = pattern.matcher(str);
		if(matcher.matches()){
			return ISUSEABLE;
		}else if(TextUtils.isEmpty(str)){
			return ISEMPTYSTATE;
		}else{
			return OTHERSTATE;
		}
	}
}
