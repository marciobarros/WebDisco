<!DOCTYPE html>
<html lang="pt">
<head>
  <!-- Configuracoes -->
  <title>Title</title>
  <base href="/sistema/"/>
  <link rel="shortcut icon" href="static/img/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>

  <!-- CSS  -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="static/third-party/materialize/css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
  <link href="static/third-party/materialize/css/style.css" type="text/css" rel="stylesheet" media="screen,projection"/>

  <!--  Scripts-->
  <script type="application/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
  <script type="application/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-route.js"></script>
  <script type="application/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
  <script type="application/javascript" src="static/third-party/materialize/js/materialize.js"></script>
  <script type="application/javascript" src="static/third-party/materialize/js/init.js"></script>
</head>

<script>
var csrf = { name: "${_csrf.parameterName}", value: "${_csrf.token}" };
</script>

<body data-ng-app="myApp" class="grey lighten-2">
  <nav class="light-blue lighten-1" role="navigation">
    <div class="nav-wrapper container">
    	<a id="logo-container" href="#" class="brand-logo">
    		Sistema de controle de chamadas
    	</a>
    </div>
  </nav>
