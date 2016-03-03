package br.unirio.simplemvc.utils;

import java.sql.Timestamp;

import org.joda.time.DateTime;

/**
 * Utility class for handling dates
 * 
 * @author marcio.barros
 */
public class DateUtils
{
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
	 * Retorna o nome de um mês do ano
	 */
	public static String getNomeMes(int mes)
	{
		String monthName[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};		
		return monthName[mes-1];
	}

	/**
	 * Converte um timestamp para data
	 */
	public static DateTime toDateTime(Timestamp timestamp)
	{
		return (timestamp == null) ? null : new DateTime(timestamp.getTime());
	}

	/**
	 * Converte um timestamp para data
	 */
	public static DateTime endOfDay(DateTime data)
	{
		return new DateTime(data.getYear(), data.getMonthOfYear(), data.getDayOfMonth(), 23, 59, 59, 0);
	}

	/**
	 * Verifica se uma data está dentro de um período de validade
	 */
	public static boolean verificaPeriodo(DateTime data, DateTime dataInicio, DateTime dataTermino)
	{
		return (!data.isBefore(dataInicio) && !data.isAfter(dataTermino));
	}
}