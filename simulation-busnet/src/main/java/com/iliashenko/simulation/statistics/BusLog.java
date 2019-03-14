package com.iliashenko.simulation.statistics;

import java.sql.Timestamp;

import com.opencsv.bean.CsvBindByPosition;

/**
 * Class that represent's bus log which contains static data.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 26-02-2019
 *
 */
public class BusLog {
	@CsvBindByPosition(position = 0)
	private Timestamp timeMoment;
	@CsvBindByPosition(position = 1)
	private int busStopId;
	@CsvBindByPosition(position = 2)
	private int busRouteNum;
	@CsvBindByPosition(position = 3)
	private int busId;
	@CsvBindByPosition(position = 4)
	private int passengersCountEntry;
	@CsvBindByPosition(position = 5)
	private int passengersCountExit;
	@CsvBindByPosition(position = 6)
	private int passengersCountInBus;

	public BusLog(Timestamp timeMoment, int busStopId, int busRouteNum, int busId, int passengersCountEntry,
			int passengersCountExit, int passengersCountInBus) {
		super();
		this.timeMoment = timeMoment;
		this.busStopId = busStopId;
		this.busRouteNum = busRouteNum;
		this.busId = busId;
		this.passengersCountEntry = passengersCountEntry;
		this.passengersCountExit = passengersCountExit;
		this.passengersCountInBus = passengersCountInBus;
	}

	public Timestamp getTimeMoment() {
		return timeMoment;
	}

	public void setTimeMoment(Timestamp timeMoment) {
		this.timeMoment = timeMoment;
	}

	public int getBusStopId() {
		return busStopId;
	}

	public void setBusStopId(int busStopId) {
		this.busStopId = busStopId;
	}

	public int getBusRouteNum() {
		return busRouteNum;
	}

	public void setBusRouteNum(int busRouteNum) {
		this.busRouteNum = busRouteNum;
	}

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	public int getPassengersCountEntry() {
		return passengersCountEntry;
	}

	public void setPassengersCountEntry(int passengersCountEntry) {
		this.passengersCountEntry = passengersCountEntry;
	}

	public int getPassengersCountExit() {
		return passengersCountExit;
	}

	public void setPassengersCountExit(int passengersCountExit) {
		this.passengersCountExit = passengersCountExit;
	}

	public int getPassengersCountInBus() {
		return passengersCountInBus;
	}

	public void setPassengersCountInBus(int passengersCountInBus) {
		this.passengersCountInBus = passengersCountInBus;
	}

}
