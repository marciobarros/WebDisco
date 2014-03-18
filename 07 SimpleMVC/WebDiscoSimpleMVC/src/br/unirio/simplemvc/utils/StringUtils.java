package br.unirio.simplemvc.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling strings
 * 
 * @author marcio.barros
 */
public class StringUtils
{
	/**
	 * Builds a comma-separated string from a set of iterable values
	 */
	public static String listToString(Iterable<String> dados)
	{
		String result = "";
		
		for (String s : dados)
		{
			if (result.length() > 0)
				result += ", ";
			
			result += s;
		}
		
		return result;
	}

	/**
	 * Builds a comma-separated string from a list of values
	 */
	public static String listToString(List<String> dados)
	{
		return listToString((Iterable<String>)dados);
	}

	/**
	 * Builds a comma-separated string from a set of iterable values using a separator
	 */
	public static void stringToList(String sDados, List<String> dados, String separador)
	{
		sDados = sDados.trim();
		dados.clear();
		
		if (sDados.length() > 0)
		{
			String[] split = sDados.split(separador);
			
			for (String s : split)
				dados.add(s.trim());
		}
	}

	/**
	 * Builds a semicolon-separated string from an array of strings
	 */
	public static String listToString(String[] data)
	{
		String s = data[0];
		
		for (int i = 1; i < data.length; i++)
			s += ";" + data[i];
		
		return s;
	}

	/**
	 * Creates a list of strings from a comma-separated string
	 */
	public static List<String> stringToList(String sDados)
	{
		List<String> dados = new ArrayList<String>();
		stringToList(sDados, dados, ",");
		return dados;
	}

	/**
	 * Returns a list of string from a separated string, given the separator
	 */
	public static List<String> stringToList(String sDados, String separador)
	{
		List<String> dados = new ArrayList<String>();
		stringToList(sDados, dados, separador);
		return dados;
	}

	/**
	 * Populates a list of string from a comma-separated string
	 */
	public static void stringToList(String sDados, List<String> dados)
	{
		sDados = sDados.trim();
		dados.clear();
		
		if (sDados.length() > 0)
		{
			String[] split = sDados.split(",");
			
			for (String s : split)
				dados.add(s.trim());
		}
	}

	/**
	 * Returns a plural or singular name according to a counter
	 */
	public static String plural(int contador, String singular, String plural)
	{
		return (contador == 1) ? singular: plural;			
	}
	
	/**
	 * Exchange accents to HTML codes in a string
	 */
	public static String escapeAccents(String s)
	{
		String[] acentos = 
		{
			"á",	"&aacute;",
			"é",	"&eacute;",
			"í",	"&iacute;",
			"ó",	"&oacute;",
			"ú",	"&uacute;",
			"Á",	"&Aacute;",
			"É",	"&Eacute;",
			"Í",	"&Iacute;",
			"Ó",	"&Oacute;",
			"Ú",	"&Uacute;",

			"à",	"&acrase;",
			"è",	"&ecrase;",
			"ì",	"&icrase;",
			"ò",	"&ocrase;",
			"ù",	"&ucrase;",
			"À",	"&Acrase;",
			"È",	"&Ecrase;",
			"Ì",	"&Icrase;",
			"Ò",	"&Ocrase;",
			"Ù",	"&Ucrase;",

			"â",	"&acirc;",
			"ê",	"&ecirc;",
			"î",	"&icirc;",
			"ô",	"&ocirc;",
			"û",	"&ucirc;",
			"Â",	"&Acirc;",
			"Ê",	"&Ecirc;",
			"Î",	"&Icirc;",
			"Ô",	"&Ocirc;",
			"Û",	"&Ucirc;",

			"ã",	"&atilde;",
			"õ",	"&otilde;",
			"ñ",	"&ntilde;",
			"Ã",	"&Atilde;",
			"Õ",	"&Otilde;",
			"Ñ",	"&Ntilde;",

			"ç",	"&ccedil;",
			"Ç",	"&Ccedil;"
		};
		
		for (int i = 0; i < acentos.length; i += 2)
			s.replaceAll(acentos[i], acentos[i+1]);
		
		return s;
	}

	/**
	 * Creates a Brazilian style number formatter
	 */
	public static NumberFormat createNumberFormat(int decimals)
	{
		String mask = "#,##0";
		
		if (decimals > 0)
			mask += '.';
		
		while (decimals > 0)
		{
			mask += '0';
			decimals--;
		}
		
		DecimalFormatSymbols custom = new DecimalFormatSymbols();
		custom.setDecimalSeparator(',');
		custom.setGroupingSeparator('.');
		return new DecimalFormat(mask, custom);
	}
}