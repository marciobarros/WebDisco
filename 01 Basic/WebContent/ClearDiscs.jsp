<%@ page import="java.util.List" %>
<%@ page import="br.unirio.webdisco.Constants" %>
<%@ page import="br.unirio.webdisco.entity.CompactDisc" %>

<%
	List<CompactDisc> cdlist = (List<CompactDisc>) session.getAttribute(Constants.CDLIST_KEY);
	cdlist.clear ();
	response.sendRedirect ("index.jsp");
%>
