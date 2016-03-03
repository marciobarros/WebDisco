package br.unirio.webdisco.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.unirio.webdisco.Constants;
import br.unirio.webdisco.dao.DAOFactory;
import br.unirio.webdisco.model.CompactDisc;

/**
 * Servlet para edicao de um CD
 * 
 * @author Marcio
 */
public class ServletEditDisc extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int id = Integer.valueOf (request.getParameter ("id"));
		CompactDisc cd = DAOFactory.getCompactDiscDAO().getCompactDiscId(id);
		request.setAttribute (Constants.CD_KEY, cd);

		ServletContext context = getServletContext();
		RequestDispatcher rd = context.getRequestDispatcher("/FormDisc.jsp");
		rd.forward(request, response);
	}
}