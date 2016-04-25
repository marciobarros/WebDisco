/*
 * Painel com lista de usuarios
 */
ListUsuariosPanel = function() {

	/*
	 * Inicializa o painel
	 */
	function init() {
		preparaFiltros();
		preparaTabela();
	}
	
	/*
	 * Prepara os filtros de usuarios
	 */
	function preparaFiltros() {
		$("#edNome").edithint();
		$("#edEmail").edithint();
	
		$("#edNome, #edEmail, #cbEstado").keydown(function(event) {
			if (event.keyCode == 13)
				$("#btAplica").trigger('click');
		});
		
		$("#btAplica").click(function(e) {
		    $('#items').jtable('load');    
		});
	}
	
	/*
	 * Prepara a tabela onde sao apresentados os usuarios
	 */
	function preparaTabela() {
		$('#items').jtable({
	        paging: true,
	        pageSize: 25,
	        
	        fields: {
	            id: { key: true, list: true, title: 'ID' },
	            nome: { title: 'Nome', width: '25%' },
	            email: { title: 'Email', width: '25%' },
	            estado: { title: 'Estado', width: '25%' },
	            municipio: { title: 'Munic\u00edpio', width: '25%' }
	        },
	        
	        editFunction: function (id) {
	    		window.location = "/usuario/edita.do?id=" + id;
			},
	
	        deleteAction: "/usuario/remove.do",
	
			createListAction: function() {
				var filtroNome = escape($("#edNome").val());
				var filtroEmail = escape($("#edEmail").val());
				var filtroEstado = escape($("#cbEstado").val());
				return '/usuario/lista.do?filtroNome=' + filtroNome + '&filtroEmail=' + filtroEmail + '&filtroEstado=' + filtroEstado;
	        }
	    });
		
	    $('#items').jtable('load');    

	    $("#btNovo").click(function() {
			window.location = "/usuario/novo.do";
		});

	    $("#btRetorna").click(function() {
			window.location = "/login/homepage.do";
		});
	}
	
	return { init: init };
}