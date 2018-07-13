app.controller("forgotController", function ($http) {
	var self = this;
	
	self.data = {
    	email: "",
    };
	
	self.envia = function() {
		$http.post("login/forgot", self.data).then(function(data) { 
			if (data.data.result == "OK") {
				M.toast({html: "E-mail para troca de senha enviado com sucesso!"});
				window.location = "#!";
			}
			else
				M.toast({html: data.data.message});
		});
	}
});