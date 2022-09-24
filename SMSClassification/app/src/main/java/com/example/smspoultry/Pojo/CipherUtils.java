package com.example.smspoultry.Pojo;

import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CipherUtils {
     static String password="static String ENCRYPTION_KEY = \"0123456789abcdef\";";
     static String algo="DES";
     static  String SECRET_KEY = "aesEncryptionKey";
     static String INIT_VECTOR = "encryptionIntVec";
/*
    public static String encrypt(@NonNull String plaintext)
    {
        String encrypted = "";
        try{
            DESKeySpec keySpec = new DESKeySpec(password.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algo);
            SecretKey key = keyFactory.generateSecret(keySpec);
            byte[] cleartext = plaintext.getBytes(StandardCharsets.UTF_8);

            Cipher cipher = Cipher.getInstance(algo); // cipher is not thread safe
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypted = Base64.encodeToString(cipher.doFinal(cleartext), Base64.DEFAULT);

        }catch (Exception e){
            Log.e("#evote",e+"");
        }
        return encrypted;
    }

    public static String decrpyt(@NonNull String encryptedtext)
    {
        String decrypted = "";
        try{
            DESKeySpec keySpec = new DESKeySpec(password.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algo);
            SecretKey key = keyFactory.generateSecret(keySpec);
            byte[] encbytes = encryptedtext.getBytes(StandardCharsets.UTF_8);

            Cipher cipher = Cipher.getInstance(algo); // cipher is not thread safe
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte []decBytes=cipher.doFinal(encbytes);
            decrypted=new String(decBytes);

        }catch (Exception e){
            Log.e("#evote",e+"");
        }
        return decrypted;

    }*/

    public static String encryptNew(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decryptNew(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decode(value, Base64.DEFAULT));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
