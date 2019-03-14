package com.iliashenko;

public enum Direction {
	FROM(true), INTO(false), PERMANENT(null);

	private Boolean isFrom;
	private static final Direction[] VALUES = values();
	private static final int SIZE = VALUES.length;

	private Direction(Boolean isFrom) {
		this.isFrom = isFrom;
	}

	public static int getSize() {
		return SIZE;
	}

	public Direction changeDirection() {
		if (this.isFrom) {
			return INTO;
		} else {
			return FROM;
		}
	}
}
