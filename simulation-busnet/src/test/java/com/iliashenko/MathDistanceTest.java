package com.iliashenko;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.iliashenko.utils.MathDistance;

public class MathDistanceTest {

	@Test
	public void distBetween2DotsTest() {
		assertEquals(0.0, MathDistance.distBetween2Dots(new BusStop("test1", 100, 100), new BusStop("test2", 100, 100)),
				0.1);
	}

	@Test
	public void distByCoordTest() {
		assertEquals(0.0, MathDistance.distByCoord(100, 100, 100, 100), 0.1);
		assertEquals(633007, MathDistance.distByCoord(55.45, 59.53, 37.37, 30.15), 13000);
	}

}
