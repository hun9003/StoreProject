package com.rateye.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginAPI {
	
	public byte[] hasing(String src) throws NoSuchAlgorithmException {
		  MessageDigest md = MessageDigest.getInstance("SHA-1");	// 해시 알고리즘에서 사용할 알고리즘의 종류를 적어준다.
		  md.update(src.getBytes());
		  return md.digest();
	}
}
