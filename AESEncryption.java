package com.thiran.lms.config;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryption {
    private static final String ALGO = "AES";

    public static String encrypt(String data, String secret) throws Exception {
        Key key = generateKey(secret);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encVal);
    }

    private static Key generateKey(String secret) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(secret.getBytes());
        return new SecretKeySpec(decoded, ALGO);
    }

    public static void main(String[] args) {
        try {
            String secret = "bXVzdGJlMTZieXRlc2tleQ=="; // Base64 encoded 16-byte key
            String data = "Hello, World!";
            String encryptedData = encrypt(data, secret);
            System.out.println("Encrypted Data: " + encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

