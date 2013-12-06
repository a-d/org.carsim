package org.carsim.agent.data;

public class Rectangle {
	public double top;
	public double left;
	public double bottom;
	public double right;
	public Point center;
	public double width;
	public double height;

	public Rectangle(double top, double left, double bottom, double right) {
		this.top = top;
		this.left = left;
		this.bottom = bottom;
		this.right = right;
		this.width = Math.abs(left-right);
		this.height = Math.abs(top-bottom);
		this.center = new Point(top + (top-bottom>0 ? -1 : 1)* height/2, left + (right-left>0 ? 1 : -1)* width/2);
	}
}
