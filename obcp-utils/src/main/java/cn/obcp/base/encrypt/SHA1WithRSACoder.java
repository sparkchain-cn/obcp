package cn.obcp.base.encrypt;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import cn.obcp.base.utils.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SHA1WithRSACoder {

    private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";



    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
//		return Base64.getDecoder().decode(key);
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
//		return Base64.getEncoder().encodeToString(key);
        return (new BASE64Encoder()).encodeBuffer(key);
    }




    /**
     * 用私钥对信息生成数字签名
     * @param data   加密数据
     * @param privateKey  私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = decryptBASE64(privateKey);

        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        byte[] signByte = signature.sign();
//        System.out.println(encryptBASE64(signByte));
        return encryptBASE64(signByte);
    }

    /** *
     * 校验数字签名
     * @param data   加密数据
     * @param publicKey   公钥
     * @param tokenByte  数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     *
     */
    public static boolean verify(byte[] data, String publicKey, byte[] tokenByte)
            throws Exception {
        // 解密由base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(tokenByte);
    }

    /**
     * 解密<br>
     * 用私钥解密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        int inputLength = data.length;
		// 最大解密字节数，超出最大字节数需要分组解密
		int MAX_ENCRYPT_BLOCK = 128;
		// 标识
		int offSet = 0;
		byte[] result = {}; 
		byte[] cache = {};
		while (inputLength - offSet > 0) {
			if (inputLength - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				offSet += MAX_ENCRYPT_BLOCK;
			} else {
				cache = cipher.doFinal(data, offSet, inputLength - offSet);
				offSet = inputLength;
			}
			result = Arrays.copyOf(result, result.length + cache.length);
			System.arraycopy(cache, 0, result, result.length - cache.length, cache.length);
		}
        return result;
    }


    /**
     * 解密<br>
     * 用私钥解密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		int inputLength = data.length;
		// 最大加密字节数，超出最大字节数需要分组加密
		int MAX_ENCRYPT_BLOCK = 117;
		// 标识
		int offSet = 0;
		byte[] result = {}; 
		byte[] cache = {};
		while (inputLength - offSet > 0) {
			if (inputLength - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				offSet += MAX_ENCRYPT_BLOCK;
			} else {
				cache = cipher.doFinal(data, offSet, inputLength - offSet);
				offSet = inputLength;
			}
			result = Arrays.copyOf(result, result.length + cache.length);
			System.arraycopy(cache, 0, result, result.length - cache.length, cache.length);
		}
        return result;
    }
    /**
     * 加密<br>
     * 用私钥加密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }


    /**
     * 初始化密钥
     * @return
     * @throws Exception
     */
    public static Map<String, String> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, String> keyMap = new HashMap<String, String>(2);
        keyMap.put("RSAPublicKey", encryptBASE64(publicKey.getEncoded()));
        keyMap.put("RSAPrivateKey", encryptBASE64(privateKey.getEncoded()));
        return keyMap;
    }

    /**
     *  将token签名时字节中的负数转为了正数，后台需要进行转换
     * @param data
     * @return
     */
    public static byte[] hexStringToByteArray(String data) {
        int k = 0;
        byte[] results = new byte[data.length() / 2];
        for (int i = 0; i + 1 < data.length(); i += 2, k++) {
            results[k] = (byte) (Character.digit(data.charAt(i), 16) << 4);
            results[k] += (byte) (Character.digit(data.charAt(i + 1), 16));
        }
        return results;
    }

    public static String bytes2String(byte[] bytes) {
        StringBuilder string = new StringBuilder();
        for (byte b : bytes) {
            String hexString = Integer.toHexString(0x00FF & b);
            string.append(hexString.length() == 1 ? "0" + hexString : hexString);
        }
        return string.toString();
    }

    /**
     * 检查是否合法RSA公钥
     * @param key
     * @return
     */
    public static boolean checkPublicKey(String key) {
        if (StringUtils.isNullOrEmpty(key)) {
            return false;
        }
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decryptBASE64(key));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            keyFactory.generatePublic(keySpec);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 检查是否合法RSA私钥
     * @param key
     * @return
     */
    public static boolean checkPrivateKey(String key) {
    	 if (StringUtils.isNullOrEmpty(key)) {
             return false;
         }
         try {
        	 PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(decryptBASE64(key));
             KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
             keyFactory.generatePrivate(pkcs8KeySpec);
             return true;
         } catch (Exception e) {
             return false;
         }
    }
} 

