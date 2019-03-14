package com.iliashenko.account.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iliashenko.account.model.Statistics;
import com.iliashenko.account.repository.StatisticsRepository;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private StatisticsRepository statRepository;

	@Override
	public Statistics findByStartTime(Timestamp time) {
		return statRepository.findByStartTime(time);
	}

	@Override
	public Statistics findByEndTime(Timestamp time) {
		return statRepository.findByEndTime(time);
	}

	@Override
	public List<Statistics> findAllStatisticsRecords() {
		return statRepository.findAll();
	}

}
