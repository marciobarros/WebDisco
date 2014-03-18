<%@ page import="br.unirio.webdisco.Constants" %>
<%@ page import="br.unirio.webdisco.model.CompactDisc" %>

<%
	CompactDisc cd = new CompactDisc ();
	request.setAttribute (Constants.CD_KEY, cd);
	pageContext.forward("FormDisc.jsp");
%>