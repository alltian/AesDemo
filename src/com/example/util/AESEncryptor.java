package com.example.util;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES ���ܽ��� Android�汾
 * 
 * @author liujigang
 * 
 */
public class AESEncryptor {
	private final static String HEX = "0123456789ABCDEF";

	/**
	 * ����
	 * 
	 * @param key
	 *            ��Կ
	 * @param plaintext
	 *            ����
	 * @return ����
	 * @throws Exception
	 */
	public static String encrypt(String key, String plaintext) throws Exception {
		byte[] rawKey = getRawKey(key.getBytes());
		byte[] result = encrypt(rawKey, plaintext.getBytes());
		return toHex(result);
	}

	/**
	 * ����
	 * 
	 * @param key
	 *            ��Կ
	 * @param ciphertext
	 *            ����
	 * @return ����
	 * @throws Exception
	 */
	public static String decrypt(String key, String ciphertext)
			throws Exception {
		byte[] rawKey = getRawKey(key.getBytes());
		byte[] enc = toByte(ciphertext);
		byte[] result = decrypt(rawKey, enc);
		return new String(result);
	}

	private static byte[] getRawKey(byte[] seed) throws Exception {
		byte[] pwd = new byte[16];
		Arrays.copyOfRange(pwd, 0, 17);
		SecretKeySpec skey = new SecretKeySpec(pwd, "AES");
		byte[] raw = skey.getEncoded();
		return raw;
	}

	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted)
			throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	public static String toHex(String txt) {
		return toHex(txt.getBytes());
	}

	public static String fromHex(String hex) {
		return new String(toByte(hex));
	}

	public static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
					16).byteValue();
		return result;
	}

	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}

	public static void main(String[] args) throws Exception {
		String encrypt = encrypt("liujigang", "abc");
		System.out.println("����" + encrypt);
		System.out.println("����" + decrypt("liujigang", encrypt));
	}
}
