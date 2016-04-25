<%@include file="/jsp/helpers/template.jsp"%>

<link type="text/css" rel="stylesheet" href="/css/twitter/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="/css/jtable/themes/lightcolor/blue/jtable.css" />
<link type="text/css" rel="stylesheet" href="/css/simplemvc/simplemvc.css" />

<script type="text/javascript" src="/js/jquery/jquery.jtable.js"></script>
<script type="text/javascript" src="/js/simplemvc/simplemvc.edithint.js"></script>

<script type="text/javascript" src="/jsp/usuario/lista.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	new ListUsuariosPanel().init();
});
</script>

<div id="content">  
	<div class="filtro">
		<div class="left">
			<input id="edNome" size="20" title="Nome do usuário"></input> 
			<input id="edEmail" size="20" title="E-mail do usuário"></input> 
			<crud:seletorEstado name="cbEstado" value="" blankOption="Selecione um estado ..."/>
			<button id="btAplica">Aplica</button>
		</div>
		
		<div class="right">
		  <button class="btn btn-primary btn-small" type="button" id="btNovo">Novo Usuário</button>
		  <button class="btn btn-primary btn-small" type="button" id="btRetorna">Retorna</button>
		</div>
		
		<div class="clear">
		</div> 
	</div>
	
	<div id="items">
	</div>
</div>