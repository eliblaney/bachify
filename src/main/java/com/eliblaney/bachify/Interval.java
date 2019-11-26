package com.eliblaney.bachify;

public enum Interval {

	HALF_STEP(1, 1),
	WHOLE_STEP(1, 2),
	MINOR_SECOND(1, 1),
	MAJOR_SECOND(1, 2),
	MINOR_THIRD(2, 3),
	MAJOR_THIRD(2, 4),
	PERFECT_FOURTH(3, 5),
	TRITONE(3, 6),
	PERFECT_FIFTH(4, 7),
	MINOR_SIXTH(5, 8),
	MAJOR_SIXTH(5, 9),
	MINOR_SEVENTH(6, 10),
	MAJOR_SEVENTH(6, 11),
	OCTAVE(0, 12);

	private final int noteLetterDiff;
	private final int skipSize;

	Interval(int noteLetterDiff, int skipSize) {
		this.noteLetterDiff = noteLetterDiff;
		this.skipSize = skipSize;
	}

	public int getNoteLetterDiff() {
		return noteLetterDiff;
	}

	public int getSkipSize() {
		return skipSize;
	}

}
