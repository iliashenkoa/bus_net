package com.iliashenko.utils;

import com.iliashenko.BusStop;

/**
 * Util class to calculate distance between two stops.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 26-02-2019
 *
 */
public class MathDistance {

	private MathDistance() {
		super();
	}

	public static double distBetween2Dots(BusStop bs1, BusStop bs2) {
		return distByCoord(bs1.getxCoordinate(), bs2.getxCoordinate(), bs1.getyCoordinate(), bs1.getyCoordinate());
	}

	public static double distByCoord(double bs1x, double bs2x, double bs1y, double bs2y) {
		// convert to radians
		double l1 = Math.toRadians(bs1x);
		double l2 = Math.toRadians(bs2x);
		double g1 = Math.toRadians(bs1y);
		double g2 = Math.toRadians(bs2y);

		// do the spherical trig calculation
		double angle = Math.acos(Math.sin(l1) * Math.sin(l2) + Math.cos(l1) * Math.cos(l2) * Math.cos(g1 - g2));

		// convert back to degrees
		angle = Math.toDegrees(angle);

		// each degree on a great circle of Earth is 111.111 km
		return 111.111 * 1000 * angle;
	}

}
