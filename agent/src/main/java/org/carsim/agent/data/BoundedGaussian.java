package org.carsim.agent.data;

import java.util.Random;

public class BoundedGaussian {
	Random r = new Random();
	private double bounds;
	public BoundedGaussian(double bounds) {
		this.bounds = bounds;
	}
	public double random() {
		double v;
		do {
			v = r.nextGaussian();
		}
		while(v<-bounds || v>bounds);
		return v/bounds;
	}
}
