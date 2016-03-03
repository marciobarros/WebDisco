package br.unirio.simplemvc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class for cryptography purposes
 * 
 * @author marcio.barros
 */
public class Crypto
{
	/**
	 * Default cypher key 
	 */
	private static final String DEFAULT_CYPHER_KEY = "ahg187#ajsh7ja.y";
	
	/**
	 * Internal key used to encrypt and decrypt data
	 */
	private static String key = null;

	/**
	 * Changes the encryption key, checking whether it has 16 bytes
	 */
	public static void setKey(String aKey)
	{
		key = aKey;

		if (key.length() > 16)
			key = key.substring(0, 16);

		while (key.length() < 16)
			key += "a";
	}

	/**
	 * Loads the encryption key from the configuration property file
	 */
	private static void loadKey()
	{
		Properties config = new Properties();

		try
		{
			InputStream is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("configuration.properties");

			if (is != null)
			{
				config.load(is);
				setKey(config.getProperty("cypherKey", DEFAULT_CYPHER_KEY));
			}
		} catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Returns the reversible encrypted version of an string
	 */
	public static String reversibleEncryption(String value)
	{
		if (key == null)
			loadKey();

		try
		{
			SecretKeySpec skeySpec = getSecretKeySpecifications();
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(value.getBytes());
			return byteArrayToString(encrypted);

		} catch (NoSuchAlgorithmException ex)
		{
			System.out.println(ex.getMessage());
		} catch (IllegalBlockSizeException ex)
		{
			System.out.println(ex.getMessage());
		} catch (BadPaddingException ex)
		{
			System.out.println(ex.getMessage());
		} catch (InvalidKeyException ex)
		{
			System.out.println(ex.getMessage());
		} catch (NoSuchPaddingException ex)
		{
			System.out.println(ex.getMessage());
		}
		return null;
	}

	/**
	 * Gets the specification for the secret encryption key
	 */
	private static SecretKeySpec getSecretKeySpecifications()
	{
		byte[] raw = new byte[16];

		for (int i = 0; i < 16; i++)
			raw[i] = (byte) key.charAt(i);

		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		return skeySpec;
	}

	/**
	 * Writes a byte-array as a hexadecimal string
	 */
	private static String byteArrayToString(byte[] encrypted)
	{
		String hex = "0123456789ABCDEF";
		String result = "";

		for (int i = 0; i < encrypted.length; i++)
		{
			int value = encrypted[i];
			if (value < 0)
				value = 256 + value;
			result += hex.charAt(value / 16);
			result += hex.charAt(value % 16);
		}

		return result;
	}

	/**
	 * Returns the decrypted version of a string
	 */
	public static String decrypt(String sdata)
	{
		if (key == null)
			loadKey();

		try
		{
			byte[] value = stringtoByteArray(sdata);
			SecretKeySpec skeySpec = getSecretKeySpecifications();
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] original = cipher.doFinal(value);
			return new String(original);

		} catch (IllegalBlockSizeException ex)
		{
			System.out.println(ex.getMessage());
		} catch (BadPaddingException ex)
		{
			System.out.println(ex.getMessage());
		} catch (InvalidKeyException ex)
		{
			System.out.println(ex.getMessage());
		} catch (NoSuchAlgorithmException ex)
		{
			System.out.println(ex.getMessage());
		} catch (NoSuchPaddingException ex)
		{
			System.out.println(ex.getMessage());
		}

		return null;
	}

	/**
	 * Converts a string to a byte-array
	 */
	private static byte[] stringtoByteArray(String s)
	{
		if (s.length() % 2 != 0)
			return null;

		int len = s.length() / 2;
		byte[] data = new byte[len];

		for (int i = 0; i < len; i++)
		{
			int value = charToNumber(s.charAt(i * 2)) * 16
					+ charToNumber(s.charAt(i * 2 + 1));
			if (value > 127)
				value = value - 256;
			data[i] = (byte) value;
		}

		return data;
	}

	/**
	 * Converts a hexadecimal char to its number
	 */
	private static int charToNumber(char c)
	{
		if (c >= '0' && c <= '9')
			return c - '0';

		if (c >= 'A' && c <= 'F')
			return 10 + c - 'A';

		return 0;
	}

	/**
	 * Gera um hash irreversível para uma string
	 */
	public static String hash(String value)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(value.getBytes());
			return byteArrayToString(md.digest());
			
		} catch(NoSuchAlgorithmException e)
		{
			System.out.println(e.getMessage());
		}
		
		return null;
	}

	/**
	 * Creates a secure randomizer, backed-up by a single random
	 */
	public static Random createSecureRandom()
	{
		try
		{
			return SecureRandom.getInstance("SHA1PRNG");
		}
		catch(NoSuchAlgorithmException e)
		{
			return new Random();
		}
	}
}