package com.eliblaney.bachify;

public class Note {

	private static float[] octaveFourFrequencies = {261.63f, 277.18f, 296.66f, 311.13f, 329.63f, 349.23f, 369.99f, 392.00f, 415.30f, 440.00f, 466.16f, 493.88f};

	private final String note;
	private final int octave;

	public Note(String note, int octave) {
		if(!validateNote(note)) {
			throw new IllegalArgumentException("Invalid note: " + note);
		}
		if(note.length() > 1) {
			char[] n = note.toCharArray();
			this.note = Character.toUpperCase(n[0]) + "" + note.substring(1).toLowerCase().replace("s", "#");
		} else {
			this.note = note.toUpperCase();
		}
		this.octave = octave;
	}

	public Note(char noteLetter, int accidental, int octave) {
		this(buildNoteString(noteLetter, accidental), octave);
	}

	public Note sharp() {
		return new Note(getNoteLetter(), getAccidental() + 1, octave);
	}

	public Note flat() {
		return new Note(getNoteLetter(), getAccidental() - 1, octave);
	}

	public char getNoteLetter() {
		return note.toCharArray()[0];
	}

	public int getAccidental() {
		// natural = 0
		if(note.length() == 1) {
			return 0;
		}
		// sharp = 1
		if(note.charAt(1) == '#') {
			return note.substring(1).length();
		}
		// flat = -1
		return -note.substring(1).length();
	}

	public Note skip(Interval interval, boolean up) {
		// adjust note letter
		int noteLetterDiff = interval.getNoteLetterDiff();
		char noteLetter = (char) (getNoteLetter() + ((up ? 1 : -1) * noteLetterDiff));
		int octave = this.getOctave();
		while(noteLetter > 'G') {
			noteLetter -= 7;
		}
		while(noteLetter < 'A') {
			noteLetter += 7;
		}

		// adjust octaves
		if(up && this.getNoteLetter() < 'C' && noteLetter >= 'C') {
			octave++;
		}
		if(!up && noteLetter < 'C' && this.getNoteLetter() >= 'C') {
			octave--;
		}

		Note note = new Note(noteLetter, getAccidental(), octave);

		// adjust codes for comparison
		int thisCode = this.getCode();
		int skipSize = interval.getSkipSize();
		if(note.getCode() - thisCode < 0) {
			thisCode -= 12;
		}

		// adjust accidentals to fit note letters & interval size
		while(note.getCode() - thisCode < skipSize) {
			note = note.sharp();
		}
		while(note.getCode() - thisCode > skipSize) {
			note = note.flat();
		}

		return note;
	}

	public int getOctave() {
		return this.octave;
	}

	public double getFrequency() {
		return octaveFourFrequencies[getCode()] * Math.pow(2, octave - 4);
	}

	public boolean equals(Object o) {
		if(o instanceof Note) {
			Note n = (Note) o;
			return n.note.equals(this.note) && n.octave == this.octave;
		}
		return false;
	}

	public boolean isEnharmonic(Note n) {
		return n.getCode() == this.getCode();
	}

	@Override
	public String toString() {
		return note;
	}

	private int getCode() {
		int code = -1;
		switch(getNoteLetter()) {
			case 'C':
				code = 0;
				break;
			case 'D':
				code = 2;
				break;
			case 'E':
				code = 4;
				break;
			case 'F':
				code = 5;
				break;
			case 'G':
				code = 7;
				break;
			case 'A':
				code = 9;
				break;
			case 'B':
				code = 11;
				break;
		}
		return code + getAccidental();
	}

	private static boolean validateNote(String note) {
		if(note == null || note.length() == 0) {
			return false;
		}
		note = note.toUpperCase();
		char noteLetter = note.toCharArray()[0];
		if(noteLetter < 'A' || noteLetter > 'G') {
			return false;
		}
		if(note.length() > 1) {
			String accidental = note.substring(1);
			return accidental.replace("S", "").replace("#", "").replace("B", "").length() == 0;
		}
		return true;
	}

	private static String buildNoteString(char noteLetter, int accidental) {
		StringBuilder accidentalStr = new StringBuilder();
		if(accidental > 0) {
			for(int i = 0; i < accidental; i++) {
				accidentalStr.append("#");
			}
		} else {
			for(int i =0; i < -accidental; i++) {
				accidentalStr.append("b");
			}
		}
		return Character.toUpperCase(noteLetter) + accidentalStr.toString();
	}

}
