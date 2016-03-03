<%@include file="/jsp/helpers/template.jsp"%>

<div id="content">  
	<h3>Esqueceu sua senha</h3>

	<p>Entre com seu e-mail no campo abaixo e o sistema lhe enviará uma mensagem para troca de senha.</p>

	<form action="/login/envioToken.do" method="post">
		<label for="email">E-mail:</label>
		<input type="text" name="email" value="${requestScope.email}" /><br>

		<button type="submit" class="btn btn-default">Envia Senha</button>
		
		<a href="/login/login.do">
			<button type="button" class="btn btn-default">Retorna</button>
		</a>
	</form>
</div>