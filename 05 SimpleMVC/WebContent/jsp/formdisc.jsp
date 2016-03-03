<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="header.jsp"%>
<%@ page import="java.util.Enumeration" %>

<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/simplemvc.tld" prefix="mvc"%>

<h2>WEBDISCO - Catálogo de Compact Discs</h2>

<mvc:error/>
<mvc:notice/>

<c:set var="cd" value="${requestScope.cd}"/>

<form action="/compactDisc/save.do">
	<input type="hidden" name="id" value="${cd.id}"/>

	<table id="tabelaFormulario">
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
		<input type="text" name="price" value='<fmt:formatNumber value="${cd.price}" pattern="#,##0.00"/>' size="12"/>
	  </td>
	</tr>

	<tr>
	  <th align="right">
		Estoque:
	  </th>
	  <td align="left">
		<input type="text" name="stock" value="<fmt:formatNumber value="${cd.stock}" pattern="#,##0"/>" size="12"/>
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
  <a href="/compactDisc/retrieve.do">Retorna para a lista</a>
</p>

<%@include file="footer.jsp"%>