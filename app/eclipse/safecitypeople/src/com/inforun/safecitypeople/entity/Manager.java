package com.inforun.safecitypeople.entity;

public class Manager extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private int id;
	private String loginName;
	private String pwd;
	private int permission;
	private String name;

	public Manager() {
		super();
	}

	public Manager(String loginName, String pwd, int permission, String name) {
		super();
		this.loginName = loginName;
		this.pwd = pwd;
		this.permission = permission;
		this.name = name;
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

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
