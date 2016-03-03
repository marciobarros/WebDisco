<%@include file="header.jsp"%>

<h2><s:text name="app.title"/></h2>

<table id="tabelaLista">
<tr>
  <th class="title"><s:text name="prompt.title"/></th>
  <th class="price"><s:text name="prompt.price"/></th>														
  <th class="stock"><s:text name="prompt.stock"/></th>							
</tr>

<s:iterator value="#request.discs" status="status">
  <s:url id="deleteURL" action="delete"><s:param name="id"><s:property value="id"/></s:param></s:url>
  <s:url id="editURL" action="edit"><s:param name="id"><s:property value="id" /></s:param></s:url>
  
  <tr>
	<td class="title">
	  <a href="<s:property value="deleteURL"/>"><img src="/img/Delete16.gif" border="0" /></a>&nbsp;
	  <a href="<s:property value="editURL"/>"><s:property value="title" /></a>
	</td>
	<td class="price">
	  <s:property value="price" />
	</td>									
	<td class="stock">
	  <s:property value="stock" />
	</td>										
  </tr>
</s:iterator>
</table>					

<s:url id="nextURL" action="list"><s:param name="page"><s:property value="nextPage"/></s:param></s:url>
<s:url id="prevURL" action="list"><s:param name="page"><s:property value="previousPage"/></s:param></s:url>

<br>
<a href="<s:property value="prevURL"/>"><s:text name="menu.disc.prev"/></a> | 
<a href="<s:property value="nextURL"/>"><s:text name="menu.disc.next"/></a> | 
<a href="<s:url action="create"/>"><s:text name="menu.disc.create"/></a>

<%@include file="footer.jsp"%>