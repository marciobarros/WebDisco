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
	List<CompactDisc> cdlist = DAOFactory.getCompactDiscDAO().lista();
	
	for (int i = 0; i < cdlist.size(); i++)
	{
		CompactDisc disc = cdlist.get(i);
%>
		<tr>
			<td class='title'>
				<a href='RemoveDisc.jsp?id=<%=disc.getId()%>'><img src='img/Delete.gif' border=0/></a>&nbsp;
				<a href='EditDisc.jsp?id=<%=disc.getId()%>'><%=disc.getTitle()%></a>&nbsp;
			</td>
			<td class='price'>
				<%=disc.getPrice() %>
			</td>
			<td class='stock'>
				<%=disc.getStock() %>
			</td>
		</tr>
<%
	}
%>
</table>					

<br>
<a href='CreateDisc.jsp'>New Disc</a>

<%@include file="footer.jsp"%>