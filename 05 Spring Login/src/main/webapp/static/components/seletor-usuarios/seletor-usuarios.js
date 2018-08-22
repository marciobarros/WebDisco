(function (angular) {
	var app2 = angular.module('SeletorUsuarios', ['ngTable']);
	
	app2.component('seletorUsuarios', {
		templateUrl: 'static/components/seletor-usuarios/seletor-usuarios.html',	
		controller: SeletorUsuariosController,
		bindings: {
			title: '<',
			modalName: '<',
		    onSelect: '<'
		}
	});
	
	function SeletorUsuariosController($http, $element, NgTableParams) {
		var $ctrl = this;
		$ctrl.filtro = {nome: ''};

		$ctrl.select = function(usuario) {
			$ctrl.onSelect(usuario);
		};

		$ctrl.carregaResumo = function (pagina, tamanho) {
			var url = 'usuario/resumo?page=' + pagina + "&size=" + tamanho + "&nome=" + $ctrl.filtro.nome;
			return $http.get(url);
		};

		$ctrl.$onInit = function() {
		    var elems = $element.children()[0];
		    M.Modal.init(elems, {});

		    $ctrl.tableUsuarios = new NgTableParams({}, {		
				getData: function (params) {
					return $ctrl.carregaResumo(params.page()-1, params.count()).then(function (data) {					
						params.total(data.data.TotalRecordCount);
						return data.data.Records;
					});
				}
			});
		};
		
		$ctrl.atualizaFiltro = function () {
			$ctrl.tableUsuarios.page(1);
			$ctrl.tableUsuarios.reload();
		};
	};
	
})(angular);