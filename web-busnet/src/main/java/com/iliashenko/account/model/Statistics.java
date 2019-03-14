package com.iliashenko.account.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "statistics")
public class Statistics {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "start_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss:SSS", timezone = "Europe/Kiev")
	private Timestamp startTime;

	@Column(name = "end_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss:SSS", timezone = "Europe/Kiev")
	private Timestamp endTime;

	@Column(name = "time_simulation")
	private Long timeSimulation;

	@Column(name = "load_percentage")
	private Double loadPercentage;

	@Column(name = "routes_count")
	private Integer routesCount;

	@Column(name = "bus_count")
	private Integer busCount;

	@Column(name = "passengers_count")
	private Integer passengersCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Long getTimeSimulation() {
		return timeSimulation;
	}

	public void setTimeSimulation(Long timeSimulation) {
		this.timeSimulation = timeSimulation;
	}

	public Double getLoadPercentage() {
		return loadPercentage;
	}

	public void setLoadPercentage(Double loadPercentage) {
		this.loadPercentage = loadPercentage;
	}

	public Integer getRoutesCount() {
		return routesCount;
	}

	public void setRoutesCount(Integer routesCount) {
		this.routesCount = routesCount;
	}

	public Integer getBusCount() {
		return busCount;
	}

	public void setBusCount(Integer busCount) {
		this.busCount = busCount;
	}

	public Integer getPassengersCount() {
		return passengersCount;
	}

	public void setPassengersCount(Integer passengersCount) {
		this.passengersCount = passengersCount;
	}

}
