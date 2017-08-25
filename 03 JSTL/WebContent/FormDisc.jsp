<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>
<%@ page import="java.util.Enumeration" %>

<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>

<h2>WEBDISCO - Catálogo de Compact Discs</h2>

<c:if test="${not empty requestScope.error}">
	<p><font color=red><b><c:out value="${requestScope.error}"/></b></font></p>
</c:if>

<c:set var="cd" value="${requestScope.cd}"/>

<form method="post" action="/save.do">
	<input type="hidden" name="id" value="${cd.id}"/>

	<table>
	<tr>
	  <th align="right">
		Título:
	  </th>
	  <td align="left">
		<input type="text" name="title" value="${cd.title}" size="64"/>
	  </td>
	</tr>

	<tr>
	  <th align="right">
		Preço:
	  </th>
	  <td align="left">
		<input type="text" name="price" value="${cd.price}" size="12"/>
	  </td>
	</tr>

	<tr>
	  <th align="right">
		Estoque:
	  </th>
	  <td align="left">
		<input type="text" name="stock" value="${cd.stock}" size="12"/>
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