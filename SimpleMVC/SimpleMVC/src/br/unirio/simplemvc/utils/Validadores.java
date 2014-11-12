package br.unirio.simplemvc.utils;

import org.joda.time.DateTime;

/**
 * Utility class for checking values of different kinds
 * 
 * @author marcio.barros
 */
public class Validadores
{
	/**
	 * Remove everything but the numbers from a string
	 */
	private static String separaNumeros(String source)
	{
		String result = "";

		for (int i = 0; i < source.length(); i++)
		{
			char c = source.charAt(i);

			if (Character.isDigit(c))
				result += c;
		}

		return result;
	}

	/**
	 * Checks whether a CPF value is valid
	 */
	public static boolean validaCPF(String source)
	{
		String cpf = separaNumeros(source);

		if (cpf.length() != 11)
			return false;

		int d1 = 0;
		int d2 = 0;
		
		int digito = cpf.charAt(0) - '0';
		boolean todosIguais = true;

		for (int i = 0; i < 9; i++)
		{
			int digitoCPF = cpf.charAt(i) - '0';
			todosIguais &= (digitoCPF == digito);

			d1 = d1 + (10 - i) * digitoCPF;
			d2 = d2 + (11 - i) * digitoCPF;
		}

		if (todosIguais)
			return false;
		
		int resto = (d1 % 11);
		int digito1 = (resto < 2) ? 0 : 11 - resto;

		d2 += 2 * digito1;
		resto = (d2 % 11);
		int digito2 = (resto < 2) ? 0 : 11 - resto;

		String digitos = String.valueOf(digito1) + String.valueOf(digito2);
		return cpf.substring(cpf.length() - 2).compareTo(digitos) == 0;
	}

	/**
	 * Checks whether a CNPJ value is valid
	 */
	public static boolean validaCNPJ(String source)
	{
		String cnpj = separaNumeros(source);

		if (cnpj.length() != 14)
			return false;

		int multiplicador1 = 5;
		int multiplicador2 = 6;
		
		int soma1 = 0;
		int soma2 = 0;

		int digito = cnpj.charAt(0) - '0';
		boolean todosIguais = true;

		for (int i = 0; i < 12; i++)
		{
			int digitoCNPJ = cnpj.charAt(i) - '0';
			todosIguais &= (digitoCNPJ == digito);

			soma1 += digitoCNPJ * multiplicador1;
			soma2 += digitoCNPJ * multiplicador2;

			multiplicador2 = (multiplicador2 == 2) ? 9 : multiplicador2 - 1;
			multiplicador1 = (multiplicador1 == 2) ? 9 : multiplicador1 - 1;
		}

		if (todosIguais)
			return false;

		soma2 += (cnpj.charAt(12) - '0') * multiplicador2;
		int digito1 = (soma1 % 11 < 2) ? 0 : (11 - soma1 % 11);
		int digito2 = (soma2 % 11 < 2) ? 0 : (11 - soma2 % 11);
		return (((cnpj.charAt(12) - '0') == digito1) && ((cnpj.charAt(13) - '0') == digito2));
	}

	/**
	 * Checks whether a date value in Brazilian format is valid
	 */
	public static DateTime validaData(String source)
	{
		String[] tokens = source.split("/");
		
		if (tokens.length != 3)
			return null;
		
		int dia = Integer.parseInt(tokens[0]);
		int mes = Integer.parseInt(tokens[1]);
		int ano = Integer.parseInt(tokens[2]);

		if (ano < 1900)
			return null;
		
		if (mes < 1 || mes > 12)
			return null;

		if (dia < 1 || dia > DateUtils.getMonthDays(mes, ano))
			return null;

		return new DateTime(ano, mes, dia, 0, 0);
	}
}