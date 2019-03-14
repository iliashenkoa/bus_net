package com.iliashenko;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "bus-shedule")
@XmlRootElement(name = "shedule")
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * Class that represent's bus shedule which contains set of shedule records.
 * 
 * @author Iliashenko A.
 * @version 0.1
 * @since 26-02-2019
 *
 */
public class BusShedule {

	@XmlElement(name = "bus-shedule-record")
	private Set<BusSheduleRecord> sheduleRecords;

	public BusShedule() {
		super();
		this.sheduleRecords = new HashSet<>();
	}

	public BusShedule(Set<BusSheduleRecord> sheduleRecords) {
		super();
		this.sheduleRecords = sheduleRecords;
	}

	public Set<BusSheduleRecord> getSheduleRecords() {
		return sheduleRecords;
	}

	public void setSheduleRecords(Set<BusSheduleRecord> sheduleRecords) {
		this.sheduleRecords = sheduleRecords;
	}

	public void addSheduleRecord(BusSheduleRecord sheduleRecord) {
		if (!sheduleRecords.contains(sheduleRecord)) {
			sheduleRecords.add(sheduleRecord);
		}
	}

	public void sortByStartTime() {
		List<BusSheduleRecord> bs = new ArrayList<>(this.sheduleRecords);
		Collections.sort(bs);
		this.sheduleRecords = new LinkedHashSet<>(bs);
	}

}
