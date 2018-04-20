App.controller("TopNavigatorController", function ($scope, topNavigatorDataService, $log, $window) {

	var self = this;
	/*self.editalSelecionado = -1;
	self.editais = [];*/
	self.csrf = csrf;
	
	/**
	 * Abre a janela para selecionar o edital
	 */
	/*self.abreJanelaSelecaoEdital = function() {
		topNavigatorDataService.carrega().then(function(data) {
			if (!checkForErrors(data.data)) {
				self.editais = data.data.data;
			}
		});

		dialog.showModal();
	}*/
	
	/**
	 * Seleciona um edital para trabalhar
	 */
	/*self.selecionaEdital = function() {
		topNavigatorDataService.mudaEditalSelecionado(self.editalSelecionado, csrf).then(function(data) {
			$window.location.href = contextPath + "/?message=edital.muda.selecionado.sucesso";
		});
	}*/

	/**
	 * Programa principal
	 */
    /*var dialog = document.querySelector('dialog');
    
    if (dialog) {
	    dialog.querySelector('.close').addEventListener('click', function() {
	        dialog.close();
	    });	 
    }*/
});