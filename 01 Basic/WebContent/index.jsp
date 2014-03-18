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
	List<CompactDisc> cdlist = (List<CompactDisc>) session.getValue (Constants.CDLIST_KEY);
	
	if (cdlist == null)
	{
		cdlist = new ArrayList<CompactDisc> ();
		session.setAttribute(Constants.CDLIST_KEY, cdlist);
	}

	for (int i = 0; i < cdlist.size(); i++)
	{
		CompactDisc disc = cdlist.get(i);
%>
		<tr>
			<td class='title'>
				<a href='RemoveDisc.jsp?index=<%=i%>'><img src='img/Delete.gif' border=0/></a>&nbsp;
				<a href='EditDisc.jsp?index=<%=i%>'><%=disc.getTitle()%></a>&nbsp;
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

<BR>
<a href='CreateDisc.jsp'>New Disc</A>&nbsp;|&nbsp;
<a href='ClearDiscs.jsp'>Clear Disc</A>

<%@include file="footer.jsp"%>