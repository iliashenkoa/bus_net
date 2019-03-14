package com.iliashenko;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlType(name = "bus-route")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * Class that represent's bus route entity. Every route contains linked list of
 * bus stops. It can be linear or roundabout type. Every delta time (time
 * interval) bus are running from start stop
 * 
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 26-02-2019
 *
 */
public class BusRoute {

	private static final Logger ROUTE_LOGGER = LoggerFactory.getLogger(BusRoute.class);

	@XmlElement(name = "bus-route-number")
	private int number;
	@XmlAttribute
	private double price;
	@XmlAttribute
	private boolean isRoundabout;
	@XmlAttribute
	private int timeInterval;

	@XmlTransient
	List<Bus> busesOnRoute;
	@XmlElementWrapper
	@XmlElement(name = "bus-stops")
	Map<Direction, ListWrapper> busStops;

	public BusRoute() {
	}

	private BusRoute(BusRouteBuilder builder) {
		super();
		this.number = builder.number;
		this.price = builder.price;
		this.isRoundabout = builder.isRoundabout;
		this.timeInterval = builder.timeInterval;
		this.busesOnRoute = builder.busesOnRoute;
		this.busStops = builder.busStops;
		this.busesOnRoute = builder.busesOnRoute;
		this.busStops = builder.busStops;
		ROUTE_LOGGER.debug("BusRoute - {} was created", this.number);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isRoundabout() {
		return isRoundabout;
	}

	public void setRoundabout(boolean isRoundabout) {
		this.isRoundabout = isRoundabout;
	}

	public List<Bus> getBusesOnRoute() {
		return busesOnRoute;
	}

	public void setBusesOnRoute(List<Bus> busesOnRoute) {
		this.busesOnRoute = busesOnRoute;
	}

	public void addBusOnRoute(Bus bus) {
		if (busesOnRoute == null) {
			busesOnRoute = new ArrayList<>();
		}
		this.busesOnRoute.add(bus);
	}

	public Map<Direction, LinkedList<BusStop>> getBusStops() {
		Map<Direction, LinkedList<BusStop>> map = new EnumMap<>(Direction.class);
		for (Map.Entry<Direction, ListWrapper> entry : busStops.entrySet()) {
			Direction dir = entry.getKey();
			ListWrapper value = entry.getValue();
			List<BusStop> stops = new LinkedList<>();
			stops.addAll(value.getList());
			map.put(dir, (LinkedList<BusStop>) stops);
		}
		return map;
	}

	public Set<BusStop> allUniqueBusStops() {
		Set<BusStop> res = new HashSet<>();
		Map<Direction, LinkedList<BusStop>> map = this.getBusStops();
		for (Map.Entry<Direction, LinkedList<BusStop>> entry : map.entrySet()) {
			res.addAll(entry.getValue());
		}
		return res;
	}

	public void setBusStops(Map<Direction, LinkedList<BusStop>> busStops) {
		for (Map.Entry<Direction, LinkedList<BusStop>> entry : busStops.entrySet()) {
			Direction dir = entry.getKey();
			LinkedList<BusStop> value = entry.getValue();
			ListWrapper lw = new ListWrapper();
			lw.setList(value);
			this.busStops.put(dir, lw);
		}
	}

	public int getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}

	public void addStop(Direction direction, BusStop busStop) {
		this.busStops.get(direction).getList().add(busStop);
	}

	public void addStop(Direction direction, BusStop busStop, int position) {
		this.busStops.get(direction).getList().add(position, busStop);
	}

	public boolean isStopLastOnRoute(Direction dir, BusStop stop) {
		return busStops.get(dir).getList().indexOf(stop) == busStops.get(dir).getList().size() - 1;
	}

	@Override
	public String toString() {
		return String.format("BusRoute - #%d, %f$, %d min interval, %s - buses, %s - busStops", number, price,
				timeInterval, busesOnRoute, busStops);
	}

	/**
	 * Builder pattern implementation for bus route.
	 *
	 * @author Iliashenko A.
	 * @version 0.1
	 * @since 26-02-2019
	 * 
	 */
	public static class BusRouteBuilder {
		private int number;
		private double price;
		private boolean isRoundabout;
		private int timeInterval;
		List<Bus> busesOnRoute;
		BusRoute busRoute;

		Map<Direction, ListWrapper> busStops;

		public BusRouteBuilder withNumber(int number) {
			this.number = number;
			return this;
		}

		public BusRouteBuilder withPrice(double price) {
			this.price = price;
			return this;
		}

		public BusRouteBuilder isRoundabout(boolean isRoundabout) {
			this.isRoundabout = isRoundabout;
			return this;
		}

		public BusRouteBuilder withTimeInterval(int timeInterval) {
			this.timeInterval = timeInterval;
			return this;
		}

		public BusRouteBuilder withBusesOnRoute(List<Bus> busesOnRoute) {
			this.busesOnRoute = busesOnRoute;
			return this;
		}

		public BusRouteBuilder withBusStops(Map<Direction, LinkedList<BusStop>> busStops) {
			this.busStops = new EnumMap<>(Direction.class);
			for (Map.Entry<Direction, LinkedList<BusStop>> entry : busStops.entrySet()) {
				Direction dir = entry.getKey();
				LinkedList<BusStop> value = entry.getValue();
				ListWrapper lw = new ListWrapper();
				lw.setList(value);
				this.busStops.put(dir, lw);
			}
			return this;
		}

		/**
		 * Notify every bus stop about attachment to this route.
		 * 
		 * @return Bus route that was created by builder
		 */
		public BusRoute build() {
			busRoute = new BusRoute(this);
			if (busesOnRoute != null) {
				for (Bus b : busesOnRoute) {
					b.setBusRoute(busRoute);
				}
			}

			if (busStops != null) {
				if (isRoundabout) {
					for (BusStop bs : busStops.get(Direction.PERMANENT).getList()) {
						bs.addToRoute(busRoute);
					}
				} else {
					for (BusStop bs : busStops.get(Direction.FROM).getList()) {
						bs.addToRoute(busRoute);
					}
					for (BusStop bs : busStops.get(Direction.INTO).getList()) {
						bs.addToRoute(busRoute);
					}
				}
			}
			return busRoute;
		}
	}
}

@XmlAccessorType(XmlAccessType.FIELD)
/**
 * Class-wrapper for bus stops. Need for un/marshalling from/to xml.
 *
 * @author Iliashenko A.
 * @version 0.1
 * @since 26-02-2019
 * 
 */
class ListWrapper {
	@XmlElement(name = "bus-stop")
	private LinkedList<BusStop> list;

	public void setList(List<BusStop> list) {
		this.list = new LinkedList<>();
		this.list.addAll(list);
	}

	public List<BusStop> getList() {
		return list;
	}

	@Override
	public String toString() {
		return list.toString();
	}

}
