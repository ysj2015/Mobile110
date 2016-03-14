package com.inforun.safecitypolice.entity;

import java.sql.Blob;
import java.util.Date;

/**
 * 用户信息（群众）
 *  xiongchaoxi
 */
public class User {
    private long id;
    private String loginName;// 注册的用户名
	private Date lastLoginTime;
    private Location coord;

	public Location getCoord() {
		return coord;
	}

	public void setCoord(Location coord) {
		this.coord = coord;
	}

	public UserDetails getDetails() {
		return details;
	}

	public void setDetails(UserDetails details) {
		this.details = details;
	}

	/* 身份信息 */
    private UserDetails details;

    private long loginCount = 0;// 登陆次数


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    public long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(long loginCount) {
        this.loginCount = loginCount;
    }

	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	
	public void setLastLoginTime(Date date) {
		this.lastLoginTime = date;
	}
}
