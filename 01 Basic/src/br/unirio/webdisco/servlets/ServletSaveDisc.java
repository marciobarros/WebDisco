package br.unirio.webdisco.servlets;

import java.io.IOException;
import java.util.ArrayList;
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
 * Servlet que salva os dados de um CD
 * 
 * @author Marcio
 */
public class ServletSaveDisc extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
	/**
	 * Execucao do servlet
	 */
	@SuppressWarnings("unchecked")
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int index = Integer.valueOf (request.getParameter ("index"));		
		String title = request.getParameter ("title");
		double price = Double.valueOf (request.getParameter ("price"));
		double stock = Double.valueOf (request.getParameter ("stock"));

		CompactDisc cd = new CompactDisc ();
		cd.setTitle (title);
		cd.setPrice (price);
		cd.setStock (stock);

		if ((title == null) || (title.length() < 1))
		{
			notifyError (request, response, index, cd, "O titulo deve ser preenchido");
			return;
		}

		if (price <= 0.0)
		{
			notifyError (request, response, index, cd, "O preco deve ser maior do que zero");
			return;
		}

		if (stock < 0.0)
		{
			notifyError (request, response, index, cd, "A quantidade em estoque deve ser maior ou igual a zero");
			return;
		}

		List<CompactDisc> cdlist = (List<CompactDisc>) request.getSession().getAttribute(Constants.CDLIST_KEY);
		
		if (cdlist == null)
		{
			cdlist = new ArrayList<CompactDisc>();
			request.getSession().setAttribute(Constants.CDLIST_KEY, cdlist);
		}
		
		if (index == -1)
			cdlist.add(cd);
		else
			cdlist.set(index, cd);
		
		response.sendRedirect ("/list.do");
	}

	/**
	 * Gera uma mensagem de erro pela critica dos dados do CD
	 */
	private void notifyError (HttpServletRequest request, HttpServletResponse response, int index, CompactDisc cd, String error) throws ServletException, IOException
	{
		request.setAttribute (Constants.CD_KEY, cd);
		request.setAttribute (Constants.INDEX_KEY, index);
		request.setAttribute (Constants.ERROR_KEY, error);

		ServletContext context = getServletContext();
		RequestDispatcher rd = context.getRequestDispatcher("/FormDisc.jsp");
		rd.forward(request, response);
	}
}