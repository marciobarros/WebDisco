package br.unirio.simplemvc.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for type conversion
 *  
 * @author Marcio Barros
 */
public class Conversion 
{
	/**
	 * Converts a string to a long integer value, using a default in case of exceptions
	 */
	public static long safeParseLong(String s, long _default)
	{
		try 
		{
			s = s.replace(".", "");
			return Long.parseLong(s);
		}
		catch (Exception e)
		{
			return _default;
		}
	}

	/**
	 * Converts a string to a floating-point value, using a default in case of exceptions
	 */
	public static double safeParseDouble(String s, double _default)
	{
		try 
		{
			s = s.replace(".", "");
			s = s.replace(",", ".");
			return Double.parseDouble(s);
		}
		catch (Exception e)
		{
			return _default;
		}
	}

	/**
	 * Converts a string to an integer value, using a default in case of exceptions
	 */
	public static int safeParseInteger(String s, int _default)
	{
		try
		{
			s = s.replace(".", "");
			return Integer.parseInt(s);
		}
		catch (Exception e)
		{
			return _default;
		}
	}
	
	/**
	 * Converts a string to a boolean value, using a default in case of exceptions
	 */
	public static boolean safeParseBoolean(String s)
	{
		if (s == null)
			return false;

		if (s.compareToIgnoreCase("ON") == 0)
			return true;
		else
			return false;
	}

	/**
	 * Converts a string to a date value, using a default in case of exceptions
	 */
	public static Date safeParseDate(String sdata)
	{
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		try
		{
			return (Date) formatter.parse(sdata);
		} catch (ParseException e)
		{
			return null;
		}
	}
}