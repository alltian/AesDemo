package com.example.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
	private final static String HEX = "0123456789ABCDEF";
	/**
	 * ����
	 * @param key ��Կ
	 * @param ciphertext ����
	 * @return ����
	 * @throws Exception
	 */
	public static String decode(String key, String ciphertext) throws Exception {
		byte[] bs = parseHexStr2Byte(ciphertext);
		IvParameterSpec ivSpec = new IvParameterSpec(HEX.getBytes());
		SecretKeySpec secretKeySpec = createKey(key);
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
		return new String(c.doFinal(bs), "UTF-8");
	}

	/**
	 * ����
	 * @param key ��Կ
	 * @param cleartext ����
	 * @return ����
	 * @throws Exception
	 */
	public static String encode(String key, String cleartext) throws Exception {
		SecretKeySpec secretKeySpec = createKey(key);
		IvParameterSpec ivSpec = new IvParameterSpec(HEX.getBytes());
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
		String result = parseByte2HexStr(c.doFinal(cleartext.getBytes("UTF-8")));

		return result;
	}

	private static SecretKeySpec createKey(String key) {
		byte[] data = null;
		if (key == null) {
			key = "";
		}
		StringBuffer sb = new StringBuffer(16);
		sb.append(key);
		while (sb.length() < 16) {
			sb.append("0");
		}
		if (sb.length() > 16) {
			sb.setLength(16);
		}
		try {
			data = sb.toString().getBytes("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new SecretKeySpec(data, "AES");
	}

	/**
	 * ��������ת����16����
	 * 
	 * @param buf byte[]
	 * @return String
	 */
	private static String parseByte2HexStr(byte[] buf) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0XFF);
			if (hex.length() == 1) {
//				hex = '0' + hex;
				sb.append("0");
			}
			sb.append(hex);
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * ��16����ת��Ϊ������
	 * 
	 * @param hexStr String
	 * @return byte[]
	 */
	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr == null || hexStr.length() == 0) {
			return null;
		}
		int len = hexStr.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++) {
			// int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1),16);
			// int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 +2),16);
			// result[i] = (byte) (high * 16 + low);
			result[i] = (byte) (Integer.parseInt(hexStr.substring(2 * i, 2 * i + 2), 16) & 0XFF);
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		String key = "liujigang";
		String text = "1���̸����̸����̸����̸����̸����̸����̸����̸����̸����̸����̸�2���̸�3";

		String ret = encode(key, text);
		System.out.println("����:" + ret);

		String sb = decode(key, ret);
		System.out.println("����:" + sb);
	}
}
