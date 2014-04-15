<%@include file="/jsp/helpers/template.jsp"%>

<style>
table { width: 600px; }
td { width: 350px; }
.ui-datepicker-trigger { margin-bottom: 10px; }
</style>

<div id="content">
	<c:set var="usuario" value="${requestScope.item}" scope="page" />
	<c:set var="dataNascimento"><fmt:formatDate value='${usuario.dataNascimento}' pattern='dd/MM/yyyy'/></c:set>
	
	<h3>Criação de Conta no Sistema</h3>
	
	<p>Entre com os seus dados no formulário abaixo:</p>
	
	<form action="/login/salva.do" method="post">
		<input type="hidden" name="id" value="${usuario.id}"/>

		<table>
		<tr><td colspan="2">
			<label for="nome">Nome*:</label>
			<input type="text" name="nome" id="nome" value="${usuario.nome}" class="long" /><br>
		</td></tr>
		
		<tr><td colspan="2">
			<c:if test="${usuario.id <= 0}">
				<label for="email">E-mail*:</label>
				<input type="text" name="email" id="email" value="${usuario.email}" class="long" /><br>
			</c:if>
			<c:if test="${usuario.id > 0}">
				<label for="email">E-mail*:</label>
				<p style="margin-bottom: 8px; font-weight: bold;"><c:out value="${usuario.email}"/></p>
			</c:if>
		</td></tr>
		
		<tr><td colspan="2">
			<label for="endereco">Endereço*:</label>
			<input type="text" name="endereco" id="endereco" value="${usuario.endereco}" class="long" /><br>
		</td></tr>
		
		<tr><td colspan="2">
			<label for="complemento">Complemento:</label>
			<input type="text" name="complemento" id="complemento" value="${usuario.complemento}" class="long" /><br>
		</td></tr>
		
		<tr><td>		
			<label for="estado">Estado*:</label>
			<ppgi:seletorEstado name="estado" value="${usuario.estado}" id="estado" blankOption="Selecione o estado ..."/><br>
		</td><td>
			<label for="municipio">Município*:</label>
			<select name="municipio" id="municipio"><option value="-1">Selecione o município ...</option></select><br>
		</td></tr>
		
		<tr><td colspan="2">
			<label for="cep">CEP*:</label>
			<input type="text" name="cep" id="cep" value="${usuario.cep}" /><br>
		</td></tr>
		
		<tr><td colspan="2">
			<label for="nacionalidade">Nacionalidade*:</label>
			<input type="text" name="nacionalidade" id="nacionalidade" value="${usuario.nacionalidade}" /><br>
		</td></tr>
		
		<tr><td>
			<label for="sexo">Sexo*:</label>
			<mvc:simpleSelector name="sexo" optionNames="Selecione ...;Masculino;Feminino" optionValues="IND;M;F" value="${usuario.sexo.codigo}"/><br>
		</td><td>
			<label for="dataNascimento">Data de nascimento*:</label>
			<input type="text" name="dataNascimento" id="dataNascimento" value="${dataNascimento}" /><br>
		</td></tr>
		
		<tr><td>
			<label for="identidade">Identidade*:</label>
			<input type="text" name="identidade" id="identidade" value="${usuario.identidade}" /><br>
		</td><td>
			<label for="emissorIdentidade">Emissor da identidade*:</label>
			<input type="text" name="emissorIdentidade" id="emissorIdentidade" value="${usuario.emissorIdentidade}" /><br>
		</td></tr>
		
		<tr><td>
			<label for="cpf">CPF*:</label>
			<input type="text" name="cpf" id="cpf" value="${usuario.cpf}" /><br>
		</td><td>
			<label for="tituloEleitor">Título de eleitor*:</label>
			<input type="text" name="tituloEleitor" id="tituloEleitor" value="${usuario.tituloEleitor}" /><br>
		</td></tr>
		
		<tr><td colspan="2">
			<label for="inscricaoPoscomp">Inscrição no POSCOMP:</label>
			<input type="text" name="inscricaoPoscomp" id="inscricaoPoscomp" value="${usuario.inscricaoPoscomp}" /><br>
		</td></tr>
		
		<tr><td>
			<label for="telefoneFixo">Telefone fixo*:</label>
			<input type="text" name="telefoneFixo" id="telefoneFixo" value="${usuario.telefoneFixo}" /><br>
		</td><td>
			<label for="telefoneCelular">Telefone celular*:</label>
			<input type="text" name="telefoneCelular" id="telefoneCelular" value="${usuario.telefoneCelular}" /><br>
		</td></tr>
		
		<tr><td colspan="2">
			<label for="nomeCurso">Nome do curso superior ou titulação mais recente*:</label>
			<input type="text" name="nomeCurso" id="nomeCurso" value="${usuario.nomeCurso}" class="long" /><br>
		</td></tr>
		
		<tr><td colspan="2">
			<label for="universidade">Universidade em que o curso superior foi concluído*:</label>
			<ppgi:seletorUniversidade name="universidade" value="${usuario.universidade.codigo}" id="universidade" blankOption="Selecione a universidade ..." classe="long" /><br>
		</td></tr>

		<tr><td colspan="2">
			<label for="anoConclusao">Ano de conclusão*:</label>
			<input type="text" name="anoConclusao" id="anoConclusao" value="${usuario.anoConclusao}" /><br>
		</td></tr>
		
		<tr><td colspan="2">
			<label for="instituicao">Empresa onde trabalha:</label>
			<input type="text" name="instituicao" id="instituicao" value="${usuario.instituicao}" class="long" /><br>
		</td></tr>
		
		<tr><td>
			<label for="cargo">Cargo que ocupa na empresa:</label>
			<input type="text" name="cargo" id="cargo" value="${usuario.cargo}" /><br>
		</td><td>
			<label for="tempoEmpresa">Tempo na empresa:</label>
			<input type="text" name="tempoEmpresa" id="tempoEmpresa" value="${usuario.tempoEmpresa}" /> em meses<br>
		</td></tr>
		
		<tr><td colspan="2">
			<label for="observacoes">Observações:</label>
			<textarea name="observacoes" id="observacoes" style="width: 500px; height: 60px;">${usuario.observacoes}</textarea>
		</td></tr>
		</table>		

		<input type="submit" name="btSubmit" value="Enviar Dados" /><br>
	</form>
</div>

<script src="/js/jquery/jquery.ui.min.js"></script>
<script src="/js/jquery/jquery.maskedinput.js"></script>
<script src="/js/jquery/jquery.meiomask.js"></script>
<script src="/js/simplemvc/simplemvc.datefield.js"></script>
<script src="/js/simplemvc/simplemvc.phonefield.js"></script>
<script src="/js/simplemvc/simplemvc.intfield.js"></script>
<script src="/js/forms.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	$("#cpf").mask("999.999.999/99");
	$("#cep").mask("99.999-999");
	$("#tempoEmpresa").setIntField();
	$("#anoConclusao").setIntField();
	$("#dataNascimento").setDateField();
	$("#telefoneFixo").phoneField();
	$("#telefoneCelular").phoneField();
	
	$("#estado").change(function() {
		capturaMunicipios("estado", "municipio", "${item.municipio}");
	});
	$("#estado").trigger("change");	
});
</script>