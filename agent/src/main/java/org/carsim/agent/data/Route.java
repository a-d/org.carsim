package org.carsim.agent.data;

import java.util.Arrays;

public class Route {

	public Point[] route;
	public Integer duration;
	public Integer distance;
	public Point endPoint;
	public Point startPoint;
	public String startStreet;
	public String endStreet;

	public Route() {
	}
	
	@Override
	public String toString() {
		return Arrays.toString(route);
	}
}
