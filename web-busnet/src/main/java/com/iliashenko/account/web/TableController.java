package com.iliashenko.account.web;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.iliashenko.account.model.BusLog;
import com.iliashenko.account.model.BusRoute;
import com.iliashenko.account.model.BusStop;
import com.iliashenko.account.model.Statistics;
import com.iliashenko.account.service.BusLogService;
import com.iliashenko.account.service.BusRouteService;
import com.iliashenko.account.service.BusStopService;
import com.iliashenko.account.service.StatisticsService;

@Controller
public class TableController {
	@Autowired
	private StatisticsService statService;

	@Autowired
	private BusLogService busesService;

	@Autowired
	private BusRouteService routeService;

	@Autowired
	private BusStopService stopService;

	private static final Logger LOGGER = LoggerFactory.getLogger(TableController.class);

	@GetMapping(value = "/statistics")
	public String statisticsTable(Model model) {
		List<Statistics> stats = statService.findAllStatisticsRecords();
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		try {
			model.addAttribute("statList", mapper.writeValueAsString(stats));
		} catch (IOException e) {
			LOGGER.warn("{}", e);
		}
		return "statistics";

	}

	@GetMapping(value = { "/", "/welcome" })
	public String routesTable(Model model) {
		List<BusRoute> routes = routeService.findAllBusRouteRecords();
		model.addAttribute("routes", routes);
		return "welcome";
	}

	@GetMapping(value = "/bus_route")
	public String busRoute(@RequestParam(value = "num") int num, Model model) {
		BusRoute route = routeService.findByBusRouteNumber(num);
		List<BusLog> buses = busesService.findAllBusLogByRoute(num);
		Long count = busesService.findTranspPassPerHourByRoute(buses);
		model.addAttribute("route", route);
		model.addAttribute("transpPas", count);
		return "bus_route";
	}

	@GetMapping(value = "/bus_stop")
	public String busStop(@RequestParam(value = "id") int id, Model model) {
		BusStop stop = stopService.findByBusStopId((long) id);
		List<BusLog> buses = busesService.findAllBusLogByStop(id);
		Long count = busesService.findTranspPassPerHourByStop(buses);
		model.addAttribute("stop", stop);
		model.addAttribute("transpPas", count);
		return "bus_stop";
	}

	@GetMapping(value = "/bus_log")
	public String busLog(Model model) {
		List<BusLog> buses = busesService.findAllBusLogRecords();
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		try {
			model.addAttribute("busLogList", mapper.writeValueAsString(buses));
		} catch (IOException e) {
			LOGGER.warn("{}", e);
		}
		return "bus_log";
	}
}
