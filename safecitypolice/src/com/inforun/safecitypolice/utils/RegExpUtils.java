package com.inforun.safecitypolice.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//正则表达式工具类
public class RegExpUtils {
	public static boolean isHM(String str){
		Pattern pattern = Pattern.compile("^([0-1][0-9]|2[0-3]):([0-5][0-9])$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	public static final String SHENFEN_REG = "^([0-9]{17})[0-9x]$";
	public static final String USERNAME_REG = "^[a-zA-Z]([0-9a-zA-Z]{4,14})$";
	public static final String POLICENO = "^([0-9]{4})$";
	public static final String REGEX_CHINESE = "^([\u4e00-\u9fa5]{1,10})$";
	public static final String MOBILE = "^(1[3|4|5|8|7][0-9]{9})$";
	public static final String SPECIAL_CHAR = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
	public static boolean isUserName(String str) {
		Pattern pattern = Pattern.compile(USERNAME_REG);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	public static boolean isSFZ(String str) {
		Pattern pattern = Pattern.compile(SHENFEN_REG);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	public static boolean isPoliceNo(String str) {
		Pattern pattern = Pattern.compile(POLICENO);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	public static boolean isChinese(String str) {
		Pattern pattern = Pattern.compile(REGEX_CHINESE);
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
