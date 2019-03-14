package com.iliashenko.generation;

import com.iliashenko.utils.Consts;
import com.iliashenko.utils.Time;

public class SystemDataGeneration {

	private SystemDataGeneration() {
		super();
	}

	public static final Time DAY_START = new Time("5:00", Time.DATE_FORMAT);
	public static final Time MORNING_PEAK_HOUR_START = new Time("7:00", Time.DATE_FORMAT);
	public static final Time MORNING_PEAK_HOUR_END = new Time("9:00", Time.DATE_FORMAT);
	public static final Time EVENING_PEAK_HOUR_START = new Time("17:00", Time.DATE_FORMAT);
	public static final Time EVENING_PEAK_HOUR_END = new Time("19:00", Time.DATE_FORMAT);
	public static final Time DAY_END = new Time("23:55", Time.DATE_FORMAT);

	public static int loadIndex(Time timeGeneration) {
		long timeInMS = timeGeneration.getTimeInMs();
		if (timeGeneration.getTimeInMs() >= DAY_END.getTimeInMs()
				|| timeGeneration.getTimeInMs() <= DAY_START.getTimeInMs()) {
			return Consts.NIGHT_LOAD_INDEX;
		} else if (!timeGeneration.isWeekend()
				&& ((timeGeneration.getTimeInMs() >= MORNING_PEAK_HOUR_START.getTimeInMs()
						&& timeGeneration.getTimeInMs() <= MORNING_PEAK_HOUR_END.getTimeInMs())
						|| (timeInMS >= EVENING_PEAK_HOUR_START.getTimeInMs()
								&& timeInMS <= EVENING_PEAK_HOUR_END.getTimeInMs()))) {
			return Consts.PEAK_LOAD_INDEX;
		}
		return Consts.DAY_LOAD_INDEX;
	}
}
