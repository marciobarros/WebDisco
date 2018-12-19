package br.unirio.dsw.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe responsável pela validação de diversos tipos de campos
 * 
 * @author marciobarros
 */
public class ValidationUtils
{
	/**
	 * Valida um campo do tipo e-mail
	 */
	public static boolean validEmail(String email)
	{
		String EMAIL_PATTERN = "^[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email.toUpperCase());
		return matcher.matches();
	}

	/**
	 * Verifica se uma senha é aceitável, checando se ela tem pelo menos 8 caracteres, uma letra e um número
	 */
	public static boolean validPassword(String password)
	{
		return (password.length() >= 8) && password.matches(".*[a-zA-Z].*") && password.matches(".*[0-9].*");
	}
}