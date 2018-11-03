package io.amecodelabs.ims.models.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Digest {
	public static String encrypt(String password) {
		return DigestUtils.sha1Hex(password);
	}
}
