package cn.obcp.base.utils;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {

	/**
	 * 获取密钥
	 *
	 * @return 密钥
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] initKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128); // 192,256
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey.getEncoded();
	}

	/**
	 * AES加密
	 *
	 * @param data
	 *            要加密的数据
	 * @param key
	 *            加密所使用的密钥
	 * @return 加密后的数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}

	/**
	 * AES解密
	 *
	 * @param data
	 *            要解密的数据
	 * @param key
	 *            解密所使用的密钥
	 * @return 解密后的数据, 即源数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}
	//
	// /**
	// * Convert byte[] to hex string
	// *
	// * @param src
	// * byte[] data
	// * @return hex string
	// */
	// public static String bytesToHexString(byte[] src) {
	// StringBuilder stringBuilder = new StringBuilder("");
	// if (src == null || src.length <= 0) {
	// return null;
	// }
	// for (int i = 0; i < src.length; i++) {
	// int v = src[i] & 0xFF;
	// String hv = Integer.toHexString(v);
	// if (hv.length() < 2) {
	// stringBuilder.append(0);
	// }
	// stringBuilder.append(hv);
	// }
	// return stringBuilder.toString();
	// }

	/*
	 * Convert byte[] to hex
	 * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
	 * 
	 * @param src byte[] data
	 * 
	 * @return hex string
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	public static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 测试AES加密-->对称加密
	 */
	private static final String DATA = "asiatravel";
//
//	public static void main(String[] args) throws Exception {
//
//		String string="dddessdd";
//	String string2= 	bytesToHexString(string.getBytes());
//		/**
//		 * 密钥
//		 */
//		// byte[] aesKey = AESUtils.initKey();//.getBytes();// AESUtils.initKey();
//		// System.out.println(DATA + "AES key: " + bytesToHexString(aesKey));
//		// byte[] aesKey = "123ewweee".getBytes();
//
//		// df79bccc71a2574dc0e2b23091b006b8
//
//		byte[] aesKey = AESUtils.hexStringToBytes("df79bccc71a2574dc0e2b23091b006b8");// .getBytes();//
//																						// AESUtils.initKey();
//		System.out.println(DATA + "AES key: " + bytesToHexString(aesKey));
//		// byte[] aesKey = "123ewweee".getBytes();
//
//		/**
//		 * 加密后的数据
//		 */
//		byte[] encryptResult = encrypt(DATA.getBytes(), aesKey);
//		System.out.println(DATA + "AES 加密: " + bytesToHexString(encryptResult));
//		/**
//		 * 解密后的数据
//		 */
//		byte[] decryptResult = AESUtils.decrypt(hexStringToBytes("9717318229d168a1aa4a0d9912e67df2"), aesKey);
//		System.out.println(DATA + "AES 解密: " + new String(decryptResult));
//
//	}

}