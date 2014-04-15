<%@include file="/jsp/helpers/template.jsp"%>

<style>
tr.header { background-color: black; color: white; }
tr.even { background-color: white; vertical-align: top; }
tr.odd { background-color: #ccc; vertical-align: top; }
th { text-align: left; }
td { font-size: 12px; text-align: left; }
</style>

<div id="content">
	<h3>Relatório de Seleção</h3>
	<c:set var="resultado" value="${requestScope.resultado}"/> 	
	
	<c:if test="${empty resultado}">
		<p>Nenhuma inscrição encontrada nestas condições.</p>
	</c:if>

	<c:if test="${not empty resultado}">
		<table class="relatorio">
		<tr class="header">
			<th class="colNomeCandidato">Nome Candidato</th>
			<th class="colPrimeiraLinha">Primeira Linha</th>
			<th class="colSegundaLinha">Segunda Linha</th>
			<th class="colMunicipio">Município</th>
			<th class="colEstado">Estado</th>
			<th class="colSexo">Sexo</th>
			<th class="colIdade">Idade</th>
			<th class="colCargo">Cargo</th>
			<th class="colInstituicao">Instituição</th>
			<th class="colTempoEmpresa">Tempo</th>
			<th class="colNomeCurso">Curso Graduação</th>
			<th class="colUniversidade">Universidade</th>
			<th class="colAnoGraduacao">Ano Graduação</th>
			<th class="colTopico">Tópico</th>
			<th class="colInteresse">Descrição</th>
		</tr>

		<c:forEach var="linha" varStatus="status" items="${resultado}">
			<c:if test="${status.index % 2 == 0}"><c:set var="classe" value="even"/></c:if>
			<c:if test="${status.index % 2 == 1}"><c:set var="classe" value="odd"/></c:if>

			<c:set var="numeroTopicos" value="${linha.inscricao.numeroTopicosInteresse}" scope="page" />
			<c:if test="${numeroTopicos == 0}"><c:set var="numeroTopicos" value="1"/></c:if>
			
			<tr class="${classe}">
				<td class="colNomeCandidato" rowspan="${numeroTopicos}">
					<c:out value="${linha.candidato.nome}"/>
				</td>
				<td class="colPrimeiraLinha" rowspan="${numeroTopicos}">
					<c:out value="${linha.inscricao.linhaPesquisaPrincipal.nome}"/>
				</td>
				<td class="colSegundaLinha" rowspan="${numeroTopicos}">
					<c:out value="${linha.inscricao.linhaPesquisaSecundaria.nome}"/>
				</td>
				<td class="colMunicipio" rowspan="${numeroTopicos}">
					<c:out value="${linha.candidato.municipio}"/>
				</td>
				<td class="colEstado" rowspan="${numeroTopicos}">
					<c:out value="${linha.candidato.estado}"/>
				</td>
				<td class="colSexo" rowspan="${numeroTopicos}">
					<c:out value="${linha.candidato.sexo}"/>
				</td>
				<td class="colIdade" rowspan="${numeroTopicos}">
					<c:out value="${linha.candidato.idade}"/>
				</td>
				<td class="colCargo" rowspan="${numeroTopicos}">
					<c:out value="${linha.candidato.cargo}"/>
				</td>
				<td class="colInstituicao" rowspan="${numeroTopicos}">
					<c:out value="${linha.candidato.instituicao}"/>
				</td>
				<td class="colTempoEmpresa" rowspan="${numeroTopicos}">
					<c:out value="${linha.candidato.tempoEmpresa}"/>
				</td>
				<td class="colNomeCurso" rowspan="${numeroTopicos}">
					<c:out value="${linha.candidato.nomeCurso}"/>
				</td>
				<td class="colUniversidade" rowspan="${numeroTopicos}">
					<c:out value="${linha.candidato.universidade.nome}"/>
				</td>
				<td class="colAnoGraduacao" rowspan="${numeroTopicos}">
					<c:out value="${linha.candidato.anoConclusao}"/>
				</td>
				
				<c:forEach var="topico" varStatus="statusTopico" items="${linha.inscricao.topicos}">
					<c:if test="${statusTopico.index != 0}">
						<tr class="${classe}">
							<td class="colTopico">
								<c:out value="${topico.topico.nome}"/>
							</td>
							<td class="colDescricao">
								<c:out value="${topico.descricao}"/>
							</td>
						</tr>
					</c:if>

					<c:if test="${statusTopico.index == 0}">
						<td class="colTopico">
							<c:out value="${topico.topico.nome}"/>
						</td>
						<td class="colDescricao">
							<c:out value="${topico.descricao}"/>
						</td>
					</c:if>
				</c:forEach>
			</tr>	
		</c:forEach>
		</table>
	</c:if>
</div>

<script src="/js/jquery/jquery.ui.min.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	$(".validaDocumentacao").click(validaDocumentacao);
	$(".invalidaDocumentacao").click(invalidaDocumentacao);
	$(".validaInscricao").click(validaInscricao);
	$(".invalidaInscricao").click(invalidaInscricao);
});

function validaDocumentacao () {
	var self = $(this);
	ajaxCallNoError("/inscricao/validaDocumentacao.do", "idInscricao=" + $(this).attr("index"), true, function() {
		self.removeClass("validaDocumentacao");
		self.addClass("invalidaDocumentacao");
		self.removeClass("icon-remove");
		self.addClass("icon-ok");
		self.unbind('click');
		self.bind('click', invalidaDocumentacao);
	});
}

function invalidaDocumentacao() {
	var self = $(this);
	ajaxCallNoError("/inscricao/invalidaDocumentacao.do", "idInscricao=" + $(this).attr("index"), true, function() {
		self.addClass("validaDocumentacao");
		self.removeClass("invalidaDocumentacao");
		self.addClass("icon-remove");
		self.removeClass("icon-ok");
		self.unbind('click');
		self.bind('click', validaDocumentacao);
	});
}

function validaInscricao() {
	var self = $(this);
	ajaxCallNoError("/inscricao/validaInscricao.do", "idInscricao=" + $(this).attr("index"), true, function() {
		self.removeClass("validaInscricao");
		self.addClass("invalidaInscricao");
		self.removeClass("icon-remove");
		self.addClass("icon-ok");
		self.unbind('click');
		self.bind('click', invalidaInscricao);
	});
}

function invalidaInscricao() {
	var self = $(this);
	ajaxCallNoError("/inscricao/invalidaInscricao.do", "idInscricao=" + $(this).attr("index"), true, function() {
		self.addClass("validaInscricao");
		self.removeClass("invalidaInscricao");
		self.addClass("icon-remove");
		self.removeClass("icon-ok");
		self.unbind('click');
		self.bind('click', validaInscricao);
	});
}
</script>
