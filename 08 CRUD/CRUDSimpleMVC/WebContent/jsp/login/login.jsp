<%@include file="/jsp/helpers/template.jsp"%>

<div id="sidebar" class="well">
	<h3>Login</h3>

	<div>
		<form action="/login/login.do" method="post">
			<label for="email">E-mail:</label>
			<input type="text" name="email" class="input-block-level" />
			
			<label for="pwd">Senha:</label>
			<input type="password" name="pwd" class="input-block-level" />
			
			<input type="submit" name="Submit" value="Login">
		</form>
	</div>
	
	<ul>
		<li><a href="/login/preparaEnvioToken.do">Esqueceu sua senha?</a></li>
		<li><a href="/login/novo.do">Criar conta no sistema</a></li>
	</ul>
</div>

<div id="content">
	<h3>Sistema CRUD com login</h3>
	
	<p>Bem-vindo ao sistema CRUD com login desenvolvido usando o framework SimpleMVC.</p>
	
	<p>Se você já se inscreveu no sistema, entre com seu e-mail e sua senha na área de login ao lado.</p>
	
	<p>Se você não se inscreveu no site, clique na opção "Cria conta no sistema" e preencha o formulário.</p>
	
	<p>Seja bem-vindo!</p>
	
	<p>Desenvolvedores do sistema CRUD com login</p>
</div>