<!DOCTYPE html>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/ppgi.tld" prefix="ppgi"%>
<%@taglib uri="/WEB-INF/simplemvc.tld" prefix="mvc"%>

<%@ page import="br.unirio.simplemvc.servlets.AuthenticationService" %>

<html lang="en">
<head>
	<meta charset="utf-8">
	<title>PPGI/UNIRIO - Sistema de Inscrição</title>

	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	
	<link href="/css/jquery/jquery-ui.custom.css" rel="stylesheet">
	<link href="/css/twitter/bootstrap.min.css" rel="stylesheet">
	<link href="/css/twitter/bootstrap-responsive.min.css" rel="stylesheet">
	<link href="/css/template.css" rel="stylesheet">

	<script src="/js/jquery/jquery.min.js"></script>
	<script src="/js/forms.js"></script>

	<link rel="shortcut icon" href="/favicon.ico">
</head>

<%
	pageContext.setAttribute("usuarioLogado", AuthenticationService.getUser(request));
%>

<body>

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			
			<a class="brand" href="javascript:void(0);">PPGI - Sistema de Inscrição para o curso de Mestrado</a>
			
			<mvc:checkLogged>
			<div class="nav-collapse collapse">
				<p class="navbar-text pull-right">
					Olá, <c:out value="${usuarioLogado.nome}"/>! | <a href="/login/preparaTrocaSenha.do">Troca Senha</a> | <a href="/login/logout.do">Logout</a>
				</p>
				<ul class="nav">
				</ul>
			</div>
			</mvc:checkLogged>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span3">
			<mvc:checkLogged>
				<div class="well sidebar-nav">
					<ul class="nav nav-list">
						<li><a href="/login/homepage.do">Homepage</a></li>
						
						<mvc:checkUserLevel level="ADM">
						<li class="nav-header">Administração</li>
						<li id="cmdAbreEdital"><a href="/edital/preparaAbertura.do">Abre edital</a></li>
						<li id="cmdFechaEdital"><a href="/edital/preparaFechamento.do">Fecha edital</a></li>
						<li><a href="/inscricao/preparaRelatorioInscritos.do">Relatório de inscritos</a></li>
						<li><a href="/inscricao/preparaRelatorioSelecao.do">Relatório de seleção</a></li>
						</mvc:checkUserLevel>

						<li class="nav-header">Meus dados</li>
						<li><a href="/login/edita.do?id=${usuarioLogado.id}">Meu perfil</a></li>

						<mvc:checkUserLevel level="ADM" type="none">
							<li id="cmdInscricao"><a href="/inscricao/prepara.do">Minha inscrição</a></li>
						</mvc:checkUserLevel>
					</ul>
				</div>
			</mvc:checkLogged>

			<mvc:checkUnlogged>
				<div class="well">
					<h3>Login</h3>
					<form action="/login/login.do" method="post">
						<label for="email">E-mail:</label>
						<input type="text" name="email" id="emailLoginField" />
						
						<label for="pwd">Senha:</label>
						<input type="password" name="pwd" /><br>
						<input type="submit" name="Submit" value="Login">
					</form>
					
					<ul>
						<li><a href="/jsp/login/esqueceuSenha.jsp">Esqueceu sua senha?</a></li>
						<li><a href="/login/novo.do">Criar conta no sistema</a></li>
					</ul>
				</div>
			</mvc:checkUnlogged>
		</div>

		<div class="span9">
			<div class="row-fluid error" id="pnErro">
				<mvc:error/>
			</div>
			
			<div class="row-fluid notice" id="pnAviso">
				<mvc:notice/>
			</div>
			
			<div class="row-fluid" id="pnCentral">
			</div>
		</div>
	</div>

	<hr style="margin-bottom: 0px;">

	<footer style="font-size: 10px;">
		<p>&copy; 2013: UNIRIO - Universidade Federal do Estado do Rio de Janeiro</p>
	</footer>
</div>

<script type="text/javascript">
$(document).ready(function() {
	var content = $("#content");
	if (content) $("#pnCentral").html("").append(content);
	
	ajaxCall("/edital/temEditalAberto.do", "", true, function(data) {
		if (data.result)
		{
			$("#cmdFechaEdital").show();
			$("#cmdInscricao").show();
		}
		else
			$("#cmdAbreEdital").show();
	});
	
	var divError = $("div.error");
	if (divError.find("p").length > 0) divError.show();
	
	var divNotice = $("div.notice");
	var divNoticeP = divNotice.find("p");
	if (divNoticeP.length > 0) divNotice.html(divNoticeP.text()).show(); 
});
</script>

</body>
</html>