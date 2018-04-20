package br.unirio.webdisco.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.unirio.webdisco.Constants;
import br.unirio.webdisco.model.CompactDisc;

/**
 * Servlet que remove um CD da memoria
 * 
 * @author Marcio
 */
public class ServletRemoveDisc extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
	@SuppressWarnings("unchecked")
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int index = Integer.valueOf (request.getParameter ("index"));
		List<CompactDisc> cdlist = (List<CompactDisc>) request.getSession().getAttribute(Constants.CDLIST_KEY);
		cdlist.remove(index);
		response.sendRedirect("/list.do");
	}
}