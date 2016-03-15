package com.inforun.safecitypeople.entity;

public class Coord extends AbstractModel{
	private static final long serialVersionUID = 1L;
	private int id;
	private double x;
	private double y;

	public Coord() {
		super();
	}

	public Coord(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Coord [id=" + id + ", x=" + x + ", y=" + y + "]";
	}

}
