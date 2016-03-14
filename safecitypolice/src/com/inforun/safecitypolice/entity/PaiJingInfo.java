package com.inforun.safecitypolice.entity;

import java.util.Date;

/**
 * 派警信息
 * 
 * @author xiongchaoxi
 * 
 */
public class PaiJingInfo {
	private long id;
	private String baoJingUser;// // 报警人的登录名
	private String message;
	private boolean accept;// 接受状态
	private Date time;

	public PaiJingInfo() {
		super();
	}

	public PaiJingInfo(String baoJingUser, String message, Date time) {
		super();
		this.baoJingUser = baoJingUser;
		this.message = message;
		this.time = time;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isAccept() {
		return accept;
	}

	public void setAccept(boolean accept) {
		this.accept = accept;
	}

	public String getBaoJingUser() {
		return baoJingUser;
	}

	public void setBaoJingUser(String baoJingUser) {
		this.baoJingUser = baoJingUser;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "PaiJingInfo{" +
				"id=" + id +
				", baoJingUser='" + baoJingUser + '\'' +
				", message='" + message + '\'' +
				", accept=" + accept +
				", time=" + time +
				'}';
	}
}
