<%@include file="header.jsp"%>

<h2><spring:message code="app.title" /></h2>

<form:form action="${pageContext.request.contextPath}/save.do" commandName="form" role="form" method="post" enctype="utf8">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<form:input type="hidden" name="id-title" path="id"/>

	<label for="cd-title"><spring:message code="label.title"/>:</label>
	<form:errors id="error-title" path="title" cssClass="error-block"/>				            
	<form:input id="cd-title" path="title" size="64"/>

	<label for="cd-price"><spring:message code="label.price"/>:</label>
	<form:errors id="error-price" path="price" cssClass="error-block"/>
	<form:input id="cd-price" path="price" size="64"/>

	<label for="cd-stock"><spring:message code="label.stock"/>:</label>
	<form:errors id="error-stock" path="stock" cssClass="error-block"/>				            
	<form:input id="cd-stock" path="stock" size="64"/>

	<button type="submit"><spring:message code="command.send"/></button>
</form:form>

<br/>

<a href="${pageContext.request.contextPath}/">
	<spring:message code="command.return.list" />
</a>

<%@include file="footer.jsp"%>