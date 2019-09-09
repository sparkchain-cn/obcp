package cn.obcp.base.utils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public abstract class RSACoder{

	public static final String KEY_ALGORITHM = "RSA";   
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";   

    private static final String PUBLIC_KEY = "RSAPublicKey";   
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";

	/**
	 * MAC算法可选以下多种算法
	 * 
	 * <pre>
	 * HmacMD5 
	 * HmacSHA1 
	 * HmacSHA256 
	 * HmacSHA384 
	 * HmacSHA512
	 * </pre>
	 */
	public static final String KEY_MAC = "HmacMD5";

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
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
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);

		return md5.digest();

	}

	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {

		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);

		return sha.digest();

	}

	/**
	 * 初始化HMAC密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);

		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {

		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);

		return mac.doFinal(data);

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

        return encryptBASE64(signature.sign());   
    }   

    /** *
     * 校验数字签名 
     * @param data 
     *            加密数据 
     * @param publicKey 
     *            公钥 
     * @param sign 
     *            数字签名 
     *   
     * @return 校验成功返回true 失败返回false 
     * @throws Exception 
     *   
     */ 
    public static boolean verify(byte[] data, String publicKey, String sign)   
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
        return signature.verify(decryptBASE64(sign));   
    }   

    /** 
     * 解密<br> 
     * 用私钥解密 http://www.5a520.cn http://www.feng123.com 
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

        return cipher.doFinal(data);   
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

        return cipher.doFinal(data);   
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
     * 取得私钥 
     * @param keyMap 
     * @return 
     * @throws Exception 
     */ 
    public static String getPrivateKey(Map<String, Object> keyMap)   
            throws Exception {   
        Key key = (Key) keyMap.get(PRIVATE_KEY);   

        return encryptBASE64(key.getEncoded());   
    }   

    /**
     * 取得公钥 
     * @param keyMap 
     * @return 
     * @throws Exception 
     */ 
    public static String getPublicKey(Map<String, Object> keyMap)   
            throws Exception {   
        Key key = (Key) keyMap.get(PUBLIC_KEY);   

        return encryptBASE64(key.getEncoded());   
    }   

    /** 
     * 初始化密钥 
     *   
     * @return 
     * @throws Exception 
     */ 
    public static Map<String, Object> initKey() throws Exception {   
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);   
        keyPairGen.initialize(1024);   
        KeyPair keyPair = keyPairGen.generateKeyPair();   
        // 公钥   
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();   

        // 私钥   
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   

        Map<String, Object> keyMap = new HashMap<String, Object>(2);   

        keyMap.put(PUBLIC_KEY, publicKey);   
        keyMap.put(PRIVATE_KEY, privateKey);   
        return keyMap;   
    }
    
    public static void main(String[] args) throws Exception {
    	Map<String, Object> map = RSACoder.initKey();
		System.out.println("RSAPublicKey:"+RSACoder.getPublicKey(map));
		System.out.println("RSAPrivateKey:"+RSACoder.getPrivateKey(map));
		
    	String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNcZygqX22xUTqqMphpXGv70jCKjJ1fKof20Jb\r\n2TsIBXyerjXPwh5yDKa5qHuln80LyR40Z35anSOlfvdydsxwK/vm2pE8tq2rQropRdDhNNccs8BU\r\nwKXmtJ+poOU+1EUjAR9T3VbcFfISWXLMPG+7QCjDdIynzOYVS6JiRb1J5wIDAQAB";
		String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAI1xnKCpfbbFROqoymGlca/vSMIq\r\n" + 
				"MnV8qh/bQlvZOwgFfJ6uNc/CHnIMprmoe6WfzQvJHjRnflqdI6V+93J2zHAr++bakTy2ratCuilF\r\n" + 
				"0OE01xyzwFTApea0n6mg5T7URSMBH1PdVtwV8hJZcsw8b7tAKMN0jKfM5hVLomJFvUnnAgMBAAEC\r\n" + 
				"gYEAgkm0zQlTE5iC8uSoB2ouXrE7hB76e95plw6RAaoIBkuHj2NJjiZxhPzCVyIrQ1T/u2t2ty7d\r\n" + 
				"5Pn6a+eYZ4emqA4mjHaX7safZeHtuH3jePqJL+/H8WfeIWbQP4nh61UCVKW0W03wlwrNfw/8xktM\r\n" + 
				"+/u0yyMqLpaJJuIOCScfKYECQQDDzqiVfsM8clf2JxJHR6HOYJ0kU4qfh3DBI2JjMRBuuc7Z2Nhc\r\n" + 
				"YKSPTnOraEuY3Yq52kZmMPuUTHYZsmNHL+RdAkEAuOy9gGw3zeYwFD5M4t7HLXWSYnl4+x/qBbrx\r\n" + 
				"+lJ0Ujd+DQv9svwqp2RGAohWrE9TbkjCkbGzI4iaMcfqr2dDEwJARcEyV4eAH+GQKnXC0jfXkcDj\r\n" + 
				"WlSUlr5WUce8Ph/1fayd3wecFFStawwEsvSFseCCkjELUU5z4WHX1NGzAmIyVQJAdSXcYrernm89\r\n" + 
				"dAWHlY1FpoG9y05imsu76Adm9Yo4kEEBRGidPj7LEqHOTBpstdnG462KPNvSvSbzb6fS/20uKwJB\r\n" + 
				"ALWlnW5g6wRwfuHvHI+4BHYU6UWi1jWskhXb2/RyQDHudlKsJLyu92Rj4Q5m+HbCmYQbQfHgVEj6\r\n" + 
				"MP8xxw++q3w=";
		byte[] pwdbyte = RSACoder.encryptByPublicKey("123456".getBytes(), publicKey);
//		String pwd = (new BASE64Encoder()).encodeBuffer(pwdbyte);
//		System.out.println( pwd);
		//加密后的密码
		String pwd = "cFdM8EddiZztbOO/Qoa09RqugMQTqe+gZetPFeUSTs4rSbMgQMevB2D06ScPAmW3ldiTWowjaf3ZtbZe73pCx6juexRCqvIG2/0PG3r75m1Vj3ij198pU3eqrKF2JVo/muF7iRZyDvGS/3MFJ2KqT8IxQvuADMbsQuHLvpe+wuk=";
		//解密后的值
		System.out.println(new String(RSACoder.decryptByPrivateKey((new BASE64Decoder()).decodeBuffer(pwd),privateKey)));
		System.out.println(Pattern.compile("^(?=.*[[A-Za-z0-9]\\\\p{Punct}].*).{6,16}$").matcher(new String(RSACoder.decryptByPrivateKey((new BASE64Decoder()).decodeBuffer(pwd),privateKey))).matches());
	}
} 

