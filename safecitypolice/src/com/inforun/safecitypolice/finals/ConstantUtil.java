package com.inforun.safecitypolice.finals;

public class ConstantUtil {

	/** 返回值 json 开关 */
	public static final boolean OPENJSON_POLICE = true;
	public static final boolean OPENJSON_USER = true;

	/** session 的生命周期 */
	public static final int HTTPSESSION_life = 20 * 60;

	/** 字符串拼接符号 (一个空格)*/
	public static final String SPLIT_STR =" ";//一个空格
	/** upload */
	// public static final String PATH_BAOJING_IMAGE = "/upload/image";
	// public static final String PATH_BAOJING_VIDEO = "/upload/video";
	/** 坐标 */
	public static final double COORD_AROUNDDISTACE = 2000;

	/** jpush */
	public static final String appKey = "255a4bc1e5117c04ed44b142";// 我的
	public static final String masterSecret = "4187320b6d59ea9c8d70bad6";

	public static final String appKey2 = "56e6e9b7ccee2691bff491bf";// 客户端的
	public static final String masterSecret2 = "b254cbbd52f84739f1ab5909";

	/** 报警信息 */
	/** 报警 类别 0 表示真警， 1 表示假警， 2 表示重复报警 */
	public static final byte ALARM_TYPE_TRUE = 0;
	public static final byte ALARM_TYPE_FALSE = 1;
	public static final byte ALARM_TYPE_REPETITION = 2;
	/** 报警 状态 0:未处理 1:处理中的 2:完成的 */
	public static final byte ALARM_STATUS_NEW = 0;
	public static final byte ALARM_STATUS_DOING = 1;
	public static final byte ALARM_STATUS_END = 2;

	/** 任务信息 */
	public static final String PAIJINGINFO_TITLE = "任务通知（标题）";
	// 接受状态 0新的 1处理中 2已经提交
	public static final byte ACCEPT_STATUS_TIMEOUT = -1;
	public static final byte ACCEPT_STATUS_NEW = 0;
	public static final byte ACCEPT_STATUS_DOING = 1;
	public static final byte ACCEPT_STATUS_END = 2;
	/** 接警超时时间 */
	public static final int ACCEPT_OUTTIME=10*60;//秒
	/** 分页 */
	public static final int PAGE_SIZE = 10;
	/** 操作成功的返回字符串 */
	public static final String RETURN_OK = "OK";
}
