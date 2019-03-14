package com.iliashenko.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.iliashenko.BusRoute;
import com.iliashenko.BusStop;
import com.iliashenko.Direction;
import com.iliashenko.Network;
import com.iliashenko.Passenger;
import com.iliashenko.observer.WatchDogTime;
import com.iliashenko.utils.Time;

/**
 * Class that generate passengers at time moment.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 26-02-2019
 *
 */
public class PassengerGenerator implements WatchDogTime {
	private List<BusRoute> allBusRoutes;
	private static final int MAX_PASSENGERS = 10;
	private static final int MIN_PASSENGERS = 2;

	private static final int MIN_WAITING_MINS = 5;
	private static final int MAX_WAITING_MINS = 10;

	public PassengerGenerator(Network net) {
		this.allBusRoutes = net.getNetwork();
	}

	public Passenger generatePassenger(Time maxWaitTime) {
		Random rand = new Random();
		int size = allBusRoutes.size();
		BusRoute route = null;
		if (size > 2) {
			route = allBusRoutes.get(rand.nextInt(size));
		} else if (size == 2) {
			route = allBusRoutes.get(rand.nextDouble() >= 0.5 ? 1 : 0);
		} else if (size == 1) {
			route = allBusRoutes.get(size - 1);
		}
		List<BusStop> busStops;
		if (route.isRoundabout()) {
			busStops = route.getBusStops().get(Direction.PERMANENT);
		} else {
			busStops = route.getBusStops().get(Direction.values()[rand.nextInt(Direction.getSize() - 1)]);
		}
		BusStop start = busStops.get(rand.nextInt(busStops.size() - 1));
		List<BusStop> temp = busStops.subList(busStops.indexOf(start) + 1, busStops.size());
		BusStop destination = temp.get(rand.nextInt(temp.size()));
		return new Passenger(start, destination, maxWaitTime);
	}

	public void generatePassengers(Time currentTime) {
		Random rand = new Random();
		List<Passenger> passengers = new ArrayList<>();
		int count = (rand.nextInt(MAX_PASSENGERS - MIN_PASSENGERS) + MIN_PASSENGERS)
				* SystemDataGeneration.loadIndex(currentTime) * allBusRoutes.size();
		for (int c = 0; c < count; c++) {
			Time temp = new Time(currentTime);
			temp.addMinutes(rand.nextInt(MAX_WAITING_MINS) + MIN_WAITING_MINS);
			Passenger p = generatePassenger(temp);
			p.getStart().addPassenger(p);
			passengers.add(p);
		}
	}

	@Override
	public void update(Time currentTime) {
		generatePassengers(currentTime);
	}
}
