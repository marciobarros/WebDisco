<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="br.unirio.webdisco.Constants" %>
<%@ page import="br.unirio.webdisco.model.CompactDisc" %>
<%@ page import="java.util.List" %>

<%
	response.setHeader ("Cache-Control", "no-cache");
	response.setHeader ("Pragma", "no-cache");
	response.setDateHeader ("Expires", 0); 
%>

<html>
  <head>
	<title>WEBDISCO - Catálogo de Compact Discs</title>
	<link rel="stylesheet" type="text/css" href="css/estilo.css">
  </head>
	
  <body>