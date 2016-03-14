package com.inforun.safecitypolice.finals;


import java.io.File;
/**
 * 常量类
 * @author xiongchaoxi
 *
 */
public class Constants {
	public static final String TAG = "safecitypolice";
	public static final String DOWNLOAD_DIR_APK = "download" + File.separator
			+ "apk" + File.separator;
	public static final String DOWNLOAD_DIR_VIDEO = "download" + File.separator
			+ "video" + File.separator;
	public static final String DOWNLOAD_DIR_SOUND = "download" + File.separator
			+ "sound" + File.separator;
	public static final String UPLOAD_DIR_IMAGE = "upload" + File.separator
			+ "image" + File.separator;
	public static final String UPLOAD_DIR_VIDEO = "upload" + File.separator
			+ "video" + File.separator;
	public static final String UPLOAD_DIR_SOUND = "upload" + File.separator
			+ "sound" + File.separator;
	// 第一次安装
	public static final String FRIST_USE = "frist_use";



	public static final String SESSIONID="sessionId";
	public static final String ID="id";
	public static final String POLICENO="policeNo";
	public static final String PWD="pwd";
	public static final String LOGINCOUNT="loginCount";
	public static final String ONLINE="onLine";

	/* 身份信息 */
	public static final String NAME="name";
	public static final String BIRTHDAY="birthday";
	public static final String AGE="age";
	public static final String SEX="sex";
	public static final String TEL="tel";
	public static final String SHEN_FEN_ID="shenFenId";
	public static final String ADDRESS="address";
	public static final String PHOTO="photo";// 照片URL
	public static final String LONGITUDE="longitude";// 经度
	public static final String LATITUDE="latitude";// 纬度

	//public static final String BASE_URL = "http://120.26.115.52:8080/zyhCityTest";
	public static final String BASE_URL = "http://222.223.170.241:8099/zyhCity160129";
	/**
	 * 警察注册
	 */
	public static final String POLICE_REGIST = BASE_URL + "/police_regist";
	/**
	 * 警察登录
	 */
	public static final String POLICE_LOGIN = BASE_URL+"/police_login";
	/**
	 * 警察退出
	 */
	public static final String POLICE_EXIT = BASE_URL+"/police_exit";
	/**
	 * 修改密码
	 */
	public static final String POLICE_UPDATEPWD = BASE_URL+"/police_updatePwd";
	/**
	 * 获取警察个人信息
	 */
	public static final String POLICE_GETMYINFO = BASE_URL + "/police_getMyInfo";
	/**
	 * 更新联系方式
	 */
	public static final String POLICE_UPDATELINKINFO = BASE_URL+"/police_updateLinkInfo";
	/**
	 * 获取历史任务
	 */
	public static final String POLICE_GETHISTORYTASKS = BASE_URL+"/police_getHistoryTasks";
	/**
	 * 提交任务
	 */
	public static final String POLICE_SUBMITTASK = BASE_URL+"/police_submitTask";
	/**
	 * 查看当前任务   Task currTask
	 */
	public static final String POLICE_GETCURRTASK = BASE_URL+"/police_getCurrTask";
	/**
	 * 接警
	 */
	public static final String POLICE_ACCEPTTASK = BASE_URL+"/police_acceptTask";
	/**
	 * 待接受任务
	 */
	public static final String POLICE_NOACCEPTASKLIST = BASE_URL+"/police_noAccepTaskList";
	/**
	 * 查看单个待接任务
	 */
	public static final String POLICE_GETTASKINFO = BASE_URL + "/police_getTask";
	/**
	 * 查看报警人信息  userId  . User user
	 */
	public static final String POLICE_GETUSER = BASE_URL+"/police_getUser";
	/**
	 * 查看附近警员  无 .List<Police> aroundPolices
	 */
	public static final String POLICE_GETAROUNDPOLICES = BASE_URL+"/police_getAroundPolices";
	/**
	 * 刷新坐标（轮询）  x,y
	 */
	public static final String POLICE_REFRESHCOORD = BASE_URL+"/police_refreshCoord";
	
	public static final String SEND_ALARM = BASE_URL+"/police_sendAlarm";

}
