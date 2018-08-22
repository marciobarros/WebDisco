<%@include file="../helper/header.jsp"%>

<div class="container" data-ng-controller="loginController">
	<div class="row">
		<div class="col s3 m3 l3"></div>
	
		<div class="col s6 m6 l6">
			<sec:authorize access="isAnonymous()">
				<div class="card">
					<div class="card-content">
						<span class="card-title">
							Login
						</span>
		
						<br/>
		
						<div class="input-field">
							<input class="" type="text" id="user-email" data-ng-model="ctrl.data.email">
							<label class="" for="user-email"> 
							Email:
							</label>
						</div>
		
						<div class="input-field">
							<input class="" type="password" id="user-password" data-ng-model="ctrl.data.senha"> 
							<label class="" for="user-password"> 
							Senha:
							</label>
						</div>
					</div>
					
					<form action="auth/facebook" method="GET">
						<input type="hidden" name="scope" value="public_profile,email"></input>
						<button type="submit"><img src="static/img/social/facebook/signin.png"></img></button>
					</form>
					
					<form action="auth/twitter" method="POST">
						<input type="hidden" name="scope" value="public_profile" />
						<button type="submit"><img src="static/img/social/twitter/signin.png"></img></button>
					</form>
					
					<form action="auth/linkedin" method="POST">
						<button type="submit"><img src="static/img/social/twitter/signin.png"></img></button>
					</form>
					
					<form action="auth/google" method="POST">
						<button type="submit"><img src="static/img/social/twitter/signin.png"></img></button>
					</form>
					
			        <div class="card-action">
						<a data-ng-click="ctrl.envia()" class="btn">
							Envia
						</a>
						<a href="login/forgot" class="right small-text-button">
							Esqueci minha senha
						</a>
						<a href="login/new" class="right small-text-button">
							Nova Conta
						</a> 
					</div>
				</div>
			</sec:authorize>
	
			<sec:authorize access="isAuthenticated()">
				<sec:authentication var="user" property="principal" />
				<div class="card">
					<span>${user.nome}</span>, you are authenticated!!!
				</div>
			</sec:authorize>
		</div>
	
		<div class="mdl-cell mdl-cell--3-col"></div>
	</div>
</div>

<script type="application/javascript">
var app = angular.module('myApp', []);
</script>

<script type="application/javascript" src="static/html/login/login/login.controller.js"></script>

<%@include file="../helper/footer.jsp"%>
