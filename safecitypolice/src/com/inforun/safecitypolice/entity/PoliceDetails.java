package com.inforun.safecitypolice.entity;

import java.util.Date;


/**
 * 警察资料
 * 
 * @author xiongchaoxi
 * 
 */
public class PoliceDetails extends AbstractModel {
	private int id;
	private String name;//姓名
	private String birthday;//生日
	private String sex;//性别
	private String tel;//电话
	private String shenFenId;//身份证
	private String address;//地址
	private String photo;// 照片URL


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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

}
