<%@ page import="java.util.List" %>
<%@ page import="br.unirio.webdisco.Constants" %>
<%@ page import="br.unirio.webdisco.entity.CompactDisc" %>

<%
	int index = Integer.valueOf (request.getParameter ("index"));
	List<CompactDisc> cdlist = (List<CompactDisc>) session.getAttribute(Constants.CDLIST_KEY);
	cdlist.remove(index);
	response.sendRedirect("index.jsp");
%>