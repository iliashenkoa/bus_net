package com.iliashenko.account.model.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.iliashenko.account.model.BusStop;

public class DijkstraAlgorithm {
	private final List<BusStop> nodes;
	private final List<Edge> edges;
	private Set<BusStop> settledNodes;
	private Set<BusStop> unSettledNodes;
	private Map<BusStop, BusStop> predecessors;
	private Map<BusStop, Integer> distance;

	public DijkstraAlgorithm(Graph graph) {
		this.nodes = new ArrayList<>(graph.getVertexes());
		this.edges = new ArrayList<>(graph.getEdges());
	}

	public void execute(BusStop source) {
		settledNodes = new HashSet<>();
		unSettledNodes = new HashSet<>();
		distance = new HashMap<>();
		predecessors = new HashMap<>();
		distance.put(source, 0);
		unSettledNodes.add(source);
		while (!unSettledNodes.isEmpty()) {
			BusStop node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	private void findMinimalDistances(BusStop node) {
		List<BusStop> adjacentNodes = getNeighbors(node);
		for (BusStop target : adjacentNodes) {
			if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
				distance.put(target, getShortestDistance(node) + getDistance(node, target));
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}

	}

	private int getDistance(BusStop node, BusStop target) {
		for (Edge edge : edges) {
			if (edge.getSource().equals(node) && edge.getDestination().equals(target)) {
				return edge.getWeight();
			}
		}
		throw new RuntimeException("Should not happen");
	}

	private List<BusStop> getNeighbors(BusStop node) {
		List<BusStop> neighbors = new ArrayList<>();
		for (Edge edge : edges) {
			if (edge.getSource().equals(node) && !isSettled(edge.getDestination())) {
				neighbors.add(edge.getDestination());
			}
		}
		return neighbors;
	}

	private BusStop getMinimum(Set<BusStop> busStops) {
		BusStop minimum = null;
		for (BusStop busStop : busStops) {
			if (minimum == null) {
				minimum = busStop;
			} else {
				if (getShortestDistance(busStop) < getShortestDistance(minimum)) {
					minimum = busStop;
				}
			}
		}
		return minimum;
	}

	private boolean isSettled(BusStop busStop) {
		return settledNodes.contains(busStop);
	}

	private int getShortestDistance(BusStop destination) {
		Integer d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	/*
	 * This method returns the path from the source to the selected target and NULL
	 * if no path exists
	 */
	public LinkedList<BusStop> getPath(BusStop target) {
		LinkedList<BusStop> path = new LinkedList<>();
		BusStop step = target;
		if (predecessors.get(step) == null) {
			return path;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		Collections.reverse(path);
		return path;
	}

}