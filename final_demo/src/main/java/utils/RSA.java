package utils;

import org.junit.Test;
import po.trade;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.*;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA {
    @Test
    public  void  t(){
        KeyPair name = RSA.getKey("name");
        PrivateKey aPrivate = name.getPrivate();
        PublicKey aPublic = name.getPublic();
        String privateKey = RSA.getPrivateKey(aPrivate);
        String publicKey = RSA.getPublicKey(aPublic);
        String ms = "我是是";

        try {
            byte[] decode = ms.getBytes("utf-8");
           // byte[] decode = Base64.getDecoder().decode();
            byte[] encrypt = RSA.encrypt(decode, aPublic);
            byte[] decrypt = RSA.decrypt(encrypt, aPrivate);
            String s = new String(decrypt, "utf-8");
            System.out.println(s);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    public static KeyPair getKey (String username) {
        KeyPairGenerator kpGen = null;
        try {
            kpGen = KeyPairGenerator.getInstance("RSA");
            kpGen.initialize(1024);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        KeyPair kp = kpGen.generateKeyPair();
        return kp;
    }
    public void T(){

    }

    public static String getPrivateKey(PrivateKey sk)  {
        String base64EncodedPublicKey = Base64.getEncoder().encodeToString( sk.getEncoded());
        return  base64EncodedPublicKey;
    }
    public static String getPublicKey(PublicKey pk) {
       // String base64EncodedPublicKey = Base64.getEncoder().encodeToString( pk.getEncoded());
        X509EncodedKeySpec x509KeySpec = new java.security.spec.X509EncodedKeySpec(pk.getEncoded());
        String pem = "-----BEGIN PUBLIC KEY-----\n" +
                Base64.getEncoder().encodeToString(x509KeySpec.getEncoded()) +
                "\n-----END PUBLIC KEY-----";
        return pem;
       // return base64EncodedPublicKey;
    }
    public static byte[] encrypt(byte[] message,PublicKey pk) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pk);
        return cipher.doFinal(message);
    }

    public static byte[] decrypt(byte[] input, PrivateKey sk) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, sk);
            return cipher.doFinal(input);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    public void tet(){
        String s = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAnBUwCY4Z+AXU7hdZbgV3tSjTf6Dv05Qf6aRhrUQrhOQDE8WAOF/GxxSSEKV5QXeAgEXEPj6uTvKOSuqvA/LjnQIDAQABAkAGryBD2H0HGYRj25t07putNEh9EJtp+EGafp4xjpDMUQdetwyL+cWHcxjsQJpnXD7eprnxI0sGi5o5P/I06vxVAiEA7M+SDvIWEnVEzgUd8ynsUJIzqjsp7SMyCibDTdagSqcCIQCouvxIwKjGe0mlUHChOgjhe/zIxalrjhXX8231afFcGwIgF/mP1PmHaIj81UrJVHP7G9Ehb+ubfAmjKEnyXfALvGMCIBtmEaV2s3FXf8P6Ze+oTtzel1O61SID9oQUoVxikbDVAiEAxoBexaTOBaYRe2rOkudKbqNTFCJLyOD9VuLA634vf2g=";
        System.out.println(s.length());
    }

  /*  class RSA_trade{

        String username;
        // 私钥:
        PrivateKey sk;
        // 公钥:
        PublicKey pk;

        public RSA_trade(String username) throws GeneralSecurityException{
            this.username = username;
            KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
            kpGen.initialize(1024);
            KeyPair kp = kpGen.generateKeyPair();
            this.sk = kp.getPrivate();
            this.pk = kp.getPublic();
        }

        public byte[] getPrivateKey()  {
            return this.sk.getEncoded();
        }

        // 把公钥导出为字节
        public byte[] getPublicKey() {
            return this.pk.getEncoded();
        }

        // 用公钥加密:
        public byte[] encrypt(byte[] message) throws GeneralSecurityException {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, this.pk);
            return cipher.doFinal(message);
        }

        // 用私钥解密:
        public byte[] decrypt(byte[] input) throws GeneralSecurityException {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, this.sk);
            return cipher.doFinal(input);
        }

    }
*/

}
