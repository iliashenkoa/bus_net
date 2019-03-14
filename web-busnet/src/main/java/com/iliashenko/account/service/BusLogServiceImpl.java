package com.iliashenko.account.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iliashenko.account.model.BusLog;
import com.iliashenko.account.repository.BusLogRepository;

@Service
public class BusLogServiceImpl implements BusLogService {

	@Autowired
	private BusLogRepository busesRepository;

	@Override
	public List<BusLog> findByBusId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BusLog> findByRouteId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BusLog> findByStartWork(Timestamp tim) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BusLog> findByEndWork(Timestamp time) {
		return null;
	}

	@Override
	public List<BusLog> findAllBusLogRecords() {
		return busesRepository.findAll();

	}

	@Override
	public List<BusLog> findAllBusLogByRoute(Integer routeNum) {
		return busesRepository.findBusLogByRouteNum(routeNum);
	}

	@Override
	public List<BusLog> findAllBusLogByStop(Integer busStopId) {
		return busesRepository.findBusLogByStopId(busStopId);
	}

	@Override
	public Long findTranspPassPerHourByRoute(List<BusLog> buses) {
		Map<Integer, List<BusLog>> logsPerHour = buses.stream().collect(Collectors.groupingBy(BusLog::getHour));
		List<Integer> res = new ArrayList<>();
		logsPerHour.forEach((key, list) -> {
			res.add(list.stream().mapToInt(log -> log.getPassengersCountExit()).sum());
		});
		return (long) Math.floor(res.stream().mapToDouble(d -> d).average().orElse(0.0));
	}

	@Override
	public Long findTranspPassPerHourByStop(List<BusLog> buses) {
		Map<Integer, List<BusLog>> logsPerHour = buses.stream().collect(Collectors.groupingBy(BusLog::getHour));
		List<Integer> res = new ArrayList<>();
		logsPerHour.forEach((key, list) -> {
			res.add(list.stream().mapToInt(log -> log.getPassengersCountExit()).sum());
			res.add(list.stream().mapToInt(log -> log.getPassengersCountEntry()).sum());
		});
		long sum = res.stream().mapToInt(Integer::intValue).sum();
		return (long) Math.floor(2 * sum / (double) res.size());
	}

	@Override
	public Long findBusLoadPerHourByStop(List<BusLog> buses) {
		return null;
	}

}
