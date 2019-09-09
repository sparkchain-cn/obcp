package cn.obcp.base.utils;

import java.util.Random;
import org.apache.commons.codec.binary.Hex;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * MD5工具类，加盐
 * 
 * @author daniel
 * @email 576699909@qq.com
 * @time 2016-6-11 下午7:57:36
 */
public class MD5Utils {

	/**
	 * 普通MD5
	 * 
	 * @author daniel
	 * @time 2016-6-11 下午8:00:28
	 * @param inStr
	 * @return
	 */
	public static String MD5(String input) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return "check jdk";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = input.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}

	/**
	 * 加盐MD5
	 * 
	 * @author daniel
	 * @time 2016-6-11 下午8:45:04
	 * @param password
	 * @return
	 */
	public static String generate(String password) {
		Random r = new Random();
		StringBuilder sb = new StringBuilder(16);
		sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
		int len = sb.length();
		if (len < 16) {
			for (int i = 0; i < 16 - len; i++) {
				sb.append("0");
			}
		}
		String salt = sb.toString();
		password = md5Hex(password + salt);
		char[] cs = new char[48];
		for (int i = 0; i < 48; i += 3) {
			cs[i] = password.charAt(i / 3 * 2);
			char c = salt.charAt(i / 3);
			cs[i + 1] = c;
			cs[i + 2] = password.charAt(i / 3 * 2 + 1);
		}
		return new String(cs);
	}

	/**
	 * 校验加盐后是否和原文一致
	 * 
	 * @author daniel
	 * @time 2016-6-11 下午8:45:39
	 * @param password
	 * @param md5
	 * @return
	 */
	public static boolean verify(String password, String md5) {
		char[] cs1 = new char[32];
		char[] cs2 = new char[16];
		for (int i = 0; i < 48; i += 3) {
			cs1[i / 3 * 2] = md5.charAt(i);
			cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
			cs2[i / 3] = md5.charAt(i + 1);
		}
		String salt = new String(cs2);
		return md5Hex(password + salt).equals(new String(cs1));
	}

	/**
	 * 获取十六进制字符串形式的MD5摘要
	 */
	private static String md5Hex(String src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bs = md5.digest(src.getBytes());
			return new String(new Hex().encode(bs));
		} catch (Exception e) {
			return null;
		}
	}
	
	   /**
     * 获取一个文件的md5值(可处理大文件)
     *
     * @return md5 value
     */
    public static String getMD5(File file) {
        FileInputStream fileInputStream = null;
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(MD5.digest()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


//	// 测试主函数
//	public static void main(String args[]) {
//		// 原文
//		String plaintext = "DingSai";
//		// plaintext = "123456";
//		System.out.println("原始：" + plaintext);
//		System.out.println("普通MD5后：" + MD5Utils.MD5(plaintext));
//
//		// 获取加盐后的MD5值
//		String ciphertext = MD5Utils.generate(plaintext);
//		System.out.println("加盐后MD5：" + ciphertext);
//		System.out.println("是否是同一字符串:" + MD5Utils.verify(plaintext, ciphertext));
//		/**
//		 * 其中某次DingSai字符串的MD5值
//		 */
//		String[] tempSalt = { "c4d980d6905a646d27c0c437b1f046d4207aa2396df6af86",
//				"66db82d9da2e35c95416471a147d12e46925d38e1185c043",
//				"61a718e4c15d914504a41d95230087a51816632183732b5a" };
//
//		for (String temp : tempSalt) {
//			System.out.println("是否是同一字符串:" + MD5Utils.verify(plaintext, temp));
//		}
//
//	}

}
