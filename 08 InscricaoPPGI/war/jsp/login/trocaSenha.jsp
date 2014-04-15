<%@include file="/jsp/helpers/template.jsp"%>
<mvc:redirectUnlogged page="/index.html" />

<link href="/css/simplemvc/password_strength.css" rel="stylesheet">

<div id="content">  
	<h3>Troca de senha</h3>

	<p>Entre uma vez com sua senha atual e duas vezes com a senha nova. A nova senha deve 
	ter, no m�nimo, 8 caracteres, sendo pelo menos uma letra e um n�mero. Senhas fortes 
	tamb�m cont�m letras min�sculas, mai�sculas e pelo menos um s�mbolo de pontua��o.</p>
	
	<form action="/login/trocaSenha.do" method="post">
		<label for="oldpassword">Entre com a senha antiga:</label>
		<input id="oldpassword" type="password" name="oldpassword" size="32" /><br>
		
		<label for="newpassword">Entre com a nova senha:</label>
		<input id="newpassword" type="password" name="newpassword" size="32"/>
		
		<label for="newpassword2">Repita a nova senha:</label>
		<input id="newpassword2" type="password" name="newpassword2" size="32"/>
		
		<input type="submit" name="btSubmit" value="Mude a Senha" />
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
