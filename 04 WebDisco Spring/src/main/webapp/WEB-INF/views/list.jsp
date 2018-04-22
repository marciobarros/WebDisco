<%@include file="header.jsp"%>

<h2><spring:message code="app.title" /></h2>				
		
<table id="tabelaLista">
<tr>
  <th class='title'><spring:message code="label.title"/></th>
  <th class='price'><spring:message code="label.price"/></th>
  <th class='stock'><spring:message code="label.stock"/></th>
</tr>

<c:forEach var="cd" items="${requestScope.cdlist}">
	<tr>
		<td class='title'>
			<a href='${pageContext.request.contextPath}/remove/${cd.id}'><img src='${pageContext.request.contextPath}/static/img/Delete.gif' border="0"/></a>&nbsp;
			<a href='${pageContext.request.contextPath}/edit/${cd.id}'><c:out value="${cd.title}"/></a>&nbsp;
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

<a href='${pageContext.request.contextPath}/create'>
	<spring:message code="command.new"/>
</a>

<%@include file="footer.jsp"%>