package com.iliashenko.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iliashenko.observer.WatchDogTime;

/**
 * Class that represent's timer entity. Handler for every minute tick.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 26-02-2019
 *
 */
public class Timer implements Runnable {

	private static final Logger TIMER_LOGGER = LoggerFactory.getLogger(Timer.class);

	public enum Status {
		STOPPED, STARTED, RESET
	}

	private Time currentSimTime;
	private Time startSimTime;
	private Time endSimTime;
	private double accelerationCoeff;
	private volatile Status status = Status.RESET;
	private Set<WatchDogTime> watchDogs;

	public Timer(Time startSimTime, Time endSimTime, double accelerationCoeff) {
		super();
		this.currentSimTime = new Time(startSimTime);
		this.startSimTime = startSimTime;
		this.endSimTime = endSimTime;
		this.accelerationCoeff = accelerationCoeff;
		watchDogs = new HashSet<>();
		this.status = Status.STARTED;
	}

	public Time getCurrentSimTime() {
		return currentSimTime;
	}

	public void setCurrentSimTime(Time currentSimTime) {
		this.currentSimTime = currentSimTime;
	}

	public Time getStartSimTime() {
		return startSimTime;
	}

	public void setStartSimTime(Time startSimTime) {
		this.startSimTime = startSimTime;
	}

	public Time getEndSimTime() {
		return endSimTime;
	}

	public void setEndSimTime(Time endSimTime) {
		this.endSimTime = endSimTime;
	}

	public double getAccelerationCoeff() {
		return accelerationCoeff;
	}

	public void setAccelerationCoeff(double accelerationCoeff) {
		this.accelerationCoeff = accelerationCoeff;
	}

	@Override
	public void run() {
		try {
			while (currentSimTime.compareTo(endSimTime) != 0 && status.equals(Status.STARTED)) {
				TIMER_LOGGER.info("{}", currentSimTime);
				tick();
				for (WatchDogTime wd : watchDogs) {
					wd.update(currentSimTime);
				}
			}
		} catch (InterruptedException e) {
			TIMER_LOGGER.error(e.toString());
			Thread.currentThread().interrupt();
		}
	}

	public synchronized void reset() {
		currentSimTime = startSimTime;
		status = Status.RESET;
	}

	public synchronized long stop() {
		status = Status.STOPPED;
		return this.currentSimTime.getDate().getTime() - this.startSimTime.getDate().getTime();
	}

	private void tick() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep((long) (TimeUnit.MINUTES.toMillis(1) / accelerationCoeff));
		currentSimTime.setMinutes(currentSimTime.getMinutes() + 1);
		if (currentSimTime.getMinutes() == Time.MINUTES_IN_HOUR) {
			currentSimTime.setMinutes(0);
			currentSimTime.setHours(currentSimTime.getHours() + 1);
		}
		if (currentSimTime.getHours() == Time.HOURS_IN_DAY) {
			currentSimTime.setHours(0);
			currentSimTime.setDay(currentSimTime.getDay() + 1);
		}
		if (currentSimTime.getDay() == currentSimTime.countDayInMonth()) {
			currentSimTime.setDay(1);
			currentSimTime.setMonth(currentSimTime.getMonth() + 1);
		}
		if (currentSimTime.getMonth() == Time.MONTHES_IN_YEAR) {
			currentSimTime.setMonth(1);
			currentSimTime.setYear(currentSimTime.getYear() + 1);
		}
	}

	public void addWatchDog(WatchDogTime wd) {
		if (!watchDogs.contains(wd)) {
			watchDogs.add(wd);
		}
	}

}
