package com.eliblaney.bachify;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChordTest {

	@Test
	void getChordMembers() {
		Chord chord = new Chord("D", Chord.ChordQuality.HALF_DIMINISHED, Chord.ChordType.SEVEN, Chord.ChordInversion.FIRST);
		assertEquals("[F, Ab, C, D]", Arrays.asList(chord.getChordMembers()).toString(), "Ddim65b7 invalid");
	}

	@Test
	@Disabled
	void canBeFollowedBy() {
	}
}