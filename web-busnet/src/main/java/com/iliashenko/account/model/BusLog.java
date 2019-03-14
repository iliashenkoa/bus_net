package com.iliashenko.account.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "bus_log")
public class BusLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Column(name = "time_moment")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm", timezone = "Europe/Kiev")
	private Timestamp timeMoment;
	@Column(name = "bus_stop_id")
	private Integer busStopId;
	@Column(name = "route_num")
	private Integer routeNum;
	@Column(name = "bus_id")
	private Integer busId;
	@Column(name = "passengers_count_entry")
	private Integer passengersCountEntry;
	@Column(name = "passengers_count_exit")
	private Integer passengersCountExit;
	@Column(name = "passengers_count_in")
	private Integer passengersCountIn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getTimeMoment() {
		return timeMoment;
	}

	public void setTimeMoment(Timestamp timeMoment) {
		this.timeMoment = timeMoment;
	}

	public Integer getBusStopId() {
		return busStopId;
	}

	public void setBusStopId(Integer busStopId) {
		this.busStopId = busStopId;
	}

	public Integer getRouteNum() {
		return routeNum;
	}

	public void setRouteNum(Integer routeNum) {
		this.routeNum = routeNum;
	}

	public Integer getBusId() {
		return busId;
	}

	public void setBusId(Integer busId) {
		this.busId = busId;
	}

	public Integer getPassengersCountEntry() {
		return passengersCountEntry;
	}

	public void setPassengersCountEntry(Integer passengersCountEntry) {
		this.passengersCountEntry = passengersCountEntry;
	}

	public Integer getPassengersCountExit() {
		return passengersCountExit;
	}

	public void setPassengersCountExit(Integer passengersCountExit) {
		this.passengersCountExit = passengersCountExit;
	}

	public Integer getPassengersCountIn() {
		return passengersCountIn;
	}

	public void setPassengersCountIn(Integer passengersCountIn) {
		this.passengersCountIn = passengersCountIn;
	}

	public int getHour() {
		Date date = new Date(timeMoment.getTime());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

}
