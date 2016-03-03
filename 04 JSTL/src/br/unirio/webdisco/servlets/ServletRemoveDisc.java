package br.unirio.webdisco.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.unirio.webdisco.dao.DAOFactory;

/**
 * Servlet que remove um CD da memoria
 * 
 * @author Marcio
 */
public class ServletRemoveDisc extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int id = Integer.valueOf (request.getParameter ("id"));
		DAOFactory.getCompactDiscDAO().remove(id);
		response.sendRedirect("/list.do");
	}
}