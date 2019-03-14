package com.iliashenko.account.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.iliashenko.account.model.BusStop;

public interface BusStopService {
	@Transactional
	BusStop findByBusStopId(Long id);

	@Transactional
	List<BusStop> findAllBusStopRecords();

	@Transactional
	List<Long> findAllBusStopsIds();

}
