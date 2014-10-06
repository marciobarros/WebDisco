package br.unirio.webdisco.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.unirio.webdisco.Constants;
import br.unirio.webdisco.model.CompactDisc;

/**
 * Servlet para criação de um novo CD
 * 
 * @author Marcio
 */
public class ServletCreateDisc extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	/**
	 * Execução do servlet - protocolo GET
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		execute(request, response);
	}

	/**
	 * Execução do servlet - protocolo POST
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		execute(request, response);
	}

	/**
	 * Cria um novo CD
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		CompactDisc cd = new CompactDisc ();
		request.setAttribute (Constants.CD_KEY, cd);
		ServletContext context = getServletContext();
		RequestDispatcher rd = context.getRequestDispatcher("/FormDisc.jsp");
		rd.forward(request, response);
	}
}