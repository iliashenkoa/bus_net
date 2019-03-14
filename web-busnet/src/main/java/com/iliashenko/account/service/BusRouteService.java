package com.iliashenko.account.service;

import java.util.List;

import com.iliashenko.account.model.BusRoute;

public interface BusRouteService {

	BusRoute findByBusRouteNumber(Integer number);
	
	List<BusRoute> findAllBusRouteRecords();
	
}
