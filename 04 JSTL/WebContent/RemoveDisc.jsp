<%@page import="br.unirio.webdisco.dao.DAOFactory"%>

<%
	int id = Integer.valueOf (request.getParameter ("id"));
	DAOFactory.getCompactDiscDAO().remove(id);
	response.sendRedirect("index.jsp");
%>