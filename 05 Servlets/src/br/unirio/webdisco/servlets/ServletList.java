package br.unirio.webdisco.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.unirio.webdisco.dao.DAOFactory;
import br.unirio.webdisco.model.CompactDisc;

/**
 * Servlet que gera a lista de CDs
 * 
 * @author Marcio
 */
public class ServletList extends HttpServlet 
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
	 * Coloca a lista de CDs na memória de requisição
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		List<CompactDisc> cdlist = DAOFactory.getCompactDiscDAO().lista();
		request.setAttribute("cdlist", cdlist);

		ServletContext context = getServletContext();
		RequestDispatcher rd = context.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}
}