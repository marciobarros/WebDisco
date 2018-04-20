package br.unirio.dsw.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Classe utilitária de criptografia
 * 
 * @author marcio.barros
 */
public class CryptoUtils
{
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
	 * Escreve a versão hexaddecimal de uma string
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
	 * Gera um token para troca de senha
	 */
	public static String createToken()
	{
		StringBuilder sb = new StringBuilder();
		Random r = createSecureRandom();
		
		for (int i = 0; i < 256; i++)
		{
			char c = (char) ('A' + r.nextInt(26));
			sb.append(c);
		}
		
		return sb.toString();
	}

	/**
	 * Cria um gerador de números aleatórios seguro
	 */
	private static Random createSecureRandom()
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