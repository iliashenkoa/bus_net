package com.iliashenko.db;

/**
 * Many to many table between route and stop representation in dto.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 25-02-2019
 * 
 */
public class BusRouteStop {

	private int busStopId;
	private int busRouteNumber;

	public int getBusStopId() {
		return busStopId;
	}

	public void setBusStopId(int busStopId) {
		this.busStopId = busStopId;
	}

	public int getBusRouteNumber() {
		return busRouteNumber;
	}

	public void setBusRouteNumber(int busRouteNumber) {
		this.busRouteNumber = busRouteNumber;
	}

	public BusRouteStop(int busStopId, int busRouteNumber) {
		super();
		this.busStopId = busStopId;
		this.busRouteNumber = busRouteNumber;
	}

}
