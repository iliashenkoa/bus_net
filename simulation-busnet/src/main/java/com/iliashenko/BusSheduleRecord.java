package com.iliashenko;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.iliashenko.utils.Time;

@XmlType(name = "bus-shedule-record")
@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * Class that represent's bus shedule record. It has start time when bus must
 * start from stop.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 26-02-2019
 *
 */
public class BusSheduleRecord implements Comparable<BusSheduleRecord> {

	@XmlElement(name = "bus-route-number")
	private int busRouteNumber;
	@XmlElement(name = "direction")
	private Direction directionType;
	@XmlElement(name = "start-time")
	private Time startTime;
	@XmlElement(name = "end-time")
	private Time endTime;

	public BusSheduleRecord() {
		super();
	}

	public BusSheduleRecord(int busRouteNumber, Direction directionType, Time startTime, Time endTime) {
		super();
		this.busRouteNumber = busRouteNumber;
		this.directionType = directionType;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public BusSheduleRecord(int busRouteNumber, Direction directionType, Time startTime) {
		super();
		this.busRouteNumber = busRouteNumber;
		this.directionType = directionType;
		this.startTime = startTime;
	}

	public int getBusRouteNumber() {
		return busRouteNumber;
	}

	public void setBusRouteNumber(int busRouteNumber) {
		this.busRouteNumber = busRouteNumber;
	}

	public Direction getDirectionType() {
		return directionType;
	}

	public void setDirectionType(Direction directionType) {
		this.directionType = directionType;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return String.format("BusSheduleRecord - route#%d - dir:%s, startTime:%s, endTime:%s", busRouteNumber,
				directionType, startTime.toString(), endTime.toString());

	}

	@Override
	public int hashCode() {
		return Objects.hash(getBusRouteNumber(), getDirectionType(), getStartTime().getTimeInMs());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		BusSheduleRecord other = (BusSheduleRecord) obj;
		return Objects.equals(getBusRouteNumber(), other.getBusRouteNumber())
				&& Objects.equals(getDirectionType(), other.getDirectionType())
				&& Objects.equals(getStartTime().getTimeInMs(), other.getStartTime().getTimeInMs());
	}

	@Override
	public int compareTo(BusSheduleRecord record) {
		return Long.compare(this.getStartTime().getTimeInMs(), record.getStartTime().getTimeInMs());
	}
}
