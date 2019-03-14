package com.iliashenko.account.service;

import java.sql.Timestamp;
import java.util.List;

import com.iliashenko.account.model.Statistics;

public interface StatisticsService {
	Statistics findByStartTime(Timestamp time);

	Statistics findByEndTime(Timestamp time);

	List<Statistics> findAllStatisticsRecords();
}
