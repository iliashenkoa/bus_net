package com.iliashenko;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iliashenko.observer.NotifierBus;
import com.iliashenko.observer.WatchDogBus;
import com.iliashenko.utils.Consts;
import com.iliashenko.utils.MathDistance;

/**
 * Class that represent's bus entity. Every bus is thread and notifier for
 * dispatcher observer. It notify observer on every stop.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 25-02-2019
 *
 */
public class Bus implements Runnable, NotifierBus {

	private static final Logger BUS_LOGGER = LoggerFactory.getLogger(Bus.class);

	private static final AtomicInteger COUNTER = new AtomicInteger(0);
	private final int idBus;
	private WatchDogBus wd;

	private int capacity;
	private BusRoute busRoute;
	private Direction direction;
	private long endOfLineBreak;
	private double speed;
	private double accelerationCoeff;
	private BusStop currentStop;
	private int currentEntry;
	private int currentExit;

	private Date startTripTime;

	List<Passenger> passengers = new ArrayList<>();

	List<Double> loadPercByRoutParts;

	public Bus(BusBuilder builder) {
		this.idBus = COUNTER.incrementAndGet();
		this.addObserver(builder.wd);
		this.capacity = builder.capacity;
		this.busRoute = builder.busRoute;
		this.direction = builder.direction;
		this.endOfLineBreak = builder.endOfLineBreak;
		this.speed = builder.speed;
		this.accelerationCoeff = builder.accelerationCoeff;
		this.passengers = new ArrayList<>();
		this.loadPercByRoutParts = new ArrayList<>();
		BUS_LOGGER.debug("Bus - {} was created", this.idBus);
	}

