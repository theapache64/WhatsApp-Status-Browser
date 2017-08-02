package com.theah64.whatsappstatusbrowser.utils;

import android.util.Base64;
import android.util.Log;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/*
 * Used to simple text enc and dec
 * Created by Shifar Shifz on 6/29/2015.
 */
public class DarKnight {

    private static final String ALGORITHM = "AES";

    private static final byte[] SALT = new byte[]{'t', 'H', 'e', 'A', 'p', 'A', 'c', 'H', 'e', '6', '4', '1', '0', '0', '0', '0'};
    private static final String X = DarKnight.class.getSimpleName();

    public static String getEncrypted(String plainText) {

        Key salt = getSalt();

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, salt);
            byte[] encodedValue = cipher.doFinal(plainText.getBytes());
            String encrypted = Base64.encodeToString(encodedValue, Base64.DEFAULT);
            Log.d(X, "Encryption Success: " + encrypted);
            return encrypted;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(X, "Error:" + e.getMessage());
        }

        return null;
    }

    public static String getDecrypted(String encodedText) {

        Key salt = getSalt();
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, salt);
            byte[] decodedValue = Base64.decode(encodedText, Base64.DEFAULT);
            byte[] decValue = cipher.doFinal(decodedValue);
            String decrypted = new String(decValue);
            Log.d(X, "Decryption Success: " + decrypted);
            return decrypted;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(X, "Error:" + e.getMessage());
        }
        return null;
    }

    public static Key getSalt() {
        return new SecretKeySpec(SALT, ALGORITHM);
    }

}