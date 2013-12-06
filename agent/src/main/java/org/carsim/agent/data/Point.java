package org.carsim.agent.data;

public class Point {
	public double longitude;
	public double latitude;

	public Point(double latitude, double longitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Point(Object a, Object b) {
		this(Double.parseDouble(a.toString()), Double.parseDouble(b.toString()));
	}

	@Override
	public String toString() {
		return "["+latitude+","+longitude+"]";
	}
}
