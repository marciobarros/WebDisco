<%@include file="header.jsp"%>

<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>

<h2>WEBDISCO - Catálogo de Compact Discs</h2>				
		
<table id="tabelaLista">
<tr>
  <th class='title'>Título</th>
  <th class='price'>Preço</th>																
  <th class='stock'>Estoque</th>							
</tr>

<c:forEach var="cd" items="${requestScope.discs}">
	<tr>
		<td class='title'>
			<a href='/compactDisc/delete.do?id=${cd.id}'><img src='/img/icons/delete.gif' border=0/></a>&nbsp;
			<a href='/compactDisc/edit.do?id=${cd.id}'><c:out value="${cd.title}"/></a>&nbsp;
		</td>
		<td class='price'>
			<c:out value="${cd.price}"/>
		</td>
		<td class='stock'>
			<c:out value="${cd.stock}"/>
		</td>
	</tr>
</c:forEach>
</table>					

<br>
<c:if test="${requestScope.hasPriorPage}">
	<a href='/compactDisc/retrieve.do?page=${requestScope.page-1}'>Ant</a> | 
</c:if>
<c:if test="${requestScope.hasNextPage}">
	<a href='/compactDisc/retrieve.do?page=${requestScope.page+1}'>Próx</a> |
</c:if> 
<a href='/compactDisc/create.do'>Novo CD</a>

<%@include file="footer.jsp"%>