<%@include file="header.jsp"%>
<%@ page import="java.util.Enumeration" %>

<h2>
    WEBDISCO - Catálogo de Compact Discs
</h2>
		
<h3>
  Menu
</h3>

<%
	String error = (String) request.getAttribute (Constants.ERROR_KEY);
	
	if (error != null)
		out.println ("<p><font color=red><B>" + error + "</B></font></p>");
	
	int index = Integer.valueOf (request.getParameter ("index"));
	CompactDisc cd = (CompactDisc) request.getAttribute (Constants.CD_KEY);
%>

<form action="SaveDisc.jsp">
	<input type="hidden" name="index" value="<%=index%>"/>

	<table id="tabelaFormulario">
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
  <a href="index.jsp">Lista</a>
</p>

<%@include file="footer.jsp"%>