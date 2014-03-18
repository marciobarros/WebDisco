<%@include file="header.jsp"%>

<h2><s:text name="app.title"/></h2>

<h3><s:text name="menu.disc"/></h3>

<s:form action="save" method="get"> 
	<s:hidden name="id" value="%{#request.disc.id}"/>
	<s:textfield key="prompt.title" name="title" value="%{#request.disc.title}" size="64"/>
	<s:textfield key="prompt.price" name="price" value="%{#request.disc.price}" size="12" />
	<s:textfield key="prompt.stock" name="stock" value="%{#request.disc.stock}" size="12" />
	<s:submit />
</s:form>

<a href="<s:url action="list"/>"><s:text name="list.list"/></a>

<%@include file="footer.jsp"%>