package br.unirio.dsw.utils;

import java.sql.Timestamp;

import org.joda.time.DateTime;

/**
 * Classe de suporte para tratamento de datas
 * 
 * @author marcio.barros
 */
public class DateUtils
{
	private static String nomesMes[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};		
	
	/**
	 * Verifica se um determinado ano eh bissexto
	 */
	public static boolean isLeapYear(int ano)
	{
		return ((ano % 4 == 0) && (ano % 100 != 0) || (ano % 400 == 0));
	}

	/**
	 * Retorna o numero de dias de um mes em um ano
	 */
	public static int getMonthDays(int mes, int ano)
	{
		int monthDays[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};		
		return (mes == 2 && isLeapYear(ano)) ? 29 : monthDays[mes-1];
	}

	/**
	 * Retorna o nome de um mes do ano
	 */
	public static String getNomeMes(int mes)
	{
		return nomesMes[mes-1];
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
	 * Verifica se uma data esta dentro de um periodo de validade
	 */
	public static boolean verificaPeriodo(DateTime data, DateTime dataInicio, DateTime dataTermino)
	{
		return (!data.isBefore(dataInicio) && !data.isAfter(dataTermino));
	}

	/**
	 * Retorna a data de inicio de um mes
	 */
	public static DateTime startOfMonth(DateTime data)
	{
		return new DateTime(data.getYear(), data.getMonthOfYear(), 1, 0, 0, 0, 0);
	}

	/**
	 * Retorna a data de termino de um mes
	 */
	public static DateTime endOfMonth(DateTime data)
	{
		int year = data.getYear();
		int month = data.getMonthOfYear();
		return new DateTime(year, month, getMonthDays(month, year), 23, 59, 59, 0);
	}

	/**
	 * Calcula a data de referencia para um filtro de data de inicio
	 */
	public static DateTime ajustaFiltroDataInicio(DateTime dataInicio)
	{
		if (dataInicio != null)
			return dataInicio;
		
		return new DateTime(1900, 1, 1, 0, 0, 0, 0);
	}

	/**
	 * Calcula a data de referencia para um filtro de data de encerramento
	 */
	public static DateTime ajustaFiltroDataTermino(DateTime dataTermino)
	{
		if (dataTermino != null)
			return endOfDay(dataTermino);
		
		return new DateTime(2100, 1, 1, 0, 0, 0, 0);
	}

	/**
	 * Verifica se existe intersecao entre dois periodos
	 */
	public static boolean verificaIntersecaoPeriodo(DateTime dataInicio1, DateTime dataTermino1, DateTime dataInicio2, DateTime dataTermino2)
	{
		if (dataInicio1.isAfter(dataTermino2))
			return false;

		if (dataInicio2.isAfter(dataTermino1))
			return false;

		return true;
	}
}