app.controller("formController", function ($log, $location, $http, NgTableParams, unidadeService) {
	var self = this;

	/*
	 * Filtros
	 */
	self.unidade = unidadeService.getUnidade();
	
	if (!self.unidade) {
        $location.path('/lista');
        return;
	}

	//window.M.updateTextFields();
	setTimeout(M.updateTextFields, 1);
	setTimeout(function() { 
		$('.modal').modal();
	}, 100);

	self.salva = function() {
/*		self.unidade.gestores = [];*/
		$http.post("unidade", self.unidade).then(function(data) {
			if (data.data.result == "OK") {
				M.toast({html: "Unidade funcional salva com sucesso!"});
		        $location.path('/lista');
			}
			else
				M.toast({html: data.data.message});
		});
	}
	
	self.lista = function() {
        $location.path('/lista');
	}
	
	self.removeGestor = function(id) {
		
	}
	
	self.adicionaUsuarioGestor = function(resumo) {
		
	}
	
	/*
	 * Prepara a tabela
	 */
	self.tableGestores = new NgTableParams({}, {
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