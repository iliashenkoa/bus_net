package com.iliashenko.account.service;

import java.sql.Timestamp;
import java.util.List;

import com.iliashenko.account.model.BusLog;

public interface BusLogService {
	List<BusLog> findByBusId(Long id);

	List<BusLog> findByRouteId(Long id);

	List<BusLog> findByStartWork(Timestamp tim);

	List<BusLog> findByEndWork(Timestamp time);

	List<BusLog> findAllBusLogRecords();

	List<BusLog> findAllBusLogByRoute(Integer routeNum);
	
	List<BusLog> findAllBusLogByStop(Integer busStopId);
	
	Long findTranspPassPerHourByRoute(List<BusLog> buses);

	Long findTranspPassPerHourByStop(List<BusLog> buses);

	Long findBusLoadPerHourByStop(List<BusLog> buses);

	
}
