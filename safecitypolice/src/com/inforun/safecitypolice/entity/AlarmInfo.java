package com.inforun.safecitypolice.entity;

import java.util.Date;

import com.inforun.safecitypolice.finals.ConstantUtil;



/**
 * 报警信息类
 * 
 * @author xiongchaoxi
 * 
 */
public class AlarmInfo extends AbstractModel {
	private static final long serialVersionUID = 1L;
	// 初始化的
	private int id;
	private int userId;// 报警用户
	private String alarmText;// 报警文字
	private String alarmVoice;// 音频
	private String alarmImg;// 图片
	private String alarmVideo;// 视频
	
	private Date alarmTime;// 报警时间
	private String coordStr;// 事发地的坐标 "121.121 41.21212" 拼接字符串

	private byte alarmStatus = ConstantUtil.ALARM_STATUS_NEW;// 状态 0:未处理 1:处理中的
																// 2:完成的
	private byte alarmType = ConstantUtil.ALARM_TYPE_TRUE;// 0真警 1 假警 2重复报警

	public AlarmInfo() {
		super();
	}

	public AlarmInfo(int userId, String alarmText, String alarmVoice, String alarmImg, String alarmVideo,
			Date alarmTime, String coordStr) {
		super();
		this.userId = userId;
		this.alarmText = alarmText;
		this.alarmVoice = alarmVoice;
		this.alarmImg = alarmImg;
		this.alarmVideo = alarmVideo;
		this.alarmTime = alarmTime;
		this.coordStr = coordStr;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAlarmText() {
		return alarmText;
	}

	public void setAlarmText(String alarmText) {
		this.alarmText = alarmText;
	}

	public String getAlarmVoice() {
		return alarmVoice;
	}

	public void setAlarmVoice(String alarmVoice) {
		this.alarmVoice = alarmVoice;
	}

	public String getAlarmImg() {
		return alarmImg;
	}

	public void setAlarmImg(String alarmImg) {
		this.alarmImg = alarmImg;
	}

	public String getAlarmVideo() {
		return alarmVideo;
	}

	public void setAlarmVideo(String alarmVideo) {
		this.alarmVideo = alarmVideo;
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getCoordStr() {
		return coordStr;
	}

	public void setCoordStr(String coordStr) {
		this.coordStr = coordStr;
	}

	public byte getAlarmStatus() {
		return alarmStatus;
	}

	public void setAlarmStatus(byte alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	public byte getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(byte alarmType) {
		this.alarmType = alarmType;
	}

}
