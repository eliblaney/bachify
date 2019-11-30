package com.eliblaney.bachify;

import java.util.ArrayList;
import java.util.List;

public class Chord {

	private final Note root;
	private final ChordQuality quality;
	private final ChordType type;
	private final ChordInversion inversion;

	private final Note[] chordMembers;

	public Chord(String root, ChordQuality quality, ChordType type, ChordInversion inversion) {
		this(new Note(root, 4), quality, type, inversion);
	}

	public Chord(Note root, ChordQuality quality, ChordType type, ChordInversion inversion) {
		this.root = root;
		this.quality = quality;
		this.type = type;
		this.inversion = inversion;

		List<Note> notes = new ArrayList<>();
		Note n = root;
		notes.add(n);
		for(Interval i : quality.getIntervals(type, ChordInversion.ROOT)) {
			n = n.skip(i, true);
			notes.add(n);
		}
		int invInd = inversion.getInversionIndex();
		if(invInd > 0) {
			Note chordRoot = notes.get(0).skip(Interval.OCTAVE, true);
			notes.add(chordRoot);
			notes.remove(0);
		}
		if(invInd > 1) {
			Note chordThird = notes.get(0).skip(Interval.OCTAVE, true);
			notes.add(chordThird);
			notes.remove(0);
		}
		if(invInd > 2) {
			Note chordFifth = notes.get(0).skip(Interval.OCTAVE, true);
			notes.add(chordFifth);
			notes.remove(0);
		}
		this.chordMembers = notes.toArray(new Note[notes.size()]);
	}

	public Note[] getChordMembers() {
		return chordMembers;
	}

	public Chord[] canBeFollowedBy(Key key) {
		return null;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(Note n : getChordMembers()) {
			str.append(n).append(n.getOctave()).append(" ");
		}
		return str.toString();
	}

	public enum ChordQuality {
		MAJOR, MINOR, AUGMENTED, DIMINISHED, MAJOR_SEVEN, HALF_DIMINISHED;

		public Interval[] getIntervals(ChordType type, ChordInversion inversion) {
			List<Interval> intervals = new ArrayList<>();
			int[] chordMembers = type.getChordMembers(inversion);

			for(int i = 1; i < chordMembers.length; i++) {
				int chordMember = chordMembers[i];
				switch(chordMember) {
					case 3:
						switch(this) {
							case MAJOR:
							case AUGMENTED:
							case MAJOR_SEVEN:
								intervals.add(Interval.MAJOR_THIRD);
								break;
							default:
								intervals.add(Interval.MINOR_THIRD);
						}
						break;
					case 5:
						switch(this) {
							case AUGMENTED:
							case MINOR:
								intervals.add(Interval.MAJOR_THIRD);
								break;
							default:
								intervals.add(Interval.MINOR_THIRD);
						}
						break;
					case 7:
						switch(this) {
							case MAJOR_SEVEN:
							case HALF_DIMINISHED:
								intervals.add(Interval.MAJOR_THIRD);
								break;
							default:
								intervals.add(Interval.MINOR_THIRD);
						}
						break;
					case 9:
						intervals.add(Interval.PERFECT_FIFTH);
						break;
					case 11:
						intervals.add(Interval.MINOR_SEVENTH);
						break;
				}
			}

			return intervals.toArray(new Interval[intervals.size()]);
		}
	}

	public enum ChordType {
		TRIAD(1, 3, 5), SEVEN(1, 3, 5, 7), NINE(1, 3, 5, 9), ELEVEN(1, 3, 5, 11);

		private final int[] chordMembers;

		ChordType(int... chordMembers) {
			this.chordMembers = chordMembers;
		}

		public int[] getChordMembers(ChordInversion inversion) {
			int length = chordMembers.length;
			int inversionIndex = inversion.getInversionIndex();
			int[] invertedMembers = new int[length];

			int j = 0;
			for(int i = inversionIndex; i < length; i++) {
				invertedMembers[j++] = chordMembers[i];
			}
			for(int i = 0; i < inversionIndex; i++) {
				invertedMembers[j++] = chordMembers[i];
			}
			return invertedMembers;
		}

	}

	public enum ChordInversion {
		ROOT(0), FIRST(1), SECOND(2), THIRD(3);

		private final int inversionIndex;

		ChordInversion(int inversionIndex) {
			this.inversionIndex = inversionIndex;
		}

		int getInversionIndex() {
			return inversionIndex;
		}
	}

}
