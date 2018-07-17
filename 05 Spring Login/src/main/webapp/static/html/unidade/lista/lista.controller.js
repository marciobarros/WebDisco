app.controller("listaController", function ($http, NgTableParams) {
	var self = this;

	/*
	 * Filtros
	 */
	self.filtros = {
		sigla: "",
		nome: ""
	}
	
	/*
	 * Unidade selecionada
	 */
	self.unidade = null;
	
	/*
	 * Altera os filtros de consulta
	 */
	self.atualizaFiltro = function () {
		atualizaLista();
	}
	
	/*
	 * Atualiza a lista de unidades
	 */
	var atualizaLista = function() {
		self.tableParams.reload();
	}
	
	/**
	 * Lista as unidades
	 */
	var lista = function(page, size, filtros) {
		return $http.get("cd?page=" + page + "&size=" + size + "&nome=" + (filtros.nome || "") + "&sigla=" + (filtros.sigla || ""));
	}
	
	/*
	 * Cria uma nova unidade
	 */
	self.nova = function() {
		self.unidade = { id: -1, sigla: "", nome: "" };
		mostraFormulario();
	}
	
	/*
	 * Edita uma unidade
	 */
	self.edita = function(item) {
		self.unidade = angular.copy(item);
		mostraFormulario();
	}
	
	/*
	 * Remove o CD selecionado
	 */
	self.remove = function(id) {
		return $http.delete("unidade/" + id);
	}
	
	/*
	 * Mostra a lista
	 */
	/*var mostraLista = function() {
		setTimeout(function() {
			$("html,body").animate({scrollTop: $("nav").offset().top}, "slow");
		}, 0);
	}*/
	
	/*
	 * Mostra o formulario
	 */
	/*var mostraFormulario = function() {
		setTimeout(function() {
			$("html,body").animate({scrollTop: $("div.form").offset().top}, "slow");
		}, 0);
	}*/

	/*
	 * Prepara a tabela
	 */
	self.tableParams = new NgTableParams({}, {
		getData: function (params) {
			return lista(params.page() - 1, params.count(), self.filtros).then(function (data) {
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