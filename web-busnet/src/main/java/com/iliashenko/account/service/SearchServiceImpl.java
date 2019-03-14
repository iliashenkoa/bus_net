package com.iliashenko.account.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iliashenko.account.model.BusRoute;
import com.iliashenko.account.model.BusStop;
import com.iliashenko.account.model.graph.DijkstraAlgorithm;
import com.iliashenko.account.model.graph.Edge;
import com.iliashenko.account.model.graph.Graph;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private BusRouteService routeService;

	@Autowired
	private BusStopService stopService;

	private List<BusStop> nodes;
	private List<Edge> edges;

	@Override
	public BusRoute findCheapestWay(int idBusFrom, int idBusTo) {
		BusStop start = stopService.findByBusStopId((long) idBusFrom);
		BusStop end = stopService.findByBusStopId((long) idBusTo);

		Comparator<BusRoute> compareById = (BusRoute r1, BusRoute r2) -> r2.getPrice().compareTo(r1.getPrice());
		List<BusRoute> sameRoute = start.getRoutes();
		sameRoute.retainAll(end.getRoutes());

		if (!sameRoute.isEmpty()) {
			Collections.sort(sameRoute, compareById.reversed());
			return sameRoute.get(0);
		} else {
			// TODO: same as fast way, but weight = price
		}
		return null;
	}

	@Override
	public List<BusStop> findFastestWay(int idBusFrom, int idBusTo) {
		BusStop start = stopService.findByBusStopId((long) idBusFrom);
		BusStop end = stopService.findByBusStopId((long) idBusTo);
		List<BusRoute> busRoutes = routeService.findAllBusRouteRecords();
		nodes = stopService.findAllBusStopRecords();

		List<BusStop> stops;

		edges = new ArrayList<>();

		for (BusRoute route : busRoutes) {
			stops = route.getStops();
			for (int c = 0; c < stops.size(); c++) {
				if (c < stops.size() - 1) {
					BusStop from = stops.get(c);
					BusStop to = stops.get(c + 1);

					BusStop node = nodes.stream().filter(a -> Objects.equals(a.getId(), from.getId())).findFirst()
							.get();
					BusStop nextNode = nodes.stream().filter(a -> Objects.equals(a.getId(), to.getId())).findFirst()
							.get();
					addLane("Edge_" + node.getId() + nextNode.getId(), nodes.indexOf(node), nodes.indexOf(nextNode),
							(int) Math.round(com.iliashenko.utils.MathDistance.distByCoord(node.getxCoord(),
									nextNode.getxCoord(), node.getyCoord(), nextNode.getyCoord())));
				}
			}
		}

		Graph graph = new Graph(nodes, edges);
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

		dijkstra.execute(nodes.get(nodes.indexOf(start)));
		List<BusStop> path = dijkstra.getPath(nodes.get(nodes.indexOf(end)));
		return path;
	}

	private void addLane(String laneId, int sourceLocNo, int destLocNo, int duration) {
		Edge lane = new Edge(laneId, nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
		edges.add(lane);
	}

}
