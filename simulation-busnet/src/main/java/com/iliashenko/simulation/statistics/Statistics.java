package com.iliashenko.simulation.statistics;

import java.sql.Timestamp;

import com.opencsv.bean.CsvBindByPosition;

/**
 * Class that represent's statistics which contains static data.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 26-02-2019
 *
 */
public class Statistics {
	@CsvBindByPosition(position = 0)
	private Timestamp startTime;
	@CsvBindByPosition(position = 1)
	private Timestamp endTime;
	@CsvBindByPosition(position = 2)
	private int timeSimulation;
	@CsvBindByPosition(position = 3)
	private double loadPercentage;
	@CsvBindByPosition(position = 4)
	private int routesCount;
	@CsvBindByPosition(position = 5)
	private int busCount;
	@CsvBindByPosition(position = 6)
	private long passengersCount;

	public Statistics() {
		super();
	}

	public Statistics(Timestamp startTime, Timestamp endTime, int timeSimulation, int loadPercentage, int routesCount,
			int busCount, int passengersCount) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.timeSimulation = timeSimulation;
		this.loadPercentage = loadPercentage;
		this.routesCount = routesCount;
		this.busCount = busCount;
		this.passengersCount = passengersCount;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public int getTimeSimulation() {
		return timeSimulation;
	}

	public void setTimeSimulation(int timeSimulation) {
		this.timeSimulation = timeSimulation;
	}

	public double getLoadPercentage() {
		return loadPercentage;
	}

	public void setLoadPercentage(double loadPercentage) {
		this.loadPercentage = loadPercentage;
	}

	public int getRoutesCount() {
		return routesCount;
	}

	public void setRoutesCount(int routesCount) {
		this.routesCount = routesCount;
	}

	public int getBusCount() {
		return busCount;
	}

	public void setBusCount(int busCount) {
		this.busCount = busCount;
	}

	public long getPassengersCount() {
		return passengersCount;
	}

	public void setPassengersCount(long passengersCount) {
		this.passengersCount = passengersCount;
	}

}
