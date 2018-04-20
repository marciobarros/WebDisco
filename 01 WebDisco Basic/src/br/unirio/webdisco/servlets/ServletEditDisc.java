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
import br.unirio.webdisco.model.CompactDisc;

/**
 * Servlet para edicao de um CD
 * 
 * @author Marcio
 */
public class ServletEditDisc extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
	@SuppressWarnings("unchecked")
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int index = Integer.valueOf (request.getParameter ("index"));
		List<CompactDisc> cdlist = (List<CompactDisc>) request.getSession().getAttribute(Constants.CDLIST_KEY);
		CompactDisc cd = cdlist.get(index);
		
		request.setAttribute (Constants.CD_KEY, cd);
		request.setAttribute (Constants.INDEX_KEY, index);

		ServletContext context = getServletContext();
		RequestDispatcher rd = context.getRequestDispatcher("/FormDisc.jsp");
		rd.forward(request, response);
	}
}