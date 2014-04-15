<%@include file="/jsp/helpers/template.jsp"%>

<div id="content">  
	<h3>Envio de senha</h3>

	<p>Entre com seu e-mail no campo abaixo e o sistema lhe enviará sua senha.</p>

	<form action="/login/enviaSenha.do" method="post">
		<label for="email">E-mail:</label>
		<input type="text" name="email" value="${requestScope.email}" /><br>
		<input type="submit" value="Envia Senha"/>
	</form>
</div>