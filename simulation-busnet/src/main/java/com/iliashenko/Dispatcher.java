package com.iliashenko;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iliashenko.observer.WatchDogBus;
import com.iliashenko.observer.WatchDogTime;
import com.iliashenko.simulation.statistics.BusLog;
import com.iliashenko.utils.DataPusher;
import com.iliashenko.utils.Time;

/**
 * Class that make reacts on all notifies from buses and. It controll creation
 * buses threads for every route.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 26-02-2019
 *
 */
public class Dispatcher implements WatchDogBus, WatchDogTime {

	private static final Logger DISPATCHER_LOGGER = LoggerFactory.getLogger(Dispatcher.class);

	private double accelerationCoeff;
	private int busesCapacity;
	private double busesSpeed;
	private int busesCount;
	private Queue<Bus> buses;
	private Collection<BusRoute> routes;
	private Map<Long, List<BusSheduleRecord>> shedule;
	private ExecutorService executor;

	private Time currentTime;
	private final Time endTime;

	private Map<Integer, Queue<Bus>> busesOnRoute;

	private Map<Integer, Long> routeTransportedPass;

	private Map<Integer, List<Integer>> busesLoad;

	DataPusher pusher;

	public Dispatcher(List<BusRoute> routes, double accelerationCoeff, Map<Long, List<BusSheduleRecord>> recordsGrouped,
			Time endTime, int busesCapacity, double busesSpeed, int busesCount, DataPusher pusher) {
		this.routes = routes;
		this.accelerationCoeff = accelerationCoeff;
		this.shedule = recordsGrouped;
		this.endTime = endTime;
		this.busesCapacity = busesCapacity;
		this.busesSpeed = busesSpeed;
		this.busesCount = busesCount;
		this.pusher = pusher;

		this.routeTransportedPass = new HashMap<>();

		for (BusRoute br : routes) {
			routeTransportedPass.put(br.getNumber(), (long) 0);
		}

		this.busesOnRoute = new HashMap<>();
		this.busesLoad = new HashMap<>();

		for (BusRoute route : routes) {
			buses = new LinkedList<>();
			for (int i = 0; i < busesCount; i++) {
				buses.add(new Bus.BusBuilder().withCapacity(busesCapacity).withWatchDogBus(this).withBusRoute(route)
						.withEndOfLineBreak(100).withSpeed(busesSpeed).withAccelerationCoeff(this.accelerationCoeff)
						.build());
			}
			busesOnRoute.put(route.getNumber(), buses);
		}
		executor = Executors.newCachedThreadPool();
	}

	public int getBusesCount() {
		return busesCount;
	}

	public int getRoutesCount() {
		return routes.size();
	}

	public int getBusesCapacity() {
		return busesCapacity;
	}

	public double getBusesSpeed() {
		return busesSpeed;
	}

	public Map<Integer, Long> getRouteTransportedPass() {
		return routeTransportedPass;
	}

	public void addScheduleRecord(BusSheduleRecord sheduleRecord) {
		shedule.computeIfAbsent(sheduleRecord.getStartTime().getTimeInMs(), k -> new ArrayList<>()).add(sheduleRecord);
	}

	private void addCountTransportedPass(Bus bus) {
		Long count = this.routeTransportedPass.get(bus.getBusRoute().getNumber());
		if (bus.getCurrentExit() > 0) {
			count += bus.getCurrentExit();
			this.routeTransportedPass.put(bus.getBusRoute().getNumber(), count);
		}
	}

	private void addBusesLoad(Bus bus) {
		if (this.busesLoad.get(bus.getBusRoute().getNumber()) == null) {
			List<Integer> loadByInterval = new ArrayList<>();
			loadByInterval.add(bus.getPassengers().size());
			this.busesLoad.put(bus.getBusRoute().getNumber(), loadByInterval);
		} else {
			this.busesLoad.get(bus.getBusRoute().getNumber()).add(bus.getPassengers().size());
		}

	}

	public long getFullCountTransportedPass() {
		return routeTransportedPass.values().stream().mapToLong(i -> i).sum();
	}

	public void setRouteTransportedPass(Map<Integer, Long> routeTransportedPass) {
		this.routeTransportedPass = routeTransportedPass;
	}

	public Map<Long, List<BusSheduleRecord>> getShedule() {
		return shedule;
	}

	public Map<Integer, List<Integer>> getBusesLoad() {
		return busesLoad;
	}

	public void setBusesLoad(Map<Integer, List<Integer>> busesLoad) {
		this.busesLoad = busesLoad;
	}

	@Override
	public void update(Time time) {
		this.currentTime = time;
		Bus bus;
		if (shedule.containsKey(this.currentTime.getTimeInMs())) {
			for (BusSheduleRecord rec : shedule.get(this.currentTime.getTimeInMs())) {
				try {
					bus = busesOnRoute.get(rec.getBusRouteNumber()).remove();
					bus.setDirection(rec.getDirectionType());
					bus.setStartTripTime(time.getDate());
					executor.execute(bus);
					DISPATCHER_LOGGER.debug("Bus #{} has left the start stop", bus.getIdBus());
				} catch (NoSuchElementException e) {
					DISPATCHER_LOGGER.warn("route/buses?");
				}
			}
		}
	}

	@Override
	public void update(Bus bus) {
		if (this.currentTime.getDate().getTime() <= this.endTime.getDate().getTime() - 1) {
			if (!bus.getBusRoute().isStopLastOnRoute(bus.getDirection(), bus.getCurrentStop())) {
				this.addBusesLoad(bus);
			} else {
				busesOnRoute.get(bus.getBusRoute().getNumber()).add(bus);
			}

			BusLog busLog = new BusLog(new java.sql.Timestamp(this.currentTime.getDate().getTime()),
					bus.getCurrentStop().getIdBusStop(), bus.getBusRoute().getNumber(), bus.getIdBus(),
					bus.getCurrentEntry(), bus.getCurrentExit(), bus.getPassengers().size());
			this.addCountTransportedPass(bus);
			pusher.pushBusLog(busLog);
		}
	}

	public double averageLoadPercantage() {
		List<Double> res = new ArrayList<>();
		for (Map.Entry<Integer, List<Integer>> item : this.busesLoad.entrySet()) {
			res.add(item.getValue().stream().mapToDouble(d -> d).average().orElse(0.0) / this.busesCapacity);
		}
		return res.stream().mapToDouble(d -> d).average().orElse(0.0);
	}

}
