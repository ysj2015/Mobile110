package com.inforun.safecitypolice.entity;

import java.util.Date;

import com.inforun.safecitypolice.finals.ConstantUtil;


/**
 * 派警任务类
 *
 * @author Administrator
 */
public class Task extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private int id;
	private byte acceptStatus = ConstantUtil.ACCEPT_STATUS_NEW;// 接受状态 0未接受 1
																// 进行中 2提交
	private String taskContent;// 任务内容
	private Date appointTime;// 派警（任命）时间---

	private Date acceptTime;// 接警时间
	private Date submitTime;// 提交时间
	private String result;// 处理报告
	private int policeId;// 属于哪个警察的任务

	private AlarmInfo alarmInfo;// 对应报警信息

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
