package com.inforun.safecitypeople.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//正则表达式工具类
public class RegExpUtils {
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
	public static final String MOBILE = "^(1[3|4|5|7|8][0-9]{9})$";
	
	public static final String SPECIAL_CHAR = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
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
	
	public static boolean isMobile(String str) {
		Pattern pattern = Pattern.compile(MOBILE);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	public static boolean isContainSpecial(String str) {
		Pattern p = Pattern.compile(SPECIAL_CHAR);
		Matcher m = p.matcher(str);
		return m.find();
	}
}
