package com.iliashenko.account.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iliashenko.account.model.Statistics;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
	Statistics findByStartTime(Timestamp time);

	Statistics findByEndTime(Timestamp time);
}
