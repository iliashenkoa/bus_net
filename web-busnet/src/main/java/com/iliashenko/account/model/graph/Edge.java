package com.iliashenko.account.model.graph;

import com.iliashenko.account.model.BusStop;

public class Edge {
	private final String id;
	private final BusStop source;
	private final BusStop destination;
	private final int weight;

	public Edge(String id, BusStop source, BusStop destination, int weight) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	public String getId() {
		return id;
	}

	public BusStop getDestination() {
		return destination;
	}

	public BusStop getSource() {
		return source;
	}

	public int getWeight() {
		return weight;
	}

}