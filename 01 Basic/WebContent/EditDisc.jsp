<%@ page import="java.util.List" %>
<%@ page import="br.unirio.webdisco.Constants" %>
<%@ page import="br.unirio.webdisco.entity.CompactDisc" %>

<%
	int index = Integer.valueOf (request.getParameter ("index"));
	List<CompactDisc> cdlist = (List<CompactDisc>) session.getValue (Constants.CDLIST_KEY);
	CompactDisc cd = cdlist.get(index);
	request.setAttribute (Constants.CD_KEY, cd);
	pageContext.forward("FormDisc.jsp?index=" + index);
%>