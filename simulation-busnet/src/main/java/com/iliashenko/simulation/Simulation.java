package com.iliashenko.simulation;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iliashenko.BusRoute;
import com.iliashenko.BusShedule;
import com.iliashenko.BusSheduleRecord;
import com.iliashenko.BusStop;
import com.iliashenko.Direction;
import com.iliashenko.Dispatcher;
import com.iliashenko.Network;
import com.iliashenko.db.BusRouteStop;
import com.iliashenko.db.MyDataSourceFactory;
import com.iliashenko.db.dao.BusRouteDAOImpl;
import com.iliashenko.db.dao.BusRouteStopDAOImpl;
import com.iliashenko.db.dao.BusStopDAOImpl;
import com.iliashenko.generation.PassengerGenerator;
import com.iliashenko.generation.SheduleGenerator;
import com.iliashenko.simulation.statistics.Statistics;
import com.iliashenko.utils.DataPusher;
import com.iliashenko.utils.Time;
import com.iliashenko.utils.Timer;

/**
 * Contains all data parts of simulation in one object. Implements start, stop
 * commands. Method for push statistics.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 11-03-2019
 *
 */
public class Simulation {
	private static final Logger SIMULATION_LOGGER = LoggerFactory.getLogger(Simulation.class);

	private Date startTime;
	private Date endTime;
	private Dispatcher dispatcher;
	private PassengerGenerator passengerGenerator;
	private Timer timer;
	private boolean isRunning;
	private Statistics stat;
	private DataSource dataSource;
	private DataPusher pusher;

	public Simulation(Configuration config, Network net, BusShedule shedule) {
		this.dataSource = MyDataSourceFactory.getMySQLDataSource();
		shedule = SheduleGenerator.sheduler(shedule, net.getNetwork());
		Map<Long, List<BusSheduleRecord>> recordsGrouped = shedule.getSheduleRecords().stream()
				.collect(Collectors.groupingBy(rec -> rec.getStartTime().getTimeInMs()));
		this.pusher = new DataPusher(this.dataSource,
				"/home/alex/work/eclipse-workspace/parent-module/simulation-busnet/result/statistics.csv",
				"/home/alex/work/eclipse-workspace/parent-module/simulation-busnet/result/busLog.csv");
		this.dispatcher = new Dispatcher((List<BusRoute>) net.getNetwork(), config.getAccelerationCoeff(),
				recordsGrouped, config.getEndTime(), config.getBusesCapacity(), config.getBusesSpeed(),
				config.getBusesCount(), pusher);
		this.timer = new Timer(config.getStartTime(), config.getEndTime(), config.getAccelerationCoeff());
		this.passengerGenerator = new PassengerGenerator(net);
		this.timer.addWatchDog(dispatcher);
		this.timer.addWatchDog(passengerGenerator);
		for (BusRoute route : net.getNetwork()) {
			if (route.isRoundabout()) {
				for (BusStop stop : route.getBusStops().get(Direction.PERMANENT)) {
					timer.addWatchDog(stop);
					stop.addToRoute(route);
					stop.initSemaphore();
				}
			} else {
				for (BusStop stop : route.getBusStops().get(Direction.FROM)) {
					timer.addWatchDog(stop);
					stop.addToRoute(route);
					stop.initSemaphore();
				}
				for (BusStop stop : route.getBusStops().get(Direction.INTO)) {
					timer.addWatchDog(stop);
					stop.addToRoute(route);
					stop.initSemaphore();
				}
			}
		}
		collectAndPushNetwork(net);
	}

	public void start() {
		stat = new Statistics();
		startTime = new Date(System.currentTimeMillis());
		stat.setStartTime(new java.sql.Timestamp(startTime.getTime()));
		if (!isRunning) {
			Thread thread = new Thread(timer);
			thread.start();
			isRunning = true;
			try {
				thread.join();
			} catch (InterruptedException e) {
				SIMULATION_LOGGER.warn("{}", e);
				Thread.currentThread().interrupt();
			}
		}
	}

	public void stop() {
		if (isRunning) {
			isRunning = false;
			endTime = new Date(System.currentTimeMillis());
			stat.setTimeSimulation((int) timer.stop() / Time.MILLIS_IN_MINUTE);
			stat.setEndTime(new java.sql.Timestamp(endTime.getTime()));
			SIMULATION_LOGGER.info("Count of transported passengers = {}", dispatcher.getFullCountTransportedPass());
			collectStatistics();
			timer.reset();
		}
	}

	/**
	 * Push start network info to database.
	 * @param net Bus routes and stops info.
	 */
	public void collectAndPushNetwork(Network net) {
		BusRouteDAOImpl brDAO = new BusRouteDAOImpl(dataSource);
		BusStopDAOImpl bsDAO = new BusStopDAOImpl(dataSource);
		BusRouteStopDAOImpl m2mBrBsDAO = new BusRouteStopDAOImpl(dataSource);

		for (BusRoute route : net.getNetwork()) {
			brDAO.create(route);
			if (route.isRoundabout()) {
				for (BusStop stop : route.getBusStops().get(Direction.PERMANENT)) {
					bsDAO.create(stop);
					m2mBrBsDAO.create(new BusRouteStop(stop.getIdBusStop(), route.getNumber()));
				}
			} else {
				for (BusStop stop : route.allUniqueBusStops()) {
					bsDAO.create(stop);
					m2mBrBsDAO.create(new BusRouteStop(stop.getIdBusStop(), route.getNumber()));
				}
			}
		}

	}

	public void collectStatistics() {
		stat.setBusCount(dispatcher.getBusesCount() * dispatcher.getRoutesCount());
		stat.setRoutesCount(dispatcher.getRoutesCount());
		stat.setPassengersCount(dispatcher.getFullCountTransportedPass());

		stat.setLoadPercentage(100 * dispatcher.averageLoadPercantage());
		pusher.pushStatistics(stat);
	}

}