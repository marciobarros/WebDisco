<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="pt">
<head>
  <!-- Configuracoes -->
  <title>Sistema de Chamadas - UNIRIO</title>
  <base href="/sistema/"/>
  <link rel="shortcut icon" href="static/img/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>

  <!-- CSS  -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="static/third-party/materialize/css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
  <link href="static/third-party/materialize/css/style.css" type="text/css" rel="stylesheet" media="screen,projection"/>
  <link href="static/css/app.css" type="text/css" rel="stylesheet" media="screen,projection"/>

  <!--  Scripts-->
  <script type="application/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
  <script type="application/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-route.js"></script>
  <script type="application/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
  <script type="application/javascript" src="static/third-party/materialize/js/materialize.js"></script>
  <script type="application/javascript" src="static/third-party/materialize/js/init.js"></script>
</head>

<script>
var csrf = { name: "${_csrf.parameterName}", value: "${_csrf.token}" };

$(document).ready(function() { 
	$(".dropdown-trigger").dropdown(); 
});

var App = angular.module('myApp', []);
</script>

<sec:authorize access="isAuthenticated()">
	<sec:authentication var="user" property="principal" />
</sec:authorize>

<body data-ng-app="myApp" class="grey lighten-3">
	<nav class="blue darken-2" role="navigation">
		<div class="nav-wrapper">
			<a href="homepage" class="brand-logo">Sistema de controle de chamadas</a>

	   		<sec:authorize access="isAuthenticated()">
	   			<!-- Dropdown trigger -->
				<ul id="nav-mobile" class="right hide-on-med-and-down">
					<li><a class="dropdown-trigger" href="#" data-target="menuConta"><i class="material-icons">more_vert</i></a></li>
				</ul>

				<!-- Identification -->
				<div class="right hide-on-med-and-down nav-identification-box">
					<div>Olá, ${user.nome}!</div>
					<div>Último login: ${user.dataUltimoLoginFormatada}</div>
				</div>

				<!-- Dropdown Structure -->
				<div class="right hide-on-med-and-down">
					<ul id="menuConta" class="dropdown-content">
						<li><a href="login/edit">Edita perfil</a></li>
						<li><a href="login/change">Troca senha</a></li>
						<li class="divider"></li>
						<li><a href="logout">Logout</a></li>
					</ul>
				</div>
			</sec:authorize>
		</div>
	</nav>
  