package com.iliashenko.simulation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.iliashenko.utils.Time;

@XmlType(name = "network-config")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * Class that represent's simulation configuration entity.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 26-02-2019
 *
 */
public class Configuration {

	@XmlElement(name = "buses-capacity")
	private int busesCapacity;
	@XmlElement(name = "buses-speed")
	private double busesSpeed;
	@XmlElement(name = "buses-count-per-route")
	private int busesCount;
	@XmlElement(name = "simulation-start-time")
	private Time startTime;
	@XmlElement(name = "simulation-end-time")
	private Time endTime;
	@XmlElement(name = "time-acceleration-coeff")
	private double accelerationCoeff;

	public Configuration() {

	}

	public Configuration(int busesCapacity, double busesSpeed, int busesCount, Time startTime, Time endTime,
			double accelerationCoeff) {
		super();
		this.busesCapacity = busesCapacity;
		this.busesSpeed = busesSpeed;
		this.busesCount = busesCount;
		this.startTime = startTime;
		this.endTime = endTime;
		this.accelerationCoeff = accelerationCoeff;
	}

	public int getBusesCapacity() {
		return busesCapacity;
	}

	public void setBusesCapacity(int busesCapacity) {
		this.busesCapacity = busesCapacity;
	}

	public double getBusesSpeed() {
		return busesSpeed;
	}

	public void setBusesSpeed(double busesSpeed) {
		this.busesSpeed = busesSpeed;
	}

	public int getBusesCount() {
		return busesCount;
	}

	public void setBusesCount(int busesCount) {
		this.busesCount = busesCount;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public double getAccelerationCoeff() {
		return accelerationCoeff;
	}

	public void setAccelerationCoeff(double accelerationCoeff) {
		this.accelerationCoeff = accelerationCoeff;
	}

}
