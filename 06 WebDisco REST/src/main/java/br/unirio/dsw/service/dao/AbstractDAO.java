package br.unirio.dsw.service.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.unirio.dsw.configuration.Configuration;

/**
 * Superclasse de todas as classes que realizam persistencia de informacoes
 * 
 * @author marcio.barros
 */
abstract class AbstractDAO
{
	/**
	 * Cria uma conexao com o banco de dados
	 */
	protected Connection getConnection()
	{	
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return DriverManager.getConnection(Configuration.getDatabaseConnection(), Configuration.getDatabaseUser(), Configuration.getDatabasePassword());
		} 
		catch (SQLException e)
		{
			System.out.println("Nao foi possivel estabelecer uma conexao com o banco de dados - erro de SQL");
			System.out.println(e.getMessage());
			return null;
		} 
		catch (ClassNotFoundException e)
		{
			System.out.println("Nao foi possivel estabelecer uma conexao com o banco de dados - driver nao encontrado");
			return null;
		} 
		catch (InstantiationException e)
		{
			System.out.println("Nao foi possivel estabelecer uma conexao com o banco de dados - erro de instanciacao do driver");
			return null;
		} 
		catch (IllegalAccessException e)
		{
			System.out.println("Nao foi possivel estabelecer uma conexao com o banco de dados - acesso ilegal no driver");
			return null;
		}
	}
	
	/**
	 * Apresenta uma mensagem no log do sistema
	 */
	protected void log(String mensagem)
	{
		System.out.println(mensagem);
	}
}