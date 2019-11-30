package com.eliblaney.bachify;

public enum Interval {

	MINOR_SECOND(1, 1),
	MAJOR_SECOND(1, 2),
	MINOR_THIRD(2, 3),
	MAJOR_THIRD(2, 4),
	PERFECT_FOURTH(3, 5),
	AUGMENTED_FOURTH(3, 6),
	DIMINISHED_FIFTH(4, 6),
	PERFECT_FIFTH(4, 7),
	MINOR_SIXTH(5, 8),
	MAJOR_SIXTH(5, 9),
	MINOR_SEVENTH(6, 10),
	MAJOR_SEVENTH(6, 11),
	OCTAVE(0, 12),
	HALF_STEP(1, 1),
	WHOLE_STEP(1, 2),
	AUGMENTED_SECOND(1, 3);

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

	public static Interval from(Note a, Note b) {
		int noteLetterDiff = b.getNoteLetter() - a.getNoteLetter();
		if(noteLetterDiff < 'A') {
			noteLetterDiff += 7;
		}

		int skipSize = b.getCode() - a.getCode();
		if(skipSize < 0) {
			skipSize += 12;
		}

		for(Interval i : values()) {
			if(i.getNoteLetterDiff() == noteLetterDiff && i.getSkipSize() == skipSize) {
				return i;
			}
		}
		return null;
	}

	public static Interval of(int skipSize) {
		if(skipSize < 0) {
			skipSize *= -1;
		}
		// octave displacement
		skipSize %= 12;
		for(Interval i : values()) {
			if(i.getSkipSize() == skipSize) {
				return i;
			}
		}
		return null;
	}

}
