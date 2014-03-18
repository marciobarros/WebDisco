<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="br.unirio.webdisco.Constants" %>
<%@ page import="br.unirio.webdisco.entity.CompactDisc" %>

<%!
	void notifyError (PageContext page, int index, CompactDisc cd, String error) throws Exception
	{
		page.getRequest().setAttribute (Constants.CD_KEY, cd);
		page.getRequest().setAttribute (Constants.ERROR_KEY, error);
		page.forward ("FormDisc.jsp?index=" + index);
	}
%>

<%
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
		notifyError (pageContext, index, cd, "O título deve ser preenchido");
		return;
	}

	if (price <= 0.0)
	{
		notifyError (pageContext, index, cd, "O preço deve ser maior do que zero");
		return;
	}

	if (stock < 0.0)
	{
		notifyError (pageContext, index, cd, "A quantidade em estoque deve ser maior ou igual a zero");
		return;
	}

	List<CompactDisc> cdlist = (List<CompactDisc>) session.getAttribute(Constants.CDLIST_KEY);
	
	if (cdlist == null)
	{
		cdlist = new ArrayList<CompactDisc>();
		session.setAttribute(Constants.CDLIST_KEY, cdlist);
	}
	
	if (index == -1)
		cdlist.add(cd);
	else
		cdlist.set(index, cd);
	
	response.sendRedirect ("index.jsp");
%>