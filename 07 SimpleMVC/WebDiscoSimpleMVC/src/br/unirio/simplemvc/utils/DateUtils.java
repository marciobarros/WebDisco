package br.unirio.simplemvc.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Utility class for handling dates
 * 
 * @author marcio.barros
 */
public class DateUtils
{
	private static Calendar calendar = new GregorianCalendar();
	
	/**
	 * Returns the year of a given date
	 */
	public static int getYear(Date date)
	{
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * Returns the month of a given date
	 */
	public static int getMonth(Date date)
	{
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * Returns the day of a given date
	 */
	public static int getDay(Date date)
	{
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Returns the hour of a given date
	 */
	public static int getHour(Date data)
	{
		calendar.setTime(data);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Returns the minute of a given date
	 */
	public static int getMinute(Date data)
	{
		calendar.setTime(data);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * Creates a date object, given its components
	 */
	public static Date create(int day, int month, int year)
	{
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return (Date) calendar.getTime().clone();
	}

	/**
	 * Creates a date object, given its components
	 */
	public static Date create(int day, int month, int year, int hour, int minute, int second)
	{
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, 0);
		return (Date) calendar.getTime().clone();
	}

	/**
	 * Adiciona um número de dias a uma data
	 */
	public static Date addDays(Date date, int days)
	{
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}

	/**
	 * Adiciona um número de meses a uma data
	 */
	public static Date addMonths(Date date, int months)
	{
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}

	/**
	 * Retorna a data de hoje
	 */
	public static Date now()
	{
		return new Date();
	}

	/**
	 * Retorna o último instante de uma data
	 */
	public static Date getLastMoment(Date data)
	{
		int dia = DateUtils.getDay(data);
		int mes = DateUtils.getMonth(data);
		int ano = DateUtils.getYear(data);
		return create(dia, mes, ano, 23, 59, 59);
	}

	/**
	 * Retorna o primeiro instante de uma data
	 */
	public static Date getFirstMoment(Date data)
	{
		int dia = DateUtils.getDay(data);
		int mes = DateUtils.getMonth(data);
		int ano = DateUtils.getYear(data);
		return create(dia, mes, ano, 0, 0, 0);
	}

	/**
	 * Retorna o primeiro dia de um mês
	 */
	public static Date getFirstDayMonth(int mes, int ano)
	{
		return create(1, mes, ano, 0, 0, 0);
	}

	/**
	 * Verifica se um determinado ano é bissexto
	 */
	public static boolean isLeapYear(int ano)
	{
		return ((ano % 4 == 0) && (ano % 100 != 0) || (ano % 400 == 0));
	}

	/**
	 * Retorna o número de dias de um mês em um ano
	 */
	public static int getMonthDays(int mes, int ano)
	{
		int monthDays[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};		
		return (mes == 2 && isLeapYear(ano)) ? 29 : monthDays[mes-1];
	}

	/**
	 * Retorna o último dia de um mês
	 */
	public static Date getLastDayMonth(int mes, int ano)
	{
		int dia = getMonthDays(mes, ano);
		return create(dia, mes, ano, 0, 0, 0);
	}

	/**
	 * Retorna o nome de um mês do ano
	 */
	public static String getNomeMes(int mes)
	{
		String monthName[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};		
		return monthName[mes-1];
	}

	/**
	 * Retorna uma data depois de programar sua hora, minuto e segundo
	 */
	public static Date setTempo(Date data, int hora, int minuto, int segundo)
	{
		calendar.setTime(data);
		calendar.add(Calendar.HOUR, hora);
		calendar.add(Calendar.MINUTE, minuto);
		calendar.add(Calendar.SECOND, segundo);
		return calendar.getTime();
	}
}