app.controller("createController", function ($http) {
	var self = this;
	
	self.data = {
    	nome: "",
    	email: "",
    	senha: "",
    	senhaRepetida: ""
    };
	
	self.envia = function() {
		$http.post("login/create", self.data).then(function(data) { 
			if (data.data.result == "OK") {
				M.toast({html: "Conta criada com sucesso!"});
				window.location = "#!";
			}
			else
				M.toast({html: data.data.message});
		});
	}
});