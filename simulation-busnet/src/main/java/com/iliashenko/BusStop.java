
package com.iliashenko;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iliashenko.observer.WatchDogTime;
import com.iliashenko.utils.Time;

@XmlType(name = "bus-stop")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * Class that represent's bus stop entity. Every bus stop can be part of several
 * routes. It has queues with passengers on some route.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 26-02-2019
 *
 */
public final class BusStop implements WatchDogTime, Comparable<BusStop> {

	private static final Logger STOP_LOGGER = LoggerFactory.getLogger(BusStop.class);

	private static final AtomicInteger COUNTER = new AtomicInteger(0);
	@XmlAttribute
	private final int idBusStop;

	@XmlElement(name = "bus-stop-name")
	private String name;
	@XmlAttribute(name = "park-space-cnt")
	private int parkingSpacesCount;
	@XmlTransient
	private Lock busStopLock;
	@XmlTransient
	private List<Bus> stoppedBuses;
	@XmlAttribute(name = "x-coord")
	private double xCoordinate;
	@XmlAttribute(name = "y-coord")
	private double yCoordinate;

	@XmlTransient
	private Map<BusRoute, ConcurrentLinkedQueue<Passenger>> passengersQueues;

	@XmlTransient
	private Semaphore semaphore;
	private static final int DEFAULT_BUS_CAPACITY = 3;

	public BusStop() {
		this.idBusStop = COUNTER.incrementAndGet();
		this.busStopLock = new ReentrantLock(true);
		this.parkingSpacesCount = this.getParkingSpacesCount();
		this.stoppedBuses = new ArrayList<>();
		this.passengersQueues = new ConcurrentHashMap<>();

	}

	public BusStop(String name, double xCoordinate, double yCoordinate) {
		super();
		this.idBusStop = COUNTER.incrementAndGet();
		this.name = name;
		this.busStopLock = new ReentrantLock(true);
		this.parkingSpacesCount = this.getParkingSpacesCount();
		this.semaphore = new Semaphore(this.getParkingSpacesCount());
		this.stoppedBuses = new ArrayList<>();
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.passengersQueues = new ConcurrentHashMap<>();
	}

	public BusStop(String name, int parkingSpacesCount,
			Map<BusRoute, ConcurrentLinkedQueue<Passenger>> passengersQueues) {
		super();
		this.idBusStop = COUNTER.incrementAndGet();
		this.name = name;
		this.parkingSpacesCount = parkingSpacesCount;
		this.passengersQueues = passengersQueues;
		this.busStopLock = new ReentrantLock(true);
		this.semaphore = new Semaphore(parkingSpacesCount);
		this.stoppedBuses = new ArrayList<>();
	}

