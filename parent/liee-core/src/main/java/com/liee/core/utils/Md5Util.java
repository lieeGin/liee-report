package com.liee.core.utils;

public class Md5Util {
	private final static Digest digest = new Digest("MD5");

	public static String encrypt(byte[] buff) {
		return digest.encrypt(buff);
	}

	public static String encrypt(String s) {
		return digest.encrypt(s);
	}

	public static String encrypt(String s, String charsetName) {
		return digest.encrypt(s, charsetName);
	}

	public static String fileHash(String fileName) {
		return digest.fileHash(fileName);
	}

}
