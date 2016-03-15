package com.inforun.safecitypeople.entity;

import java.util.Date;

public class AlarmInfo extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private int id;
	private int userId;
	private String alarmText;
	private String alarmVoice;
	private String alarmImg;
	private String alarmVideo;

	private Date alarmTime;
	private String coordStr;

	private byte alarmStatus;
	private byte alarmType;

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

	@Override
	public String toString() {
		return "AlarmInfo [id=" + id + ", userId=" + userId + ", alarmText=" + alarmText + ", alarmVoice=" + alarmVoice
				+ ", alarmImg=" + alarmImg + ", alarmVideo=" + alarmVideo + ", alarmTime=" + alarmTime + ", coordStr="
				+ coordStr + ", alarmStatus=" + alarmStatus + ", alarmType=" + alarmType + "]";
	}

}
