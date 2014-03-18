<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.unirio.webdisco.Constants" %>
<%@ page import="br.unirio.webdisco.entity.CompactDisc" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<link rel="stylesheet" type="text/css" href="/css/estilo.css">

<%
	response.setHeader ("Cache-Control", "no-cache");
	response.setHeader ("Pragma", "no-cache");
	response.setDateHeader ("Expires", 0); 
%>

<html>
  <head>
	<title>
	  WEBDISCO - Catálogo de Compact Discs
	</title>
  </head>
	
  <body>
	<table id="tabelaExterna">
	<tr>
	  <td id="colunaLateral">
	  </td>

	  <td id="colunaCentral">