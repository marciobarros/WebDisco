package br.unirio.webdisco.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.unirio.webdisco.Constants;
import br.unirio.webdisco.dao.CompactDiscDAO;
import br.unirio.webdisco.model.CompactDisc;

/**
 * Servlet que gera a lista de CDs
 * 
 * @author Marcio
 */
public class ServletList extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		List<CompactDisc> cdlist = new CompactDiscDAO().lista();
		request.setAttribute(Constants.CDLIST_KEY, cdlist);

		ServletContext context = getServletContext();
		RequestDispatcher rd = context.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}
}