<%@include file="header.jsp"%>

<h2>
  WEBDISCO - Cat�logo de Compact Discs
</h2>				

<table id="tabelaLista">
<tr>
  <th class='title'>T�tulo</th>
  <th class='price'>Pre�o</th>																
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
			<a href='/remove.do?index=<%=i%>'><img src='img/Delete.gif' border=0/></a>&nbsp;
			<a href='/edit.do?index=<%=i%>'><%=cd.getTitle()%></a>&nbsp;
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
<a href='/create.do'>Novo CD</a>

<%@include file="footer.jsp"%>