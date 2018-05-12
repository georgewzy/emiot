package com.em.tools;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

public class KeyGenerator {
	//default key length
    private static final int KEY_LENGTH = 20;
    
    private static final int PASSWORDLENGTH = 8;
    
    private static final StringBuffer sb = new StringBuffer("123456789abcdefghijklmnpqrstuvwxyz");

    public KeyGenerator() {
    }

    /**
     * Generate a random key
     * @return  key bytes
     */
    public byte[] generateKey() {
        SecureRandom sr = new SecureRandom();
        byte[] keyBytes = new byte[KEY_LENGTH];
        sr.nextBytes(keyBytes);
        return keyBytes;
    }

    /**
     * generate a random key and encode with base64
     * @return  base64 encoded random key
     */
    public String generateKeyEncodeBase64() {
        return new BASE64Encoder().encode(generateKey());
    }
    
    public String generatePsw() {
    	Random random = new Random();
    	StringBuffer pswSb = new StringBuffer();
    	for (int i = 0; i < PASSWORDLENGTH; i++) {
    		int rr = random.nextInt(sb.length());
    		pswSb.append(sb.substring(rr, ++rr));
    	}
    	return pswSb.toString();
    }
    
    public SecretKeySpec getKey() throws NoSuchAlgorithmException {
        javax.crypto.KeyGenerator keyGenerator = javax.crypto.KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey key = keyGenerator.generateKey();
        
        byte[] kkkkkk = "0102030405060708".getBytes();
        SecretKeySpec k0 = new SecretKeySpec(kkkkkk, key.getAlgorithm());
        
        return k0;
    }
    
    public static String getKeyStr(SecretKey key) {
        return new sun.misc.BASE64Encoder().encode(key.getEncoded());
    }
}
