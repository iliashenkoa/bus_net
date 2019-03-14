package com.iliashenko.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iliashenko.account.model.BusLog;

public interface BusLogRepository extends JpaRepository<BusLog, Long> {

	@Query("SELECT log FROM BusLog log WHERE log.routeNum=:num")
	List<BusLog> findBusLogByRouteNum(@Param("num") Integer num);
	
	@Query("SELECT log FROM BusLog log WHERE log.busId=:id")
	List<BusLog> findBusLogByStopId(@Param("id") Integer id);
}
