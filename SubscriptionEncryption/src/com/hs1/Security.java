package com.hs1;

import com.hs1.exceptions.EncryptionException;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Security {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 256;
    private static IvParameterSpec ivParameterSpec;
    private static SecretKey secretKey;

    private Security() {
    }

    static {
        try {
            ivParameterSpec = generateIv();
            secretKey = generateKey(KEY_SIZE);
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException(e.getMessage(), e);
        }
    }

    public static String encode(final String clearText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] cipherText = cipher.doFinal(clearText.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    public static String decode(final String encodedText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(encodedText));
        return new String(plainText);
    }

    private static SecretKey generateKey(final int keysize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(keysize);
        return keyGenerator.generateKey();
    }

    private static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
}
