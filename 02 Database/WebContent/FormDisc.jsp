<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="header.jsp"%>
<%@ page import="java.util.Enumeration" %>

<h2>WEBDISCO - Catálogo de Compact Discs</h2>

<%
	String error = (String) request.getAttribute (Constants.ERROR_KEY);
	
	if (error != null)
		out.println ("<p><font color=red><b>" + error + "</b></font></p>");
	
	CompactDisc cd = (CompactDisc) request.getAttribute (Constants.CD_KEY);
%>

<form method="post" action="/save.do">
	<input type="hidden" name="id" value="<%=cd.getId()%>"/>

	<table>
	<tr>
	  <th align="right">
		Título:
	  </th>
	  <td align="left">
		<input type="text" name="title" value="<%=cd.getTitle()%>" size="64"/>
	  </td>
	</tr>

	<tr>
	  <th align="right">
		Preço:
	  </th>
	  <td align="left">
		<input type="text" name="price" value="<%=cd.getPrice()%>" size="12"/>
	  </td>
	</tr>

	<tr>
	  <th align="right">
		Estoque:
	  </th>
	  <td align="left">
		<input type="text" name="stock" value="<%=cd.getStock()%>" size="12"/>
	  </td>
	</tr>								

	<tr>
	  <td colspan="2" align="right">
		<input type="submit"/>
	  </td>									
	</tr>
	</table>					
</form>

<p>
  <a href="/list.do">Retorna para a lista</a>
</p>

<%@include file="footer.jsp"%>