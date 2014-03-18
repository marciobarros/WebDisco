<%@page import="br.unirio.webdisco.dao.DAOFactory"%>
<%@ page import="br.unirio.webdisco.Constants" %>
<%@ page import="br.unirio.webdisco.model.CompactDisc" %>

<%
	int id = Integer.valueOf (request.getParameter ("id"));
	CompactDisc cd = DAOFactory.getCompactDiscDAO().getCompactDiscId(id);
	request.setAttribute (Constants.CD_KEY, cd);
	pageContext.forward("FormDisc.jsp");
%>