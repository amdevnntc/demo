package com.example.demo.util;

public class RandomText {
	private static final String ALPHA_NUMERIC_STRING = "0123456789";

	public static String generateRND() {
		int count = 4;
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

}