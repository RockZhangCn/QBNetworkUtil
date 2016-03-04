package com.tencent.rocksnzhang.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionDecryption {

    private static String strDefaultKey = "QabC-+50";
    
    private static byte[] ivs = {1,2,3,4,5,6,7,8};

    /** 加密工具 */
    private Cipher encryptCipher = null;

    /** 解密工具 */
    private Cipher decryptCipher = null;

    private static final String DES_CBC     = "DES/CBC/PKCS5Padding";
    private static final String DES_ECB     = "DES/ECB/PKCS5Padding";

    /**
     * 默认构造方法，使用默认密钥
     *
     * @throws Exception
     */
    public EncryptionDecryption() throws Exception {
		  Key key = getKey(strDefaultKey.getBytes("UTF-8"));
		
		  encryptCipher = Cipher.getInstance(DES_ECB);
		  encryptCipher.init(Cipher.ENCRYPT_MODE, key);
		  
		  decryptCipher = Cipher.getInstance(DES_ECB);
		  decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    /**
     * 加密字节数组
     *
     * @param arrB
     *            需加密的字节数组
     * @return 加密后的字节数组
     * @throws Exception
     */
    public byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }

    /**
     * 解密字节数组
     *
     * @param arrB
     *            需解密的字节数组
     * @return 解密后的字节数组
     * @throws Exception
     */
    public byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }
    
    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     *
     * @param arrBTmp
     *            构成该字符串的字节数组
     * @return 生成的密钥
     * @throws Exception
     */
    private Key getKey(byte[] arrBTmp) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];

        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        // 生成密钥
        Key key = new SecretKeySpec(arrB, "DES");
        byte[] keyByte = key.getEncoded();
        int tmp = keyByte.length;

        return key;
    }


}