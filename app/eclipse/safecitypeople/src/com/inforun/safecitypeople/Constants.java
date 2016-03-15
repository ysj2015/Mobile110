package com.inforun.safecitypeople;


import java.io.File;
/**
 * 常量类
 * @author xiongchaoxi
 *
 */
public class Constants {

	public static final String BASE_URL = "http://222.223.170.241:8099/zyhCity160129";
	public static final String USER_REGISTER = BASE_URL + "/user_regist";
	public static final String LOGIN = BASE_URL + "/user_login";
	public static final String SEND_ALARM = BASE_URL + "/user_sendAlarm";
	public static final String USER_REFRESHCOORD = BASE_URL + "/user_refreshCoord";
	public static final String GET_USER_INFO = BASE_URL + "/user_getMyInfo";
	public static final String USER_UPDATELINKINFO = BASE_URL + "/user_updateLinkInfo";
	public static final String USER_UPDATEPWD = BASE_URL + "/user_updatePwd";
	public static final String USER_GETNEARBYPOLICES = BASE_URL + "/user_getAroundPolices";
	public static final String USER_EXIT = BASE_URL + "/user_exit";
	/* 身份信息 */
	public static final String NAME = "name";
	public static final String BIRTHDAY = "birthday";
	public static final String SEX = "sex";
	public static final String TEL = "tel";
	public static final String SHEN_FEN_ID = "shenFenId";
	public static final String ADDRESS = "address";
	public static final String PHOTO = "photo";// 照片URL
	public static final String LONGITUDE = "longitude";// 经度
	public static final String LATITUDE = "latitude";// 纬度
	
	public static final String SESSIONID = "sessionId";
	public static final String ID = "id";
	public static final String LOGINNAME = "loginName";
	public static final String LOGINCOUNT = "loginCount";
	public static final String ONLINE = "onLine";

}
