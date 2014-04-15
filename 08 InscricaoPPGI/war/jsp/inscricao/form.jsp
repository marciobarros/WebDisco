<%@include file="/jsp/helpers/template.jsp"%>

<style>
#linha { width: 500px; }
#observacoes { width: 500px; }
#dlgNovoTopico form textarea { margin-bottom: 0px; }
#dlgNovoTopico form { margin-bottom: 0px; }
#novoTopicoPesquisa { width: 580px; }
#novoComentario { width: 565px; height: 300px; }
#dadosInscricao { /*width: 100%; */padding: 8px; margin-bottom: 8px; }
#dadosInscricao:hover { background-color: #eee; }
#divEditaInscricao { float: right; }
#cmdEditaInscricao { font-size: 10px; text-decoration: none; margin-top: 4px; margin-right: 4px; display: none; }
#cmdEditaInscricao:hover { text-decoration: underline; }
#formInscricao { display: none; }
#tbTopicos { width: 100%; text-align: left; border: 1px solid gray; margin-bottom: 4px; }
#tbTopicos tr.header { background-color: black; color: white; font-weight: bold; }
#tbTopicos tr.header th.nomeTabelaTopicos { width: 200px; }
#tbTopicos tr.header th.comandoTabelaTopicos { width: 16px; }
#tbTopicos tr td { vertical-align: top; font-size: 10px; line-height: 14px; padding-top: 4px; padding-bottom: 4px; }
#tbTopicos tr.even { background-color: white; }
#tbTopicos tr.odd { background-color: #eee; }
</style>

<div id="content">
	<c:set var="inscricao" value="${requestScope.inscricao}" scope="page" />
	
	<h3>Inscri��o em Edital de Vagas</h3>	
	
	<c:if test="${inscricao.id <= 0}">
		<p>Voc� ainda n�o est� inscrito no �ltimo edital de vagas dispon�vel. No formul�rio abaixo, selecione as linhas de pesquisa
		do PPGI que representam seus interesses de pesquisa e, se necess�rio, entre com observa��es sobre a sua inscri��o. Depois
		de preencher este formul�rio, voc� poder� escolher um ou mais t�picos de pesquisa que sejam do seu interesse.</p>
	</c:if>
	
	<c:if test="${inscricao.id > 0}">
		<p>Voc� est� inscrito no edital de vagas com as informa��es apresentadas abaixo. Se quiser alterar estas informa��es, passe
		o mouse sobre elas e clique no comando "Editar", que aparecer� a direita. As informa��es poder�o ser alteradas at� que o
		edital de vagas seja fechado pelo administradror do sistema.</p>

		<div id="dadosInscricao">
			<div id="divEditaInscricao"><a href="javascript:void(0)" id="cmdEditaInscricao">Edita</a></div>
			<p><b>Linha Principal</b>: <c:out value="${inscricao.linhaPesquisaPrincipal.nome}"/></p>
			<p><b>Linha Secund�ria</b>: <c:out value="${inscricao.linhaPesquisaSecundaria.nome}"/></p>
			<p><b>Observa��es</b>: <c:out value="${inscricao.observacoes}"/> <c:if test="${empty inscricao.observacoes}"><i>Nenhuma observa��o.</i></c:if></p>
		</div>
	</c:if>

	<div id="formInscricao">			
		<form action="/inscricao/salva.do" method="post">
			<label for="linhaPrincipal">Linha de pesquisa principal:</label>
			<ppgi:seletorLinhaPesquisa name="linhaPrincipal" id="linhaPrincipal" value="${inscricao.linhaPesquisaPrincipal.codigo}" style="width: 514px;" blankOption="Selecione uma linha de pesquisa ..."/>
	
			<label for="linhaSecundaria">Linha de pesquisa secund�ria:</label>
			<ppgi:seletorLinhaPesquisa name="linhaSecundaria" id="linhaSecundaria" value="${inscricao.linhaPesquisaSecundaria.codigo}" style="width: 514px;" blankOption="Selecione uma linha de pesquisa ..."/>

			<label for="observacoes">Observa��es:</label>
			<textarea name="observacoes" id="observacoes" style="width: 500px; height: 60px;">${inscricao.observacoes}</textarea><br>
			<input type="submit" name="btSubmit" value="Enviar Dados" />
		</form>
	</div>

	<c:if test="${inscricao.id > 0}">
		<p>Agora voc� precisa selecionar um ou mais t�picos de pesquisa que sejam do seu interesse. Clique no bot�o "Novo T�pico"
		abaixo para registrar um t�pico de interesse. Depois, voc� poder� incluir outros t�picos, editar t�picos previamente
		selecionados, remover ou reordenar estes t�picos. Coloque os t�picos em ordem, conforme seus interesses de pesquisa.</p>

		<table id="tbTopicos">
		</table>
	
		<p><a href="#" class="btn btn-small" id="btAdiciona"><i class="icon-bookmark"></i> Novo T�pico</a></p>

		<br>
		<p>Depois de registrar seus t�picos de interesse, voc� poder� gerar a ficha de inscri��o. Esta ficha deve ser entregue
		na Secretaria do PPGI, junto com os outros documentos necess�rios para a inscri��o no Edital de Vagas para nosso curso
		de Mestrado. Para gerar a ficha, pressione o bot�o abaixo.</p>

		<p><a href="/inscricao/geraFicha.do?idInscricao=${inscricao.id}" target="_blank" class="btn btn-small" id="btGeraFicha"><i class="icon-list"></i> Gera Ficha</a></p>
	</c:if>	
