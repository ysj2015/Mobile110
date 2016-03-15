package com.inforun.safecitypeople.entity;

import java.util.Date;

public class Task extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private int id;
	private byte acceptStatus;
	private String taskContent;
	private Date appointTime;

	private Date acceptTime;
	private Date submitTime;
	private String result;//
	private int policeId;//

	private AlarmInfo alarmInfo;//

	public Task() {
		super();
	}

	public Task(int policeId, String taskContent, Date appointTime, AlarmInfo alarmInfo) {
		super();
		this.taskContent = taskContent;
		this.appointTime = appointTime;
		this.policeId = policeId;
		this.alarmInfo = alarmInfo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getAcceptStatus() {
		return acceptStatus;
	}

	public void setAcceptStatus(byte acceptStatus) {
		this.acceptStatus = acceptStatus;
	}

	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getPoliceId() {
		return policeId;
	}

	public void setPoliceId(int policeId) {
		this.policeId = policeId;
	}

	public AlarmInfo getAlarmInfo() {
		return alarmInfo;
	}

	public void setAlarmInfo(AlarmInfo alarmInfo) {
		this.alarmInfo = alarmInfo;
	}

	public String getTaskContent() {
		return taskContent;
	}

	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}

	public Date getAppointTime() {
		return appointTime;
	}

	public void setAppointTime(Date appointTime) {
		this.appointTime = appointTime;
	}

}
