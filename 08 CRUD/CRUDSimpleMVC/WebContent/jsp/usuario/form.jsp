<%@include file="/jsp/helpers/template.jsp"%>

<style>
table { width: 600px; }
td { width: 350px; }
</style>

<div id="content">
	<c:set var="usuario" value="${requestScope.usuario}" scope="page" />
	
	<h3>Criação ou edição de usuário</h3>
	
	<form action="/usuario/salva.do" method="post">
		<input type="hidden" name="id" value="${usuario.id}" />

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
			<crud:seletorEstado name="estado" value="${usuario.estado.sigla}" id="estado" blankOption="Selecione o estado ..."/><br>
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

		<button type="submit" class="btn btn-default">Salva</button>
		
		<a href="/login/login.do">
			<button type="button" class="btn btn-default">Retorna</button>
		</a>
	</form>
</div>

<script type="text/javascript" src="/js/jquery/jquery.maskedinput.js"></script>
<script type="text/javascript" src="/js/simplemvc/simplemvc.phonefield.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	$("#cep").mask("99.999-999");
	$("#telefoneFixo").phoneField();
	$("#telefoneCelular").phoneField();
	
	$("#estado").change(function() {
		capturaMunicipios("estado", "municipio", "${usuario.municipio}");
	});
	$("#estado").trigger("change");	
});
</script>