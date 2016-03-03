<!DOCTYPE html>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/crud.tld" prefix="crud"%>
<%@taglib uri="/WEB-INF/simplemvc.tld" prefix="mvc"%>

<%@ page import="br.unirio.simplemvc.servlets.AuthenticationService" %>

<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="Sistema CRUD com login construído com o framework SimpleMVC">
	<meta name="author" content="Marcio Barros">

	<title>Sistema CRUD com Login</title>
	<link rel="shortcut icon" href="/favicon.ico">
	
	<link href="/css/twitter/bootstrap.min.css" rel="stylesheet">
	<link href="/css/twitter/bootstrap-responsive.min.css" rel="stylesheet">
	<link href="/css/jquery/jquery-ui.custom.css" rel="stylesheet">
	<link href="/css/template.css" rel="stylesheet">

	<script src="/js/jquery/jquery.min.js"></script>
	<script src="/js/jquery/jquery-ui.min.js"></script>
	<script src="/js/forms.js"></script>
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
			
			<a class="brand" href="javascript:void(0);">Sistema CRUD com login</a>
			
			<mvc:checkLogged>
			<div class="nav-collapse collapse">
				<p class="navbar-text pull-right">
					Olá, <c:out value="${usuarioLogado.nome}"/>! | 
					<a href="/login/preparaTrocaSenha.do">Troca Senha</a> | 
					<a href="/login/logout.do">Logout</a>
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
		<div id="pnSidebar">
		</div>

		<div id="pnCentral">
			<div class="row-fluid error" id="pnErro">
				<mvc:error/>
			</div>
			
			<div class="row-fluid notice" id="pnAviso">
				<mvc:notice/>
			</div>
			
			<div class="row-fluid" id="pnContent">
			</div>
		</div>
	</div>

	<hr style="margin-bottom: 0px;">

	<footer style="font-size: 10px;">
		<p>&copy; 2014: UNIRIO - Universidade Federal do Estado do Rio de Janeiro</p>
	</footer>
</div>

<script type="text/javascript">
$(document).ready(function() {
	var sidebar = $("#sidebar");
	
	if (sidebar && sidebar.length > 0) {
		$("#pnSidebar").addClass("span3");
		$("#pnCentral").addClass("span9");
		$("#pnSidebar").empty().append(sidebar)
	}
	else {
		$("#pnSidebar").remove();
		$("#pnCentral").addClass("span12");
	}
	
	var content = $("#content");
	if (content) $("#pnContent").empty().append(content);
	
	var divError = $("div.error");
	if (divError.find("p").length > 0) divError.show();
	
	var divNotice = $("div.notice");
	var divNoticeP = divNotice.find("p");
	if (divNoticeP.length > 0) divNotice.html(divNoticeP.text()).show();
});
</script>

</body>
</html>