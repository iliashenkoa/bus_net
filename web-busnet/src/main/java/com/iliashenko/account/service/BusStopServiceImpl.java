package com.iliashenko.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iliashenko.account.model.BusStop;
import com.iliashenko.account.repository.BusStopRepository;

@Service
public class BusStopServiceImpl implements BusStopService {

	@Autowired
	BusStopRepository busStopRepository;

	@Override
	public BusStop findByBusStopId(Long id) {
		return busStopRepository.findOne(id);
	}

	@Override
	public List<BusStop> findAllBusStopRecords() {
		return busStopRepository.findAll();
	}

	@Override
	public List<Long> findAllBusStopsIds() {
		return busStopRepository.findAllBusStopsIds();
	}

}
