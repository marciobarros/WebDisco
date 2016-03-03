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
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int id = safeConversionInt(request.getParameter ("id"));
		String title = request.getParameter ("title");
		double price = safeConversionDouble(request.getParameter ("price"));
		double stock = safeConversionDouble(request.getParameter ("stock"));

		CompactDisc cd = new CompactDisc ();
		cd.setId(id);
		cd.setTitle (title);
		cd.setPrice (price);
		cd.setStock (stock);

		if ((title == null) || (title.length() < 1))
		{
			notifyError (request, response, cd, "O título deve ser preenchido");
			return;
		}

		if (price <= 0.0)
		{
			notifyError (request, response, cd, "O preço deve ser maior do que zero");
			return;
		}

		if (stock < 0.0)
		{
			notifyError (request, response, cd, "A quantidade em estoque deve ser maior ou igual a zero");
			return;
		}

		if (id == -1)
			DAOFactory.getCompactDiscDAO().insere(cd);
		else
			DAOFactory.getCompactDiscDAO().atualiza(cd);
		
		response.sendRedirect ("/list.do");
	}

	/**
	 * Gera uma mensagem de erro pela critica dos dados do CD
	 */
	private void notifyError (HttpServletRequest request, HttpServletResponse response, CompactDisc cd, String error) throws ServletException, IOException
	{
		request.setAttribute (Constants.CD_KEY, cd);
		request.setAttribute (Constants.ERROR_KEY, error);

		ServletContext context = getServletContext();
		RequestDispatcher rd = context.getRequestDispatcher("/FormDisc.jsp");
		rd.forward(request, response);
	}
	
	/**
	 * Converte uma string para inteiro
	 */
	private int safeConversionInt(String s)
	{
		try
		{
			return Integer.valueOf(s);
		} 
		catch (Exception e)
		{
			return 0;
		}
	}
	
	/**
	 * Converte uma string para double
	 */
	private double safeConversionDouble(String s)
	{
		try
		{
			return Double.valueOf(s);
		} 
		catch (Exception e)
		{
			return 0.0;
		}
	}
}