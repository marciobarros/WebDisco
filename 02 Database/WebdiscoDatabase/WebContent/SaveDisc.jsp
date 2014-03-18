<%@page import="br.unirio.webdisco.dao.DAOFactory"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="br.unirio.webdisco.Constants" %>
<%@ page import="br.unirio.webdisco.model.CompactDisc" %>

<%!
	void notifyError (PageContext page, CompactDisc cd, String error) throws Exception
	{
		page.getRequest().setAttribute (Constants.CD_KEY, cd);
		page.getRequest().setAttribute (Constants.ERROR_KEY, error);
		page.forward ("FormDisc.jsp");
	}
%>

<%
	int id = Integer.valueOf (request.getParameter ("id"));
	String title = request.getParameter ("title");
	double price = Double.valueOf (request.getParameter ("price"));
	double stock = Double.valueOf (request.getParameter ("stock"));

	CompactDisc cd = new CompactDisc ();
	cd.setId(id);
	cd.setTitle (title);
	cd.setPrice (price);
	cd.setStock (stock);

	if ((title == null) || (title.length() < 1))
	{
		notifyError (pageContext, cd, "O título deve ser preenchido");
		return;
	}

	if (price <= 0.0)
	{
		notifyError (pageContext, cd, "O preço deve ser maior do que zero");
		return;
	}

	if (stock < 0.0)
	{
		notifyError (pageContext, cd, "A quantidade em estoque deve ser maior ou igual a zero");
		return;
	}

	if (id == -1)
		DAOFactory.getCompactDiscDAO().insere(cd);
	else
		DAOFactory.getCompactDiscDAO().atualiza(cd);
	
	response.sendRedirect ("index.jsp");
%>