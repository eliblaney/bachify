package com.eliblaney.bachify;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KeyTest {

	@Test
	void getScaleDegree() {
		Key key = new Key("F#", true);
		assertEquals(7, key.getScaleDegree("E#"), "Failed to get scale degree 7 in F# major");
	}

	@Test
	void getScale() {
		Key key = new Key("Eb", true);
		assertEquals("[Eb, F, G, Ab, Bb, C, D]", Arrays.asList(key.getScale()).toString(), "Eb major scale failed");

		key = new Key("F#", false);
		assertEquals("[F#, G#, A, B, C#, D, E]", Arrays.asList(key.getScale()).toString(), "F# minor scale failed");
	}
}