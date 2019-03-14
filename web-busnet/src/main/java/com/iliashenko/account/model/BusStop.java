package com.iliashenko.account.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bus_stop")
public class BusStop {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "parking_space")
	private Integer parkingSpace;
	@Column(name = "x_coord")
	private Double xCoord;
	@Column(name = "y_coord")
	private Double yCoord;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "bus_route_stop", joinColumns = @JoinColumn(name = "stop_id"), inverseJoinColumns = @JoinColumn(name = "route_number"))
	private List<BusRoute> routes = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParkingSpace() {
		return parkingSpace;
	}

	public void setParkingSpace(Integer parkingSpace) {
		this.parkingSpace = parkingSpace;
	}

	public Double getxCoord() {
		return xCoord;
	}

	public void setxCoord(Double xCoord) {
		this.xCoord = xCoord;
	}

	public Double getyCoord() {
		return yCoord;
	}

	public void setyCoord(Double yCoord) {
		this.yCoord = yCoord;
	}

	public List<BusRoute> getRoutes() {
		return routes;
	}

	public void setRoutes(List<BusRoute> routes) {
		this.routes = routes;
	}

	public void addStop(BusRoute route) {
		routes.add(route);
		route.getStops().add(this);
	}

	public void removeStop(BusRoute route) {
		routes.remove(route);
		route.getStops().remove(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parkingSpace == null) ? 0 : parkingSpace.hashCode());
		result = prime * result + ((routes == null) ? 0 : routes.hashCode());
		result = prime * result + ((xCoord == null) ? 0 : xCoord.hashCode());
		result = prime * result + ((yCoord == null) ? 0 : yCoord.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		else if (obj instanceof BusStop) {
			BusStop other = (BusStop) obj;
			return Objects.equals(this.id, other.id);
		} else {
			return false;
		}
	}

}
