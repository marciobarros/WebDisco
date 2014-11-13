<%@include file="/jsp/helpers/template.jsp"%>

<link href="/css/simplemvc/simplemvc.css" rel="stylesheet">

<style>
table { width: 600px; }
td { width: 350px; }
</style>

<div id="content">
	<c:set var="usuario" value="${requestScope.item}" scope="page" />
	
	<h3>Criação de Conta no Sistema</h3>
	
	<p>Entre com os seus dados no formulário abaixo:</p>
	
	<form action="/login/salva.do" method="post">
		<table>
		<tr>
		<td colspan="2">
			<label for="nome">Nome*:</label>
			<input type="text" name="nome" id="nome" value="${usuario.nome}" class="long" /><br>
		</td>
		</tr>
		
		<tr>
		<td colspan="2">
			<label for="email">E-mail*:</label>
			<input type="text" name="email" id="email" value="${usuario.email}" class="long" /><br>
		</td>
		</tr>

		<tr>
		<td>
			<label for="newpassword">Entre com uma senha:</label>
			<input id="newpassword" type="password" name="password" style="width: 100px"/>
		</td>
		<td>
			<label for="newpassword2">Repita a senha:</label>
			<input id="newpassword2" type="password" name="password2" style="width: 100px"/>
		</td>
		</tr>		
		
		<tr>
		<td colspan="2">
			<label for="endereco">Endereço*:</label>
			<input type="text" name="endereco" id="endereco" value="${usuario.endereco}" class="long" /><br>
		</td>
		</tr>
		
		<tr>
		<td colspan="2">
			<label for="complemento">Complemento:</label>
			<input type="text" name="complemento" id="complemento" value="${usuario.complemento}" class="long" /><br>
		</td>
		</tr>
		
		<tr>
		<td>		
			<label for="estado">Estado*:</label>
			<crud:seletorEstado name="estado" value="${usuario.estado}" id="estado" blankOption="Selecione o estado ..."/><br>
		</td>
		<td>
			<label for="municipio">Município*:</label>
			<select name="municipio" id="municipio"><option value="-1">Selecione o município ...</option></select><br>
		</td>
		</tr>
		
		<tr>
		<td colspan="2">
			<label for="cep">CEP*:</label>
			<input type="text" name="cep" id="cep" value="${usuario.cep}" /><br>
		</td>
		</tr>
		
		<tr>
		<td>
			<label for="telefoneFixo">Telefone fixo*:</label>
			<input type="text" name="telefoneFixo" id="telefoneFixo" value="${usuario.telefoneFixo}" /><br>
		</td>
		<td>
			<label for="telefoneCelular">Telefone celular*:</label>
			<input type="text" name="telefoneCelular" id="telefoneCelular" value="${usuario.telefoneCelular}" /><br>
		</td>
		</tr>
		</table>		

		<button type="submit" class="btn btn-default">Envia Dados</button>
		
		<a href="/login/login.do">
			<button type="button" class="btn btn-default">Retorna</button>
		</a>
	</form>
</div>

<script type="text/javascript" src="/js/jquery/jquery.maskedinput.js"></script>
<script type="text/javascript" src="/js/simplemvc/simplemvc.phonefield.js"></script>
<script type="text/javascript" src="/js/simplemvc/simplemvc.password.strength.js"></script>
<script type="text/javascript" src="/js/simplemvc/simplemvc.password.repeat.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	$("#newpassword").passStrength();
	$("#newpassword2").passRepetition({ source: "#newpassword" });
	$("#cep").mask("99.999-999");
	$("#telefoneFixo").phoneField();
	$("#telefoneCelular").phoneField();
	
	$("#estado").change(function() {
		capturaMunicipios("estado", "municipio", "${item.municipio}");
	});
	$("#estado").trigger("change");	
});
</script>