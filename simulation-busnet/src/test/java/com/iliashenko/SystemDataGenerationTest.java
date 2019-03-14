package com.iliashenko;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.iliashenko.generation.SystemDataGeneration;
import com.iliashenko.utils.Consts;
import com.iliashenko.utils.Time;

public class SystemDataGenerationTest {

	@Test
	public void systemDataGenerationTest() {
		assertEquals(Consts.PEAK_LOAD_INDEX,
				SystemDataGeneration.loadIndex(new Time("2018-03-07 7:45", Time.FULL_DATE_FORMAT)));

		assertEquals(Consts.DAY_LOAD_INDEX,
				SystemDataGeneration.loadIndex(new Time("2018-03-07 12:45", Time.FULL_DATE_FORMAT)));

		assertEquals(Consts.NIGHT_LOAD_INDEX,
				SystemDataGeneration.loadIndex(new Time("2018-03-07 23:56", Time.FULL_DATE_FORMAT)));

	}
}