	public int getIdBusStop() {
		return idBusStop;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParkingSpacesCount() {
		if (parkingSpacesCount < 1) {
			return DEFAULT_BUS_CAPACITY;
		} else {
			return parkingSpacesCount;
		}
	}

	public void setParkingSpacesCount(int parkingSpacesCount) {
		this.parkingSpacesCount = parkingSpacesCount;
	}

	public Map<BusRoute, ConcurrentLinkedQueue<Passenger>> getPassengersQueues() {
		return passengersQueues;
	}

	public void setPassengersQueues(ConcurrentMap<BusRoute, ConcurrentLinkedQueue<Passenger>> passengersQueues) {
		this.passengersQueues = passengersQueues;
	}

	public Semaphore getSemaphore() {
		return semaphore;
	}

	public void setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public Lock getBusStopLock() {
		return busStopLock;
	}

	public void setBusStopLock(Lock busStopLock) {
		this.busStopLock = busStopLock;
	}

	public Set<BusRoute> getPassingRoutes() {
		return passengersQueues.keySet();
	}

	public void addToRoute(BusRoute route) {
		passengersQueues.put(route, new ConcurrentLinkedQueue<Passenger>());
	}

	public void removeFromRoute(BusRoute route) {
		passengersQueues.remove(route);
	}

	public List<Bus> getStoppedBuses() {
		return stoppedBuses;
	}

	public void setStoppedBuses(List<Bus> stoppedBuses) {
		this.stoppedBuses = stoppedBuses;
	}

	public double getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public double getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public void initSemaphore() {
		this.semaphore = new Semaphore(this.parkingSpacesCount);
	}

	public boolean addPassenger(Passenger passenger) {
		Queue<Passenger> queueToAdd = this.passengersQueues.get(passenger.getDestinationRoute());
		if (queueToAdd != null) {
			queueToAdd.offer(passenger);
			return true;
		}
		return false;
	}

	public boolean removePassenger(Passenger passenger) {
		Queue<Passenger> queueToRemove = this.passengersQueues.get(passenger.getDestinationRoute());
		if (queueToRemove != null) {
			queueToRemove.remove(passenger);
			return true;
		}
		return false;
	}

	public Passenger removeFirstPassenger(BusRoute route) {
		Queue<Passenger> queueToRemove = this.passengersQueues.get(route);
		if (queueToRemove != null) {
			return queueToRemove.poll();
		}
		return null;
	}

	public Passenger removeLongWaitingPassengers(Time currentTime) {
		Set<BusRoute> routes = passengersQueues.keySet();
		for (BusRoute route : routes) {
			Queue<Passenger> queue = passengersQueues.get(route);
			for (Passenger p : queue) {
				if (p.getMaxWaitTime().compareTo(currentTime) <= 0) {
					STOP_LOGGER.debug("Passenger id[{}] is gone from stop id[{}]", p.getIdPassenger(), this.idBusStop);
					removePassenger(p);
				}
			}
		}
		return null;
	}

	public void addBus(Bus bus) {
		stoppedBuses.add(bus);
	}

	public boolean addToParking(Bus bus) throws InterruptedException {
		return semaphore.tryAcquire();
	}

	public void removeFromParking(Bus bus) {
		semaphore.release();
	}

	@Override
	public String toString() {
		return String.format("BusStop - id[%d] - %s, parkingSpacesCount=%d", idBusStop, name, parkingSpacesCount);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((busStopLock == null) ? 0 : busStopLock.hashCode());
		result = prime * result + idBusStop;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + parkingSpacesCount;
		result = prime * result + ((passengersQueues == null) ? 0 : passengersQueues.hashCode());
		result = prime * result + ((semaphore == null) ? 0 : semaphore.hashCode());
		result = prime * result + ((stoppedBuses == null) ? 0 : stoppedBuses.hashCode());
		long temp;
		temp = Double.doubleToLongBits(xCoordinate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(yCoordinate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusStop other = (BusStop) obj;
		if (busStopLock == null) {
			if (other.busStopLock != null)
				return false;
		} else if (!busStopLock.equals(other.busStopLock)) {
			return false;
		}
		if (idBusStop != other.idBusStop)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (parkingSpacesCount != other.parkingSpacesCount)
			return false;
		if (passengersQueues == null) {
			if (other.passengersQueues != null)
				return false;
		} else if (!passengersQueues.equals(other.passengersQueues)) {
			return false;
		}
		if (semaphore == null) {
			if (other.semaphore != null)
				return false;
		} else if (!semaphore.equals(other.semaphore)) {
			return false;
		}
		if (stoppedBuses == null) {
			if (other.stoppedBuses != null) {
				return false;
			}
		} else if (!stoppedBuses.equals(other.stoppedBuses)) {
			return false;
		}
		if (Double.doubleToLongBits(xCoordinate) != Double.doubleToLongBits(other.xCoordinate)
				|| Double.doubleToLongBits(yCoordinate) != Double.doubleToLongBits(other.yCoordinate)) {
			return false;
		}
		return true;
	}

	@Override
	public void update(Time currentTime) {
		removeLongWaitingPassengers(currentTime);
	}

	@Override
	public int compareTo(BusStop o) {
		int x = Double.compare(this.getxCoordinate(), this.yCoordinate);
		if (x == 0) {
			return Double.compare(this.getyCoordinate(), this.yCoordinate);
		} else {
			return x;
		}
	}
}
