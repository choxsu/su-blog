package com.choxsu.kit;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * RSA非对称加密辅助类
 *
 * @author choxsu
 */
public final class RSAKit {
    /**
     * 指定加密算法为DESede
     */
    private static String ALGORITHM = "RSA/ECB/PKCS1Padding";//MD5withRSA///RSA/ECB/PKCS1Padding
    /**
     * 指定key的大小(64的整数倍,最小512位)
     */
    private static int KEYSIZE = 1024;
    /* RSA最大加密明文大小 */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /* RSA最大解密密文大小 */
    private static final int MAX_DECRYPT_BLOCK = 128;
    /* 公钥模量 */
    public static String publicModulus = null;
    /* 公钥指数 */
    public static String publicExponent = null;
    /* 私钥模量 */
    public static String privateModulus = null;
    /* 私钥指数 */
    public static String privateExponent = null;
    private static KeyFactory keyFactory = null;

    static {
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * 初始化
     */
    public static void init() {
        new RSAKit();
    }

    /**
     * 初始化
     *
     * @param keySize
     */
    public static void init(int keySize) {
        new RSAKit(keySize);
    }

    public RSAKit() {
        try {
            generateKeyPairString(KEYSIZE);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("密匙生成失败：" + e.getMessage());
        }
    }

    public RSAKit(int keySize) {
        try {
            generateKeyPairString(keySize);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("密匙生成失败：" + e.getMessage());
        }
    }

    /**
     * 生成密钥对字符串
     */
    private void generateKeyPairString(int keySize) throws Exception {
        /** RSA算法要求有一个可信任的随机数源 */
        SecureRandom sr = new SecureRandom();
        /** 为RSA算法创建一个KeyPairGenerator对象 */
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
        kpg.initialize(keySize, sr);
        /** 生成密匙对 */
        KeyPair kp = kpg.generateKeyPair();
        /** 得到公钥 */
        Key publicKey = kp.getPublic();
        /** 得到私钥 */
        Key privateKey = kp.getPrivate();
        /** 用字符串将生成的密钥写入文件 */

        String algorithm = publicKey.getAlgorithm(); // 获取算法
        KeyFactory keyFact = KeyFactory.getInstance(algorithm);
        BigInteger prime;
        BigInteger exponent;

        RSAPublicKeySpec keySpec = keyFact.getKeySpec(publicKey, RSAPublicKeySpec.class);
        prime = keySpec.getModulus();
        exponent = keySpec.getPublicExponent();
        RSAKit.publicModulus = HexKit.bytes2Hex(prime.toByteArray());
        RSAKit.publicExponent = HexKit.bytes2Hex(exponent.toByteArray());

        RSAPrivateCrtKeySpec privateKeySpec = keyFact.getKeySpec(privateKey, RSAPrivateCrtKeySpec.class);
        BigInteger privateModulus = privateKeySpec.getModulus();
        BigInteger privateExponent = privateKeySpec.getPrivateExponent();
        RSAKit.privateModulus = HexKit.bytes2Hex(privateModulus.toByteArray());
        RSAKit.privateExponent = HexKit.bytes2Hex(privateExponent.toByteArray());
    }

    /**
     * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的公钥对象。
     *
     * @param hexModulus        系数。
     * @param hexPublicExponent 专用指数。
     * @return RSA专用公钥对象。
     */
    public static RSAPublicKey getRSAPublicKey(String hexModulus, String hexPublicExponent) {
        if (isBlank(hexModulus) || isBlank(hexPublicExponent)) {
            System.out.println("hexModulus and hexPublicExponent cannot be empty. return null(RSAPublicKey).");
            return null;
        }
        byte[] modulus = null;
        byte[] publicExponent = null;
        try {
            modulus = HexKit.hex2Bytes(hexModulus);
            publicExponent = HexKit.hex2Bytes(hexPublicExponent);
        } catch (Exception ex) {
            System.out.println("hexModulus or hexPublicExponent value is invalid. return null(RSAPublicKey).");
            ex.printStackTrace();
        }
        if (modulus != null && publicExponent != null) {
            return generateRSAPublicKey(modulus, publicExponent);
        }
        return null;
    }

    /**
     * 根据给定的系数和专用指数构造一个RSA专用的公钥对象。
     *
     * @param modulus        系数。
     * @param publicExponent 专用指数。
     * @return RSA专用公钥对象。
     */
    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) {
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(modulus),
                new BigInteger(publicExponent));
        try {
            return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException ex) {
            System.out.println("RSAPublicKeySpec is unavailable.");
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            System.out.println("RSAKits#KEY_FACTORY is null, can not generate KeyFactory instance.");
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的私钥对象。
     *
     * @param hexModulus         系数。
     * @param hexPrivateExponent 专用指数。
     * @return RSA专用私钥对象。
     */
    public static RSAPrivateKey getRSAPrivateKey(String hexModulus, String hexPrivateExponent) {
        if (isBlank(hexModulus) || isBlank(hexPrivateExponent)) {
            System.out.println("hexModulus and hexPrivateExponent cannot be empty. RSAPrivateKey value is null to return.");
            return null;
        }
        byte[] modulus = null;
        byte[] privateExponent = null;
        try {
            modulus = HexKit.hex2Bytes(hexModulus);
            privateExponent = HexKit.hex2Bytes(hexPrivateExponent);
        } catch (Exception ex) {
            System.out.println("hexModulus or hexPrivateExponent value is invalid. return null(RSAPrivateKey).");
            ex.printStackTrace();
        }
        if (modulus != null && privateExponent != null) {
            return generateRSAPrivateKey(modulus, privateExponent);
        }
        return null;
    }

    /**
     * 根据给定的系数和专用指数构造一个RSA专用的私钥对象。
     *
     * @param modulus         系数。
     * @param privateExponent 专用指数。
     * @return RSA专用私钥对象。
     */
    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) {
        RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus),
                new BigInteger(privateExponent));
        try {
            return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
        } catch (InvalidKeySpecException ex) {
            System.out.println("RSAPrivateKeySpec is unavailable.");
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            System.out.println("RSAKits#KEY_FACTORY is null, can not generate KeyFactory instance.");
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 使用给定的公钥加密给定的字符串。
     *
     * @param publicKey 给定的公钥。
     * @param plaintext 字符串。
     * @return 给定字符串的密文。
     */
    public static String encryptString(Key publicKey, String plaintext) {
        if (publicKey == null || plaintext == null) {
            return null;
        }
        byte[] data = plaintext.getBytes();
        try {
            byte[] en_data = encrypt(publicKey, data);
            return new String(Base64.encodeBase64String(en_data));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 使用指定的公钥加密数据。
     *
     * @param publicKey 给定的公钥。
     * @param data      要加密的数据。
     * @return 加密后的数据。
     */

    public static byte[] encrypt(Key publicKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance(ALGORITHM);
        ci.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = ci.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = ci.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }


    /**
     * 使用给定的公钥解密给定的字符串。
     *
     * @param publicKey   给定的公钥
     * @param encryptText 密文
     * @return 原文字符串。
     */
    public static String decryptString(Key publicKey, String encryptText) {
        if (publicKey == null || isBlank(encryptText)) {
            return null;
        }
        try {
            byte[] en_data = Base64.decodeBase64(encryptText);
            byte[] data = decrypt(publicKey, en_data);
            return new String(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(String.format("\"%s\" Decryption failed. Cause: %s", encryptText, ex.getCause().getMessage()));
        }
        return null;
    }

    /**
     * 使用指定的公钥解密数据。
     *
     * @param publicKey 指定的公钥
     * @param data      要解密的数据
     * @return 原数据
     * @throws Exception
     */
    public static byte[] decrypt(Key publicKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance(ALGORITHM);
        ci.init(Cipher.DECRYPT_MODE, publicKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = ci.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = ci.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 判断非空字符串
     *
     * @param cs 待判断的CharSequence序列
     * @return 是否非空
     */
    private static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        RSAKit.init();
        String source = "需要加密的数据";
        System.out.println("RSAKit.publicModulus==" + RSAKit.publicModulus);
        System.out.println("RSAKit.publicExponent==" + RSAKit.publicExponent);
        System.out.println("RSAKit.privateModulus==" + RSAKit.privateModulus);
        System.out.println("RSAKit.privateExponent==" + RSAKit.privateExponent);

        //公钥加密，私钥解密
        PublicKey publicKey = RSAKit.getRSAPublicKey(RSAKit.publicModulus, RSAKit.publicExponent);
        System.out.println("公钥："+ Base64.encodeBase64String(publicKey.getEncoded()));
        String encript = RSAKit.encryptString(publicKey, source);
        System.out.println("加密后数据：" + encript);
        PrivateKey privateKey = RSAKit.getRSAPrivateKey(RSAKit.privateModulus, RSAKit.privateExponent);
        System.out.println("私钥:"+ Base64.encodeBase64String(privateKey.getEncoded()));
        String newSource = RSAKit.decryptString(privateKey, encript);
        System.out.println("解密后数据:" + newSource);

    }




}
