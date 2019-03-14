package com.iliashenko.account.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bus_route")
public class BusRoute {
	@Id
	private Integer number;
	private Double price;
	@Column(name = "is_roundabout")
	private Integer isRoundabout;
	@Column(name = "time_interval")
	private Integer timeInterval;

	@ManyToMany(mappedBy = "routes", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<BusStop> stops = new ArrayList<>();

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getIsRoundabout() {
		return isRoundabout;
	}

	public void setIsRoundabout(Integer isRoundabout) {
		this.isRoundabout = isRoundabout;
	}

	public Integer getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(Integer timeInterval) {
		this.timeInterval = timeInterval;
	}

	public List<BusStop> getStops() {
		return stops;
	}

	public void setStops(List<BusStop> stops) {
		this.stops = stops;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isRoundabout == null) ? 0 : isRoundabout.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((stops == null) ? 0 : stops.hashCode());
		result = prime * result + ((timeInterval == null) ? 0 : timeInterval.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		else if (obj instanceof BusRoute) {
			BusRoute other = (BusRoute) obj;
			return Objects.equals(this.number, other.number);
		} else {
			return false;
		}
	}

}
