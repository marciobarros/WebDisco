<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
	response.setHeader ("Cache-Control", "no-cache");
	response.setHeader ("Pragma", "no-cache");
	response.setDateHeader ("Expires", 0); 
%>

<html>
  <head>
	<title><s:text name="app.title"/></title>
	<link rel="stylesheet" type="text/css" href="/css/estilo.css">
  </head>

  <body>
