package br.unirio.simplemvc.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

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

		if (s.compareToIgnoreCase("ON") == 0 || s.compareToIgnoreCase("true") == 0)
			return true;
		else
			return false;
	}

	/**
	 * Converts a string to a date/time value, using a default in case of exceptions
	 */
	public static DateTime safeParseDateTime(String sdata)
	{
		try
		{
			return DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(sdata);
		} catch(Exception e)
		{
			return null;
		}
	}

	/**
	 * Converts a date/time value to a string, using a default in case of exceptions
	 */
	public static String safeFormatDateTime(DateTime data)
	{
		try
		{
			return DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").print(data);
		} catch(Exception e)
		{
			return "";
		}
	}

	/**
	 * Converts a string to a date value, using a default in case of exceptions
	 */
	public static DateTime safeParseDate(String sdata)
	{
		try
		{
			return DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(sdata);
		} catch(Exception e)
		{
			return null;
		}
	}

	/**
	 * Converts a date value to a string, using a default in case of exceptions
	 */
	public static String safeFormatDate(DateTime data)
	{
		try
		{
			return DateTimeFormat.forPattern("dd/MM/yyyy").print(data);
		} catch(Exception e)
		{
			return "";
		}
	}
}