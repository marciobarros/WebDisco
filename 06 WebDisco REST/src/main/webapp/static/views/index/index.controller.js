App.controller("cdController", function ($scope, dataService, NgTableParams) {
	var self = this;

	/*
	 * Filtros
	 */
	self.filtros = {
		nome: ""
	}
	
	/*
	 * CD selecionado
	 */
	self.cd = null;
	
	/*
	 * Altera os filtros de consulta
	 */
	self.atualizaFiltro = function () {
		atualizaLista();
	}
	
	/*
	 * Atualiza a lista de CDs
	 */
	var atualizaLista = function() {
		$scope.tableParams.reload();
	}
	
	/*
	 * Edita um CD
	 */
	self.edita = function(item) {
		self.cd = angular.copy(item);
	}
	
	/*
	 * Cria um novo CD
	 */
	self.novo = function() {
		self.cd = { id: -1, title: "", price: "1", stock: "0" };
	}
	
	/*
	 * Salva os dados do CD editado
	 */
	self.salva = function() {
		dataService.salva(self.cd).then(function(data) { 
			if (data.data.result == "OK") {
				atualizaLista();
				self.cd = null;
			}
			else
				M.toast(data.data.message, 4000);
		});
	}
	
	/*
	 * Remove o CD selecionado
	 */
	self.remove = function(id) {
		dataService.remove(id).then(atualizaLista);
	}

	/*
	 * Prepara a tabela
	 */
	$scope.tableParams = new NgTableParams({}, {
		getData: function (params) {
			return dataService.lista({
				page: params.page() - 1,
				size: params.count(),
				nome: self.filtros.nome
			}).then(function (data) {
				if(data.data.TotalRecordCount == 0) {
					self.noSite = true;
				}
				else {
					params.total(data.data.TotalRecordCount);
					self.noSite = false;
					return data = data.data.Records;
				}
			});
		}
	});
});