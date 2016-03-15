package com.inforun.safecitypeople.entity;

import java.util.Date;

public class User extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private int id;
	private String loginName;
	private transient String pwd;

	private UserDetails details;
	private Coord coord;

	private AlarmInfo currAlarm;
	private boolean onLine = false;
	private Date lastLoginTime;

	public User() {
		super();
	}

	public User(String loginName, String pwd, UserDetails details, Coord coord) {
		super();
		this.loginName = loginName;
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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public UserDetails getDetails() {
		return details;
	}

	public void setDetails(UserDetails details) {
		this.details = details;
	}

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public boolean isOnLine() {
		return onLine;
	}

	public void setOnLine(boolean onLine) {
		this.onLine = onLine;
	}

	public AlarmInfo getCurrAlarm() {
		return currAlarm;
	}

	public void setCurrAlarm(AlarmInfo currAlarm) {
		this.currAlarm = currAlarm;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

}