	public int getIdBus() {
		return idBus;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public BusRoute getBusRoute() {
		return busRoute;
	}

	public void setBusRoute(BusRoute busRoute) {
		this.busRoute = busRoute;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public long getEndOfLineBreak() {
		return endOfLineBreak;
	}

	public void setEndOfLineBreak(long endOfLineBreak) {
		this.endOfLineBreak = endOfLineBreak;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getAccelerationCoeff() {
		return accelerationCoeff;
	}

	public void setAccelerationCoeff(double accelerationCoeff) {
		this.accelerationCoeff = accelerationCoeff;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	public void addLoadPerc(double perc) {
		loadPercByRoutParts.add(Double.valueOf(perc));
	}

	public boolean hasPlaces() {
		return passengers.size() < capacity;
	}

	public List<Double> getLoadPercByRoutParts() {
		return loadPercByRoutParts;
	}

	public void clearLoadPercByRoutParts() {
		loadPercByRoutParts.clear();
	}

	public Date getStartTripTime() {
		return startTripTime;
	}

	public void setStartTripTime(Date startTripTime) {
		this.startTripTime = startTripTime;
	}

	public BusStop getCurrentStop() {
		return currentStop;
	}

	public void setCurrentStop(BusStop currentStop) {
		this.currentStop = currentStop;
	}

	public int getCurrentEntry() {
		return currentEntry;
	}

	public void setCurrentEntry(int currentEntry) {
		this.currentEntry = currentEntry;
	}

	public int getCurrentExit() {
		return currentExit;
	}

	public void setCurrentExit(int currentExit) {
		this.currentExit = currentExit;
	}

	@Override
	public String toString() {
		return String.format("Bus - id[%d], route#%d, direction - %s,  %s - current stop", idBus, busRoute.getNumber(),
				direction.name(), currentStop.getName());
	}

	/**
	 * Overriden method that processing route of bus. Processing depends of bus
	 * route type.
	 */
	@Override
	public void run() {
		if (busRoute == null || direction == null) {
			BUS_LOGGER.warn("Can't run bus {}, because route or direction is doesn't added", this.getIdBus());
			return;
		}
		BUS_LOGGER.info("Bus #{} started his work on route #{}", this.getIdBus(), this.getBusRoute().getNumber());
		if (!busRoute.isRoundabout()) {
			regularRoute();
		} else {
			roundaboutRoute();
		}
		BUS_LOGGER.info("Bus #{} finished trip on route #{}", this.getIdBus(), this.getBusRoute().getNumber());
		try {
			TimeUnit.MILLISECONDS.sleep((long) (TimeUnit.SECONDS.toMillis(this.endOfLineBreak) / accelerationCoeff));
		} catch (InterruptedException e) {
			BUS_LOGGER.error(e.toString());
			Thread.currentThread().interrupt();
		}
	}

	private void roundaboutRoute() {
		List<BusStop> busStopsCycle = new ArrayList<>(busRoute.getBusStops().get(Direction.PERMANENT));
		busStopsBypass(busStopsCycle);
	}

	private void regularRoute() {
		List<BusStop> busStopsFrom = new ArrayList<>(busRoute.getBusStops().get(Direction.FROM));
		List<BusStop> busStopsInto = new ArrayList<>(busRoute.getBusStops().get(Direction.INTO));
		if (direction == Direction.FROM) {
			busStopsBypass(busStopsFrom);
		} else if (direction == Direction.INTO) {
			busStopsBypass(busStopsInto);
		}
		direction.changeDirection();
	}

	private void busStopsBypass(List<BusStop> busStops) {

		for (int i = 0; i < busStops.size(); i++) {
			busStopAct(busStops.get(i));
			if (i < busStops.size() - 1) {
				double delay = MathDistance.distBetween2Dots(busStops.get(i), busStops.get(i + 1))
						/ (speed * Consts.KM_PER_HRS_TO_CI);
				try {
					TimeUnit.MILLISECONDS.sleep((long) (TimeUnit.SECONDS.toMillis((long) delay) / accelerationCoeff));
				} catch (InterruptedException e) {
					BUS_LOGGER.error(e.toString());
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	/**
	 * Bus try to take place on bus. This process depends of current bus stop's
	 * parking places.
	 * 
	 * @param busStop Current bus stop for bus
	 */
	private void busStopAct(BusStop busStop) {
		boolean stopTaken = false;
		try {
			stopTaken = busStop.addToParking(this);
			if (stopTaken) {
				BUS_LOGGER.debug("Bus - #{} came to stop {}[id={}]", this.idBus, busStop.getName(),
						busStop.getIdBusStop());
				this.currentStop = busStop;
				BusDriver.boardingUnboarding(busStop, this);
			} else {
				BUS_LOGGER.debug("Bus - #{} is waiting to take place on stop {}[id={}]", this.idBus, busStop.getName(),
						busStop.getIdBusStop());
				TimeUnit.MILLISECONDS.sleep(
						(long) (TimeUnit.MINUTES.toMillis((long) Consts.BUS_WAITING_TIME_MNTS) / accelerationCoeff));
			}
		} catch (InterruptedException e) {
			BUS_LOGGER.error(e.toString());
			Thread.currentThread().interrupt();
		} finally {
			if (stopTaken) {
				BUS_LOGGER.debug("Bus - #{} leaved stop {}", this.idBus, busStop.getIdBusStop());
				busStop.removeFromParking(this);
			}
		}
	}

	@Override
	public void addObserver(WatchDogBus wd) {
		this.wd = wd;
	}

	@Override
	public void removeObserver(WatchDogBus wd) {
		this.wd = null;
	}

	@Override
	public void notifyObserver() {
		wd.update(this);
	}

	/**
	 * Builder pattern implementation for bus.
	 *
	 * @author Iliashenko A.
	 * @version 0.1
	 * @since 25-02-2019
	 * 
	 */
	public static class BusBuilder {
		private WatchDogBus wd;

		private int capacity;
		private BusRoute busRoute;
		private Direction direction;
		private long endOfLineBreak;
		private double speed;
		private double accelerationCoeff;
		Bus bus;

		public BusBuilder withWatchDogBus(WatchDogBus wd) {
			this.wd = wd;
			return this;
		}

		public BusBuilder withCapacity(int capacity) {
			this.capacity = capacity;
			return this;
		}

		public BusBuilder withBusRoute(BusRoute busRoute) {
			this.busRoute = busRoute;
			return this;
		}

		public BusBuilder withDirection(Direction direction) {
			this.direction = direction;
			return this;
		}

		public BusBuilder withEndOfLineBreak(long endOfLineBreak) {
			this.endOfLineBreak = endOfLineBreak;
			return this;
		}

		public BusBuilder withSpeed(double speed) {
			this.speed = speed;
			return this;
		}

		public BusBuilder withAccelerationCoeff(double accelerationCoeff) {
			this.accelerationCoeff = accelerationCoeff;
			return this;
		}

		public Bus build() {
			bus = new Bus(this);
			if (wd != null) {
				bus.addObserver(wd);
			}
			if (busRoute != null) {
				busRoute.addBusOnRoute(bus);
			}
			return bus;
		}

	}

	/**
	 * Class that represents input/output operations on current stop for passengers.
	 * 
	 * @author Iliashenko A.
	 * @version 0.1
	 * @since 25-02-2019
	 * 
	 */
	static class BusDriver {

		private static final Logger BUS_DRIVER_LOGGER = LoggerFactory.getLogger(BusDriver.class);

		private BusDriver() {
			super();
		}

		public static void boardingUnboarding(BusStop busStop, Bus bus) throws InterruptedException {
			BUS_DRIVER_LOGGER.debug("Bus - #{} parked on stop {}[id={}]", bus.getIdBus(), busStop.getName(),
					busStop.getIdBusStop());

			busStop.getSemaphore().acquire();
			busStop.getBusStopLock().lock();

			BUS_DRIVER_LOGGER.debug("Bus - #{} opens doors", bus.getIdBus());

			busStop.addBus(bus);
			Passenger passenger;
			Iterator<Passenger> passengerIterator = bus.getPassengers().iterator();
			int before = bus.getPassengers().size();
			while (passengerIterator.hasNext()) {
				passenger = passengerIterator.next();
				if (passenger.getDestination().equals(busStop)) {
					passengerIterator.remove();
					BUS_DRIVER_LOGGER.debug("Bus - #{} unload with passenger = {}", bus.getIdBus(),
							passenger.getIdPassenger());
				}
			}
			int unloadCount = bus.getPassengers().size() - before;
			bus.setCurrentExit(Math.abs(unloadCount));
			for (Bus stoppedBus : busStop.getStoppedBuses()) {
				while (stoppedBus.hasPlaces()
						&& (passenger = busStop.removeFirstPassenger(stoppedBus.getBusRoute())) != null) {
					stoppedBus.getPassengers().add(passenger);
					passenger.setBus(bus);
					BUS_DRIVER_LOGGER.debug("Bus - #{} load with passenger = {}", stoppedBus.getIdBus(),
							stoppedBus.getPassengers().size());
				}
			}
			bus.setCurrentEntry(bus.getPassengers().size() - (before - bus.getCurrentExit()));
			BUS_DRIVER_LOGGER.debug("Bus - #{}; ufter load count of passengers = {}", bus.getIdBus(),
					bus.getPassengers().size());
			busStop.getBusStopLock().unlock();
			bus.notifyObserver();
			TimeUnit.MILLISECONDS.sleep((long) (TimeUnit.SECONDS.toMillis(
					(long) (Math.abs(unloadCount) * Consts.PASSENGER_UNLOAD_TIME_SEC / bus.getAccelerationCoeff()))));
			BUS_DRIVER_LOGGER.debug("Bus - #{}; ufter unload count of passengers = {}", bus.getIdBus(),
					bus.getPassengers().size());
			TimeUnit.MILLISECONDS.sleep((long) (TimeUnit.SECONDS
					.toMillis((long) (Math.abs(bus.getPassengers().size() - (before - unloadCount))
							* Consts.PASSENGER_UNLOAD_TIME_SEC / bus.getAccelerationCoeff()))));
			busStop.getBusStopLock().lock();
			busStop.getStoppedBuses().remove(bus);
			busStop.getBusStopLock().unlock();
			BUS_DRIVER_LOGGER.debug("Bus - #{} leaved stop {}[id={}]", bus.getIdBus(), busStop.getName(),
					busStop.getIdBusStop());
			busStop.getSemaphore().release();
			if (!bus.getBusRoute().isStopLastOnRoute(bus.getDirection(), busStop)) {
				bus.addLoadPerc((double) bus.getPassengers().size() / bus.getCapacity());
			}
		}
	}
}
