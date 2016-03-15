package com.inforun.safecitypeople.entity;

import java.util.Date;

public class Police extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private int id;
	private String policeNo;
	private transient String pwd;
	private transient String token;
	private int loginCount = 0;

	private PoliceDetails details;
	private Coord coord;
	// @Expose
	private Task currTask;
	private boolean onLine = false;
	private Date lastLoginTime;

	public Police() {
		super();
	}

	public Police(String policeNo, String pwd, PoliceDetails details, Coord coord) {
		super();
		this.policeNo = policeNo;
		this.pwd = pwd;
		this.details = details;
		this.coord = coord;
	}

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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public PoliceDetails getDetails() {
		return details;
	}

	public void setDetails(PoliceDetails details) {
		this.details = details;
	}

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public Task getCurrTask() {
		return currTask;
	}

	public void setCurrTask(Task currTask) {
		this.currTask = currTask;
	}

	public boolean isOnLine() {
		return onLine;
	}

	public void setOnLine(boolean onLine) {
		this.onLine = onLine;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Override
	public String toString() {
		return "Police [id=" + id + ", policeNo=" + policeNo + ", loginCount=" + loginCount + ", details=" + details
				+ ", coord=" + coord + ", currTask=" + currTask + ", onLine=" + onLine + ", lastLoginTime="
				+ lastLoginTime + "]";
	}

}
