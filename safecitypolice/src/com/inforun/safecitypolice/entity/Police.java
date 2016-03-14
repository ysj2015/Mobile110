package com.inforun.safecitypolice.entity;

import java.util.Date;


/**
 * 警察
 * 
 * @author Administrator
 * 
 */
public class Police extends AbstractModel {
	private int id;
	private String policeNo;
	private int loginCount = 0;// 登陆次数
	private Date lastLoginTime;

	private PoliceDetails details;// 详细信息
	private Location coord;// 坐标
	private boolean onLine = false;// 在线状态
	private Task currTask;//当前任务 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPoliceNo() {
		return policeNo;
	}

	public void setPoliceNo(String pliceNo) {
		this.policeNo = pliceNo;
	}


	public PoliceDetails getDetails() {
		return details;
	}

	public void setDetails(PoliceDetails details) {
		this.details = details;
	}


	public boolean isOnLine() {
		return onLine;
	}

	public void setOnLine(boolean onLine) {
		this.onLine = onLine;
	}

	public Task getCurrTask() {
		return currTask;
	}
	
	public void setCurrTask(Task t) {
		currTask = t;
	}
	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public Location getCoord() {
		return coord;
	}

	public void setCoord(Location coord) {
		this.coord = coord;
	}
	
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	
	public void setLastLoginTime(Date date) {
		this.lastLoginTime = date;
	}
}
