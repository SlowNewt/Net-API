package net.slownewt.core.utils;

import org.apache.commons.lang.RandomStringUtils;

public class RandomString {
	public static String randomString(int length) {
		boolean useLetters = true;
		boolean useNumbers = false;
		String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

		return generatedString;
	}

}