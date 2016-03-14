package com.inforun.safecitypolice.entity;

/**
 * 经纬度实体类
 *  xiongchaoxi
 */
public class Location {
    private int id;
    private String  x;//经度
    private String y;//纬度

  

    public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
