<%@include file="/jsp/helpers/template.jsp"%>

<div id="content">
	<h3>Relat�rio de Inscri��es</h3>	
	
	<form action="/inscricao/executaRelatorioInscritos.do" method="post">
		<label for="linha">Parte do nome do candidato:</label>
		<input type="text" name="filtroNomeCandidato"/>
	
		<label for="linhaPesquisa">Linha de pesquisa:</label>
		<ppgi:seletorLinhaPesquisa name="filtroLinhaPesquisa" value="" blankOption="Todas as linhas de pesquisa ..."/>
	
		<label for="edital">Edital de vagas:</label>
		<mvc:simpleSelector name="filtroEdital" colNames="nomeEdital" colValues="valorEdital" value=""/>

		<label for="statusDocumentacao">Estado da documenta��o:</label>
		<mvc:simpleSelector name="filtroDocumentacao" optionNames="Todos;Validada;N�o validada" optionValues="-1;0;1" value=""/>

		<label for="statusInscricao">Estado da inscri��o:</label>
		<mvc:simpleSelector name="filtroInscricao" optionNames="Todas;Validadas;N�o validadas" optionValues="-1;0;1" value=""/>
		
		<br><input type="submit" name="btSubmit" value="Enviar Dados" />
	</form>
</div>

<script src="/js/jquery/jquery.ui.min.js"></script>
