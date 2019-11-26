package com.eliblaney.bachify;

import java.util.ArrayList;
import java.util.List;

public class Key {

	private static final String MAJOR_SCALE = "WWHWWWH";
	private static final String MINOR_SCALE = "WHWWHWW";

	private final Note tonic;
	private final String scalePattern;
	private final List<Note> scale = new ArrayList<>();

	public Key(String tonic, boolean major) {
		this(new Note(tonic, 4), major);
	}

	public Key(Note tonic, boolean major) {
		this(tonic, major ? MAJOR_SCALE : MINOR_SCALE);
	}

	public Key(Note tonic, String scalePattern) {
		this.tonic = tonic;
		scalePattern = scalePattern.toUpperCase();
		this.scalePattern = scalePattern;

		int octave = tonic.getOctave();
		char tonicLetter = this.tonic.getNoteLetter();
		Note note = tonic;
		scale.add(tonic);
		for(int i = 0; i < scalePattern.length() - 1; i++){
			char noteLetter = (char) (tonicLetter + i);
			if(noteLetter > 'G') {
				noteLetter -= 7;
			}
			if(noteLetter == 'C') {
				// keep octave consistent with scale direction
				octave++;
			}
			boolean whole = scalePattern.charAt(i) == 'W';
			if(whole) {
				note = note.skip(Interval.WHOLE_STEP, true);
			} else {
				note = note.skip(Interval.HALF_STEP, true);
			}
			scale.add(note);
		}
	}

	public Key(String tonic, String scalePattern) {
		this(new Note(tonic, 4), scalePattern);
	}

	public String getScalePattern() {
		return this.scalePattern;
	}

	public int getScaleDegree(String note) {
		return getScaleDegree(new Note(note, 4));
	}

	public int getScaleDegree(Note note) {
		Note t = getTonic();
		int tonicChar = t.getNoteLetter();
		int thisChar = note.getNoteLetter();
		if(thisChar < tonicChar) {
			// "octave displacement" if necessary
			thisChar += 7;
		}
		return thisChar - tonicChar + 1;
	}

	public Note getTonic() {
		return this.tonic;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(this.getTonic() + (this.isMajor() ? " major: " : (this.isMinor() ? " minor: " : " special: ")));
		for(Note n : this.getScale()) {
			str.append(n).append(" ");
		}
		return str.toString();
	}

	public boolean isMajor() {
		return scalePattern.equals(MAJOR_SCALE);
	}

	public boolean isMinor() {
		return scalePattern.equals(MINOR_SCALE);
	}

	public List<Note> getScale() {
		return scale;
	}

}
