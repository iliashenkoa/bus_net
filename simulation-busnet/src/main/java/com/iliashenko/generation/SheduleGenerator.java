package com.iliashenko.generation;

import java.util.Collection;
import java.util.NoSuchElementException;

import com.iliashenko.BusRoute;
import com.iliashenko.BusShedule;
import com.iliashenko.BusSheduleRecord;
import com.iliashenko.utils.Time;

/**
 * Generate shedule for every bus route using start/end time and time interval.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 10-03-2019
 *
 */
public class SheduleGenerator {

	private SheduleGenerator() {
		super();
	}

	public static BusShedule sheduler(BusShedule shedule, Collection<BusRoute> routes) {
		BusShedule sheduleRecords = new BusShedule();
		for (BusSheduleRecord record : shedule.getSheduleRecords()) {
			Time date = record.getStartTime();
			while (date.getTimeInMs() <= record.getEndTime().getTimeInMs()) {
				BusSheduleRecord bsr = new BusSheduleRecord(record.getBusRouteNumber(), record.getDirectionType(),
						new Time(date));
				sheduleRecords.addSheduleRecord(bsr);
				date.addMinutes(getRoute(record.getBusRouteNumber(), routes).getTimeInterval());
			}
		}
		// sheduleRecords.sortByStartTime();
		return sheduleRecords;
	}

	private static BusRoute getRoute(int number, Collection<BusRoute> routes) {
		for (BusRoute route : routes) {
			if (route.getNumber() == number) {
				return route;
			}
		}
		throw new NoSuchElementException();
	}
}
