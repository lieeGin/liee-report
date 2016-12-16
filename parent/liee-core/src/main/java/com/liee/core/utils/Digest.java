package com.liee.core.utils;

import java.security.MessageDigest;
import java.util.logging.Logger;
import java.io.*;

public class Digest {
	private static Logger log = Logger.getLogger(Md5Util.class.getName());

	private String algorithm = "MD5";

	public Digest(String algorithm) {
		if (algorithm != null && algorithm.length() > 0) {
			this.algorithm = algorithm;
		}
	}

	public String encrypt(String s) {
		return encrypt(s, null);
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String encrypt(String s, String charsetName) {
		if (s == null) return "";
		byte buff[];
		try {
			if (charsetName != null && charsetName.length() > 0)
				buff = s.getBytes(charsetName);
			else
				buff = s.getBytes();
			MessageDigest messagedigest = MessageDigest.getInstance(getAlgorithm());
			messagedigest.update(buff);
			byte result[] = messagedigest.digest();
			return StringUtil.byte2Hex(result);
		} catch (Exception e) {
			log.severe(e.getMessage());
			return "";
		}
	}

	public String encrypt(byte[] buff) {
		if (buff == null) return "";
		try {
			MessageDigest messagedigest = MessageDigest.getInstance(getAlgorithm());
			messagedigest.update(buff);
			byte result[] = messagedigest.digest();
			return StringUtil.byte2Hex(result);
		} catch (Exception e) {
			log.severe(e.getMessage());
			return "";
		}
	}

	public String fileHash(String fileName) {
		try {
			byte buff[] = new byte[4096];
			MessageDigest messagedigest = MessageDigest.getInstance(getAlgorithm());
			FileInputStream fis = new FileInputStream(fileName);
			int len = 0;
			while ((len = fis.read(buff)) > 0) {
				messagedigest.update(buff, 0, len);
			}
			fis.close();
			byte result[] = messagedigest.digest();
			return StringUtil.byte2Hex(result);
		} catch (Exception e) {
			log.severe(e.getMessage());
			return "";
		}
	}

}
