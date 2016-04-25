<%@include file="/jsp/helpers/template.jsp"%>

<link href="/css/simplemvc/simplemvc.css" rel="stylesheet">

<div id="content">  
	<h3>Reinicializa��o de senha</h3>

	<p>Entre duas vezes com a senha nova. A nova senha deve ter, no m�nimo, 8 caracteres, 
	sendo pelo menos uma letra e um n�mero. Senhas fortes tamb�m cont�m letras min�sculas, 
	mai�sculas e pelo menos um s�mbolo de pontua��o.</p>
	
	<form action="/login/executaResetSenha.do" method="post">
		<input id="token" name="token" type="hidden" value="${requestScope.token}"/>
		<input id="email" name="email" type="hidden" value="${requestScope.email}"/>
		
		<label for="newpassword">Entre com a nova senha:</label>
		<input id="newpassword" type="password" name="newpassword" />
		
		<label for="newpassword2">Repita a nova senha:</label>
		<input id="newpassword2" type="password" name="newpassword2" />
		
		<br/>
		<button type="submit" class="btn btn-default">Mude a Senha</button>
		
		<a href="/login/login.do">
			<button type="button" class="btn btn-default">Retorna</button>
		</a>
	</form>
</div>

<script type="text/javascript" src="/js/simplemvc/simplemvc.password.strength.js"></script>
<script type="text/javascript" src="/js/simplemvc/simplemvc.password.repeat.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	$("#newpassword").passStrength();
	$("#newpassword2").passRepetition({ source: "#newpassword" });
});
</script>
