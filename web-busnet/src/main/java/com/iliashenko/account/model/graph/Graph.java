package com.iliashenko.account.model.graph;

import java.util.List;

import com.iliashenko.account.model.BusStop;

public class Graph {
	private final List<BusStop> vertexes;
	private final List<Edge> edges;

	public Graph(List<BusStop> vertexes, List<Edge> edges) {
		this.vertexes = vertexes;
		this.edges = edges;
	}

	public List<BusStop> getVertexes() {
		return vertexes;
	}

	public List<Edge> getEdges() {
		return edges;
	}

}