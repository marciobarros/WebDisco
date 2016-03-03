package br.unirio.webdisco.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe de suporte a persistencia de informacoes
 * 
 * @author marcio.barros
 */
public class SupportDAO
{
	/**
	 * Cria uma conexao com o banco de dados
	 */
	public static Connection getConnection()
	{	
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/webdisco", "root", "root");
			conexao.setCatalog("webdisco");
			return conexao;
			
		} catch (SQLException e)
		{
			System.out.println("Não foi possível estabelecer uma conexão com o banco de dados - erro de SQL");
			System.out.println(e.getMessage());
			return null;
		} catch (ClassNotFoundException e)
		{
			System.out.println("NNão foi possível estabelecer uma conexão com o banco de dados - driver não encontrado");
			return null;
		} catch (InstantiationException e)
		{
			System.out.println("Não foi possível estabelecer uma conexão com o banco de dados - erro de instanciacao do driver");
			return null;
		} catch (IllegalAccessException e)
		{
			System.out.println("Não foi possível estabelecer uma conexão com o banco de dados - acesso ilegal no driver");
			return null;
		}
	}
	
	/**
	 * Apresenta uma mensagem no log do sistema
	 */
	public static void log(String mensagem)
	{
		System.out.println(mensagem);
	}
}