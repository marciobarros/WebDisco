<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
	response.setHeader ("Cache-Control", "no-cache");
	response.setHeader ("Pragma", "no-cache");
	response.setDateHeader ("Expires", 0); 
%>

<html>
  <head>
	<title><s:text name="app.title"/></title>
  </head>
	
  <link rel="stylesheet" type="text/css" href="/css/estilo.css">

  <body>
	<table id="tabelaExterna">
	<tr>
	  <td id="colunaLateral">
	  </td>
	  <td id="colunaCentral">