</div>

<script src="/js/jquery/jquery.ui.min.js"></script>
<script src="/js/simplemvc/simplemvc.modaldialog.js"></script>
<script src="/js/forms.js"></script>

<c:if test="${inscricao.id <= 0}">
<script type="text/javascript">
$(document).ready(function() {
	$("#formInscricao").show();
});
</script>
</c:if>	

<script type="text/javascript">
$(document).ready(function() {

	$("#cmdEditaInscricao").click(function() {
		$("#dadosInscricao").hide();
		$("#formInscricao").show();
	});
	
	$("#dlgNovoTopico").modalDialog({width: 600, autoOpen: false, afterCommandFunction: refresh });
	$("#btAdiciona").click(function() {
		$("#dlgNovoTopico").dialog("open");	
	});
	$("#dadosInscricao").hover(function () {
		$("#cmdEditaInscricao").show();
	}, function() {
		$("#cmdEditaInscricao").hide();
	});
	
	$(".editaTopico").click(function() {
		$("dlgNovoTopico").find("#novoTopicoPesquisa")
	});
	
	ajaxCall("/inscricao/listaTopicos.do", "", true, function(data) {
		atualizaTopicos(data);
	});
});

function criaComandoEdicao(data) {
	
	var a = $("<a>")
		.attr("href", "javascript:void(0)")
		.append($("<i class='icon-pencil'/>"))
		.click(function() {
			$("#novoTopicoPesquisa").val(data.codigo);
			$("#novoComentario").val(data.descricao);
			$("#dlgNovoTopico").dialog("open");
		});
	
	return a;
}

function criaComandoRemocao(indice) {
	
	var a = $("<a>")
		.attr("href", "javascript:void(0)")
		.attr("index", indice)
		.append($("<i class='icon-remove'/>"))
		.click(function() {
			ajaxCall("/inscricao/removeTopico.do", "indice=" + $(this).attr('index'), true, atualizaTopicos);
		});
	
	return a;
}

function criaComandoSubir(indice) {
	
	if (indice == 0)
		return $("<span>");
	
	var a = $("<a>")
		.attr("href", "javascript:void(0)")
		.attr("index", indice)
		.append($("<i class='icon-hand-up'/>"))
		.click(function() {
			ajaxCall("/inscricao/sobeTopico.do", "indice=" + $(this).attr('index'), true, atualizaTopicos);
		});
	
	return a;
}

function criaComandoDescer(indice, limite) {
	
	if (indice == limite-1)
		return $("<span>");
	
	var a = $("<a>")
		.attr("href", "javascript:void(0)")
		.attr("index", indice)
		.append($("<i class='icon-hand-down'/>"))
		.click(function() {
			ajaxCall("/inscricao/desceTopico.do", "indice=" + $(this).attr('index'), true, atualizaTopicos);
		});
	
	return a;
}

function atualizaTopicos(data) {
	var numeroTopicos = data.length;

	var tabela = $("#tbTopicos")
		.empty();

	if (numeroTopicos > 0)
	{
		$("<tr class='header'>")
			.append("<th class='nomeTabelaTopicos'>T�pico</th>")
			.append("<th>Descri��o</th>")
			.append("<th class='comandoTabelaTopicos'></th>")
			.append("<th class='comandoTabelaTopicos'></th>")
			.append("<th class='comandoTabelaTopicos'></th>")
			.append("<th class='comandoTabelaTopicos'></th>")
			.appendTo(tabela);
	}

	for (var i = 0; i < numeroTopicos; i++)
	{
		var cmdEdita = $("<td>").append(criaComandoEdicao(data[i]));
		var cmdRemove = $("<td>").append(criaComandoRemocao(i));
		var cmdSobe = $("<td>").append(criaComandoSubir(i));		
		var cmdDesce = $("<td>").append(criaComandoDescer(i, numeroTopicos));
		var classe = (i % 2 == 0) ? "even" : "odd";
		
		$("<tr class='" + classe + "'>")
			.append("<td>" + data[i].nome + "</td>")
			.append("<td>" + data[i].descricao + "</td>")
			.append(cmdEdita)
			.append(cmdRemove)
			.append(cmdSobe)
			.append(cmdDesce)
			.appendTo(tabela);
	}
	
	tabela.find("tr").hover(function() {
		if (!$(this).hasClass("header"))
			$(this).css('background-color', 'lightblue');
	}, function() {
		if (!$(this).hasClass("header"))
			$(this).css('background-color', '');
	});
}

function refresh() {
	window.location = "/inscricao/prepara.do";
}
</script>

<div id="dlgNovoTopico" title='Novo T�pico de Pesquisa' style='display: none;'>
	<form action="/inscricao/salvaTopico.do">
		<ppgi:seletorTopicoPesquisa name="topicoPesquisa" id="novoTopicoPesquisa" value="" blankOption="Selecione um t�pico de pesquisa ..."/>
		<textarea name="comentario" id="novoComentario" rows="4" cols="60"></textarea>
	</form>
</div>