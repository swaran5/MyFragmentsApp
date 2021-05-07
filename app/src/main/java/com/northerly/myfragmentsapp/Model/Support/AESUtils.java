package com.northerly.myfragmentsapp.Model.Support;

import android.util.Base64;

import com.northerly.myfragmentsapp.Model.PojoClass.Data;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    private static String  keyValue = "swaran";

    public static String encrypt(String cleartext)
            throws Exception {
      SecretKey rawKey = getRawKey();
      Cipher c = Cipher.getInstance("AES");
      c.init(Cipher.ENCRYPT_MODE, rawKey);
      byte[] encVal = c.doFinal(cleartext.getBytes());
      String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
      return encryptedValue;
    }

    public static String decrypt(String cleartext)
            throws Exception {
        SecretKey rawKey = getRawKey();
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE, rawKey);
        byte[] decryptedValue = Base64.decode(cleartext, Base64.DEFAULT);
        byte[] decVal = c.doFinal(decryptedValue);
        return new String(decVal);
    }

    private static SecretKey getRawKey() throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = keyValue.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }
}
