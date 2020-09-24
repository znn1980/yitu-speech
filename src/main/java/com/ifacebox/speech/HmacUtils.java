package com.ifacebox.speech;

import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacUtils {
	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

	public static String hmacSha256Hex(String key, String data) {
		try {
			Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
			mac.init(new SecretKeySpec(key.getBytes(), HMAC_SHA256_ALGORITHM));
			return toHexString(mac.doFinal(data.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String toHexString(byte[] bytes) {
		try (Formatter formatter = new Formatter()) {
			for (byte b : bytes) {
				formatter.format("%02x", b);
			}
			return formatter.toString();
		}
	}

}
