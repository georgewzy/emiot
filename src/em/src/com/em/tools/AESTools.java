package com.em.tools;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;

public class AESTools {
    public static void main(String args[]) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey key = keyGenerator.generateKey();
        
        byte[] kkkkkk = "0102030405060708".getBytes();
        SecretKeySpec k0 = new SecretKeySpec(kkkkkk, key.getAlgorithm());
        
        String enStr = encrypt("我是准备加密的字符串！", k0);
        
//        System.out.println("key："+ AESTools.getKeyStr(key));
        
        System.out.println("key："+ AESTools.getKeyStr(k0));
        
        System.out.println("加密："+ enStr);
        
        
//        SecretKeySpec kk = new SecretKeySpec(key.getEncoded(), key.getAlgorithm());
//        
//        String jm = "4JGrR8LdvOHCK1NOw1+6Z0tiWh/U4rmEBpx82NSeGUbNgkXbfnMsAr0r1D8n/AjE";
//        SecretKeySpec kk = new SecretKeySpec(jm.getBytes(), jm);
//        System.out.println("解密："+ decrypt(enStr, kk));
//        
        SecretKeySpec kkk = AESTools.getSecretKey("MDEwMjAzMDQwNTA2MDcwOA==");//new SecretKeySpec(deKey, "AES");
        System.out.println("解密2："+ decrypt("4JGrR8LdvOHCK1NOw1+6Z0tiWh/U4rmEBpx82NSeGUbNgkXbfnMsAr0r1D8n/AjE", kkk));
                                                 
    }

    private static String AES_MODE = "AES/CBC/PKCS5Padding"; //AES加密算法，CBC反馈模式，PKCSPadding的填充方案
    
    private static byte[] IV_BYTE = "0102030405060708".getBytes(); //加解密时的初始化向量 16bytes
    
    /**
     * 取得密钥对象
     * @return
     */
    public static SecretKey getKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            SecretKey key = keyGenerator.generateKey();
            return key;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 取得密钥字符串
     * @return
     */
    public static String getKeyStr(SecretKey key) {
        return new sun.misc.BASE64Encoder().encode(key.getEncoded());
    }
    
    public static SecretKeySpec getSecretKey(String key) throws Exception {
        byte[] deKey = new sun.misc.BASE64Decoder().decodeBuffer(key);
        return new SecretKeySpec(deKey, "AES");
    }
    
    /**
     * 加密
     * @return
     * @throws Exception
     */
    public static String encrypt(String str, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, key , new IvParameterSpec(IV_BYTE));
            byte[] strByte = str.getBytes();
            byte[] encryStr = cipher.doFinal(strByte);//加密
            return new sun.misc.BASE64Encoder().encode(encryStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     * @return
     * @throws Exception
     */
    public static String decrypt(String str, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_MODE);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV_BYTE));
            byte[] strByte = new BASE64Decoder().decodeBuffer(str);
            byte[] decryStr = cipher.doFinal(strByte); //解密
            return new String(decryStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}