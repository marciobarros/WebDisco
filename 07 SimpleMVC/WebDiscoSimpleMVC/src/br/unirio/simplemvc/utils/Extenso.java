package br.unirio.simplemvc.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for presenting number values per extense
 * 
 * @author marcio.barros
 */
public class Extenso
{
	private static final String[] NUMEROS = 
	{
		"zero", "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove", "dez", "onze", "doze", "treze", "quatorze", "quinze", "dezesseis", "dezessete", "dezoito", "dezenove"
	};

	private static final String[] DEZENAS = 
	{
		"vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa"
	};

	private static final String[] CENTENAS =
	{
		"cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos", "setecentos", "oitocentos", "novecentos"
	};

	private static final String[] MILHAR_SINGULAR =
	{
		"mil", "milhão", "bilhão"
	};

	private static final String[] MILHAR_PLURAL =
	{
		"mil", "milhões", "bilhões"
	};

	/**
	 * Creates an string with the representation of a number in extense format
	 */
	public static String converte(int numero)
	{
		// Trata o caso especial do zero
		if (numero == 0)
			return NUMEROS[0];
		
		// Trata as potências de milhar (mil, milhão e bilhão)
		List<String> partes = new ArrayList<String>();
		
		for (int potencia = MILHAR_SINGULAR.length; potencia > 0; potencia--)
		{
			int milhar = (int) Math.pow(1000, potencia);
			
			if (numero >= milhar)
			{
				partes.add(apresentaMilhar(numero / milhar, MILHAR_SINGULAR[potencia-1], MILHAR_PLURAL[potencia-1]));
				numero = numero % milhar;
			}
		}

		// Trata os números abaixo de mil
		String ultimaParte = apresentaMilhar(numero, "", "");
		
		if (ultimaParte.length() > 0)
			partes.add(ultimaParte);
		
		// Junta todas as partes do número
		StringBuffer sb = new StringBuffer();
		sb.append(partes.get(0));
		
		for (int i = 1; i < partes.size()-1; i++)
			sb.append(", " + partes.get(i));
		
		if (partes.size() > 1)
			sb.append(" e " + partes.get(partes.size()-1));
		
		return sb.toString();
	}
	
	/**
	 * Creates a string with a 1000 multiple value of the source number
	 */
	private static String apresentaMilhar(int numero, String nomeSingular, String nomePlural)
	{
		if (numero == 100)
			return compoe("cem", nomePlural, ' ');
		
		StringBuffer parcial = new StringBuffer();

		if (numero > 100)
		{
			parcial.append(CENTENAS[numero / 100 - 1]);
			numero = numero % 100;
		}
		
		if (numero >= 20)
		{
			if (parcial.length() > 0) 
				parcial.append(" e ");
			
			parcial.append(DEZENAS[numero / 10 - 2]);
			numero = numero % 10;
		}
		
		if (numero == 1)
		{
			if (parcial.length() > 0) 
				parcial.append(" e ");
			
			return parcial.append(compoe("um", nomeSingular, ' ')).toString();
		}
		
		if (numero > 0)
		{
			if (parcial.length() > 0) 
				parcial.append(" e ");
			
			parcial.append(compoe(NUMEROS[numero], nomePlural, ' '));
		}
		
		return parcial.toString();
	}
	
	/**
	 * Composes two string using a separator if required
	 */
	private static String compoe(String s1, String s2, char separador)
	{
		return (s2.length() > 0) ? s1 + separador + s2 : s1;
	}
}