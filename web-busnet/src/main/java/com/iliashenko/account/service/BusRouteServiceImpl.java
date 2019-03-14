package com.iliashenko.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iliashenko.account.model.BusRoute;
import com.iliashenko.account.repository.BusRouteRepository;

@Service
public class BusRouteServiceImpl implements BusRouteService {

	@Autowired
	private BusRouteRepository busesRoutesRepository;

	@Override
	public BusRoute findByBusRouteNumber(Integer number) {
		return busesRoutesRepository.findOne(number);
	}

	@Override
	public List<BusRoute> findAllBusRouteRecords() {
		return busesRoutesRepository.findAll();
	}

}
