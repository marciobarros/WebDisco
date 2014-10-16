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

<%
	List<CompactDisc> cdlist = (List<CompactDisc>) request.getAttribute("cdlist");
	
	for (int i = 0; i < cdlist.size(); i++)
	{
		CompactDisc cd = cdlist.get(i);
%>
	<tr>
		<td class='title'>
			<a href='/WebdiscoServlets/remove.do?id=<%=cd.getId()%>'><img src='img/Delete.gif' border=0/></a>&nbsp;
			<a href='/WebdiscoServlets/edit.do?id=<%=cd.getId()%>'><%=cd.getTitle()%></a>&nbsp;
		</td>
		<td class='price'>
			<%=cd.getPrice()%>
		</td>
		<td class='stock'>
			<%=cd.getStock()%>
		</td>
	</tr>
<% } %>
</table>					

<br>
<a href='/WebdiscoServlets/create.do'>Novo CD</a>

<%@include file="footer.jsp"%>