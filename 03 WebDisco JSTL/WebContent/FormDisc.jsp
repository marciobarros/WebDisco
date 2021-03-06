<%@include file="header.jsp"%>

<h2>WEBDISCO - Cat�logo de Compact Discs</h2>

<c:if test="${not empty requestScope.error}">
	<p><font color=red><b><c:out value="${requestScope.error}"/></b></font></p>
</c:if>

<c:set var="cd" value="${requestScope.cd}"/>

<form method="post" action="/save.do">
	<input type="hidden" name="id" value="${cd.id}"/>

	<table>
	<tr>
	  <th align="right">
		T�tulo:
	  </th>
	  <td align="left">
		<input type="text" name="title" value="${cd.title}" size="64"/>
	  </td>
	</tr>

	<tr>
	  <th align="right">
		Pre�o:
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