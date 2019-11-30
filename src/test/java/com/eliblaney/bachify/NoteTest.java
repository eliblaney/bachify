package com.eliblaney.bachify;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NoteTest {

	@ParameterizedTest
	@ValueSource(strings = {"C", "Bb", "C#"})
	void getNoteLetter(String letter) {
		Note note = new Note(letter, 4);
		assertEquals(note.getNoteLetter(), letter.charAt(0), "Note letter modified");
	}

	@TestFactory
	Collection<DynamicTest> skip() {
		Collection<DynamicTest> dynamicTests = new ArrayList<>();

		List<String> startNotes = Arrays.asList("C", "Eb", "F", "F#");
		List<Interval> intervals = Arrays.asList(Interval.PERFECT_FIFTH, Interval.MAJOR_THIRD, Interval.PERFECT_FOURTH, Interval.MINOR_THIRD);
		List<String> expectedNotes = Arrays.asList("G", "G", "Bb", "A");
		List<Integer> octaves = Arrays.asList(4, 4, 4, 4);

		for(int i = 0; i < startNotes.size(); i++) {
			String start = startNotes.get(i);
			Interval interval = intervals.get(i);
			String expected = expectedNotes.get(i);
			int octave = octaves.get(i);

			Note startNote = new Note(start, 4);
			Note expectedNote = new Note(expected, octave);

			String displayName = startNote + " -> " + expectedNote;
			Executable exec = () -> assertEquals(startNote.skip(interval, true), expectedNote);
			DynamicTest test = DynamicTest.dynamicTest(displayName, exec);
			dynamicTests.add(test);
		}

		return dynamicTests;
	}

	@Test
	void isEnharmonic() {
		assertTrue(new Note("C#").isEnharmonic(new Note("Db")));
		assertTrue(new Note("D#").isEnharmonic(new Note("Eb")));
		assertTrue(new Note("F#").isEnharmonic(new Note("Gb")));
		assertTrue(new Note("G#").isEnharmonic(new Note("Ab")));
		assertTrue(new Note("A#").isEnharmonic(new Note("Bb")));
	}
}