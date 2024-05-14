package utils;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.*;

import static ch.qos.logback.core.encoder.ByteArrayUtil.hexStringToByteArray;

public class AES {


    public static byte[] encrypt(byte[] key, byte[] input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        // CBC模式需要生成一个16 bytes的initialization vector:
        SecureRandom sr = SecureRandom.getInstanceStrong();
        byte[] iv = sr.generateSeed(16);
        IvParameterSpec ivps = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivps);
        byte[] data = cipher.doFinal(input);
        // IV不需要保密，把IV和密文一起返回:
        return join(iv, data);
    }
    // 解密:
    public static String AESdecrypt(String iv,String encryptedData, String aesKeyBase64) {
        // 把input分割成IV和密文:
        byte[] aesKeyBytes = Base64.getDecoder().decode(aesKeyBase64);
       // byte[] keyBytes = hexStringToByteArray(encryptedData);
        // 创建AES密钥规格

        byte[] decryptedBytes = new byte[0];
        try {

            // 创建初始化向量(IV)，取密钥的前4个字节
            // 使用密钥的前4个字节作为IV
            byte[] ivBytes = Base64.getDecoder().decode(iv);
            // 创建AES密钥规格
            SecretKeySpec keySpec = new SecretKeySpec(aesKeyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

            // 创建Cipher实例并初始化为解密模式
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            // 将加密后的字符串转换为字节数组
            byte[] encryptedDataBytes = Base64.getDecoder().decode(encryptedData);

            // 执行解密
            decryptedBytes = cipher.doFinal(encryptedDataBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }
        // 将解密后的字节数组转换为字符串
        String decryptedData = new String(decryptedBytes, StandardCharsets.UTF_8);

        return decryptedData;
    }

    public static byte[] join(byte[] bs1, byte[] bs2) {
        byte[] r = new byte[bs1.length + bs2.length];
        System.arraycopy(bs1, 0, r, 0, bs1.length);
        System.arraycopy(bs2, 0, r, bs1.length, bs2.length);
        return r;
    }
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;

    }


}
