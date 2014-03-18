<%@include file="header.jsp"%>

<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>

<h2>
  WEBDISCO - Catálogo de Compact Discs
</h2>				
		
<table id="tabelaLista">
<tr>
  <th class='title'>Título</th>
  <th class='price'>Preço</th>																
  <th class='stock'>Estoque</th>							
</tr>

<%
	List<CompactDisc> cdlist = DAOFactory.getCompactDiscDAO().lista();
	pageContext.setAttribute("cdlist", cdlist);
%>
	<c:forEach var="cd" items="${cdlist}">
		<tr>
			<td class='title'>
				<a href='RemoveDisc.jsp?id=${cd.id}'><img src='img/Delete.gif' border=0/></a>&nbsp;
				<a href='EditDisc.jsp?id=${cd.id}'><c:out value="${cd.title}"/></a>&nbsp;
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
<a href='CreateDisc.jsp'>New Disc</a>

<%@include file="footer.jsp"%>