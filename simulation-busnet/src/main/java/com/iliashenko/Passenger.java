package com.iliashenko;

import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.iliashenko.utils.Time;

/**
 * Class that represent's passenger entity.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 26-02-2019
 *
 */
public class Passenger implements Cloneable {
	private static final AtomicLong COUNTER = new AtomicLong(0);
	private final long idPassenger;

	private BusStop start;
	private BusStop destination;
	private Time maxWaitTime;

	private Time enterTime;
	private Time exitTime;
	private Bus bus;
	private BusRoute busRoute;

	public Passenger(BusStop start, BusStop destination, Time maxWaitTime) {
		super();
		this.idPassenger = COUNTER.incrementAndGet();
		this.start = start;
		this.destination = destination;
		this.maxWaitTime = maxWaitTime;
		this.busRoute = this.getDestinationRoute();
	}

	public BusRoute getDestinationRoute() {
		Set<BusRoute> sameRoutes = this.start.getPassingRoutes();
		sameRoutes.retainAll(this.destination.getPassingRoutes());
		sameRoutes.stream().sorted(Comparator.comparing(BusRoute::getPrice)).collect(Collectors.toList());
		return sameRoutes.iterator().next();
	}

	public long getIdPassenger() {
		return idPassenger;
	}

	public BusStop getStart() {
		return start;
	}

	public void setStart(BusStop start) {
		this.start = start;
	}

	public BusStop getDestination() {
		return destination;
	}

	public void setDestination(BusStop destination) {
		this.destination = destination;
	}

	public Time getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(Time maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	public BusRoute getBusRoute() {
		return busRoute;
	}

	public void setBusRoute(BusRoute busRoute) {
		this.busRoute = busRoute;
	}

	public Time getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(Time enterTime) {
		this.enterTime = enterTime;
	}

	public Time getExitTime() {
		return exitTime;
	}

	public void setExitTime(Time exitTime) {
		this.exitTime = exitTime;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	@Override
	/**
	 * Implementation of prototype pattern.
	 */
	protected Passenger clone() throws CloneNotSupportedException {
		return new Passenger(this.start, this.destination, this.maxWaitTime);
	}

	@Override
	public String toString() {
		return String.format("Passenger - id[%d] - max wait to: %s", idPassenger, maxWaitTime.toString());
	}

}
