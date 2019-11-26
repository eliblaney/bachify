package com.eliblaney.bachify;

public class Bachify {

	public static void main(String... args) {
		System.out.println(new Key("A", true));
		System.out.println(new Key("A", false));
		System.out.println(new Key("C", true));
		System.out.println(new Key("C", false));
		System.out.println(new Key("Ab", true));
		System.out.println(new Key("Cb", true));
		System.out.println(new Key("G", false));
		Interval i = Interval.MAJOR_THIRD;
	}



}

