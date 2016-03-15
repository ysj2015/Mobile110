package com.inforun.safecitypeople.entity;

import java.util.Date;

public class UserDetails extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	// private int age;
	private Date birthday;
	private char sex;
	private String tel;
	private String shenFenId;
	private String address;
	private String photo;

	public UserDetails() {
		super();
	}

	public UserDetails(String name, char sex, Date birthday,String tel,
			String shenFenId, String address, String photo) {
		super();
		this.name = name;
		this.birthday = birthday;
		this.sex = sex;
		this.tel = tel;
		this.shenFenId = shenFenId;
		this.address = address;
		this.photo = photo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getShenFenId() {
		return shenFenId;
	}

	public void setShenFenId(String shenFenId) {
		this.shenFenId = shenFenId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
