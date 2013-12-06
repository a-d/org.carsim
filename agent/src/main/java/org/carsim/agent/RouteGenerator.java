package org.carsim.agent;
import java.util.Arrays;

import org.carsim.agent.data.BoundedGaussian;
import org.carsim.agent.data.Point;
import org.carsim.agent.data.Rectangle;
import org.carsim.agent.data.Route;
import org.carsim.agent.data.RouteManager;

public class RouteGenerator {


	private BoundedGaussian boundedGaussian;
	private Rectangle rectangle;

	public static void main(String[] args) {
		Rectangle rect = new Rectangle(52.659726, 13.162994, 52.34373, 13.712311);
		double alpha = 4;
		
		RouteGenerator rg = new RouteGenerator(rect, alpha);
		Route[] vs = new Route[1];
		for (int i = 0; i < vs.length; i++) {
			vs[i] = rg.randomRoute();
		}
		System.out.println(Arrays.toString(vs));
	}
	
	

	private Route randomRoute() {
		Point start = randomCoordinate();
		Point end = randomCoordinate();
		Route route = RouteManager.getRoute(start, end);
		return route;
	}




	
	public RouteGenerator(Rectangle rect, double alpha) {
		this.rectangle = rect;
		this.boundedGaussian = new BoundedGaussian(alpha);
	}

	public Point randomCoordinate()
	{
		double longitude = rectangle.center.longitude
				+ boundedGaussian.random()
				* rectangle.width/2;
		double latitude = rectangle.center.latitude
				+ boundedGaussian.random()
				* rectangle.height/2;
		return new Point(latitude, longitude);
	}
	
}
