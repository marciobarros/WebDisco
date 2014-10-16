package br.unirio.webdisco.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.unirio.webdisco.dao.DAOFactory;

/**
 * Servlet que remove um CD da memória
 * 
 * @author Marcio
 */
public class ServletRemoveDisc extends HttpServlet 
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
	 * Remove o CD selecionado pelo usuário
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int id = Integer.valueOf (request.getParameter ("id"));
		DAOFactory.getCompactDiscDAO().remove(id);
		response.sendRedirect("/WebdiscoServlets/list.do");
	}
}