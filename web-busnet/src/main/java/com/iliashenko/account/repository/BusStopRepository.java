package com.iliashenko.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iliashenko.account.model.BusStop;

public interface BusStopRepository extends JpaRepository<BusStop, Long> {

	@Query("SELECT stop.id FROM BusStop stop")
	List<Long> findAllBusStopsIds();

}
