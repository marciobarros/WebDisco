<%@include file="header.jsp"%>

<h2>
  WEBDISCO - Catálogo de Compact Discs
</h2>				
		
<table id="tabelaLista">
<tr>
  <th class='title'>Título</th>
  <th class='price'>Preço</th>																
  <th class='stock'>Estoque</th>							
</tr>

<c:forEach var="cd" items="${requestScope.cdlist}">
	<tr>
		<td class='title'>
			<a href='/remove.do?id=${cd.id}'><img src='img/Delete.gif' border=0/></a>&nbsp;
			<a href='/edit.do?id=${cd.id}'><c:out value="${cd.title}"/></a>&nbsp;
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
<a href='/create.do'>Novo CD</a>

<%@include file="footer.jsp"%>