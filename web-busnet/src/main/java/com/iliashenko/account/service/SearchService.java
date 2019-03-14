package com.iliashenko.account.service;

import java.util.List;

import com.iliashenko.account.model.BusRoute;
import com.iliashenko.account.model.BusStop;

public interface SearchService {
	BusRoute findCheapestWay(int idBusFrom, int idBusTo);

	List<BusStop> findFastestWay(int idBusFrom, int idBusTo);

}
