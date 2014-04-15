<%@include file="/jsp/helpers/template.jsp"%>

<style>
table.relatorio { width: 100% }
tr.header { background-color: black; color: white; }
tr.even { background-color: white; }
tr.odd { background-color: #ccc; }

th.colFicha { width: 20px; }
th.colDocumentacao { width: 30px; }
th.colInscricao { width: 30px; }
th.colNomeCandidato { text-align: left; }
th.colMunicipio { text-align: left; }
th.colNomeCurso { text-align: left; }
th.colUniversidade { text-align: left; }
th.colInstituicao { text-align: left; }
th.colCargo { text-align: left; }

td { font-size: 12px; }
td.colFicha { text-align: center; }
td.colDocumentacao { text-align: center; }
td.colInscricao { text-align: center; }
td.colNomeCandidato { text-align: left; }
td.colMunicipio { text-align: left; }
td.colNomeCurso { text-align: left; }
td.colUniversidade { text-align: left; }
td.colInstituicao { text-align: left; }
td.colCargo { text-align: left; }
</style>

<div id="content">
	<h3>Relatório de Inscrições</h3>
	<c:set var="resultado" value="${requestScope.resultado}"/> 	
	
	<c:if test="${empty resultado}">
		<p>Nenhuma inscrição encontrada nestas condições.</p>
	</c:if>

	<c:if test="${not empty resultado}">
		<table class="relatorio">
		<tr class="header">
			<th class="colFicha"></th>
			<th class="colDocumentacao">D</th>
			<th class="colInscricao">I</th>
			<th class="colNomeCandidato">Nome Candidato</th>
			<th class="colMunicipio">Município</th>
			<th class="colNomeCurso">Curso Graduação</th>
			<th class="colUniversidade">Universidade</th>
			<th class="colInstituicao">Instituição</th>
			<th class="colCargo">Cargo</th>
		</tr>		

		<c:forEach var="linha" varStatus="status" items="${resultado}">
			<c:if test="${status.index % 2 == 0}"><c:set var="classe" value="even"/></c:if>
			<c:if test="${status.index % 2 == 1}"><c:set var="classe" value="odd"/></c:if>
			<tr class="${classe}">
				<td class="colFicha">
					<a href="/inscricao/geraFicha.do?idInscricao=${linha.inscricao.id}" target="_blank"><i class="icon-list"></i></a>
				</td>
				<td class="colDocumentacao">
					<c:if test="${linha.inscricao.documentosPendente}"><i class="icon-remove icon-fixed-width validaDocumentacao" index="${linha.inscricao.id}" ></i></c:if>
					<c:if test="${!linha.inscricao.documentosPendente}"><i class="icon-ok icon-fixed-width invalidaDocumentacao" index="${linha.inscricao.id}" ></i></c:if>
				</td>
				<td class="colInscricao">
					<c:if test="${linha.inscricao.inscricaoVerificada}"><i class="icon-ok icon-fixed-width invalidaInscricao" index="${linha.inscricao.id}" ></i></c:if>
					<c:if test="${!linha.inscricao.inscricaoVerificada}"><i class="icon-remove icon-fixed-width validaInscricao" index="${linha.inscricao.id}" ></i></c:if>
				</td>
				<td class="colNomeCandidato">
					<c:out value="${linha.candidato.nome}"/>
				</td>
				<td class="colMunicipio">
					<c:out value="${linha.candidato.municipio}"/>
				</td>
				<td class="colNomeCurso">
					<c:out value="${linha.candidato.nomeCurso}"/>
				</td>
				<td class="colUniversidade">
					<c:out value="${linha.candidato.universidade.nome}"/>
				</td>
				<td class="colInstituicao">
					<c:out value="${linha.candidato.instituicao}"/>
				</td>
				<td class="colCargo">
					<c:out value="${linha.candidato.cargo}"/>
				</td>
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
