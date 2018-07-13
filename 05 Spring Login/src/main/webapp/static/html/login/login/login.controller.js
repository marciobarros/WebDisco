app.controller("loginController", function ($http) {
	var self = this;
	
	self.data = {
    	email: "marcio.barros@gmail.com",
    	senha: "Ab#123456"
    };
	
	self.envia = function() {
		var username = window.encodeURIComponent(self.data.email);
		var password = window.encodeURIComponent(self.data.senha);

		$http.post("login/authenticate?username=" + username + "&password=" + password, "").then(function(data) { 
			console.log(data);
			if (data.data.result == "OK") {
				M.toast({html: "Logado?"});
				window.location = "#!";
			}
			else
				M.toast({html: data.data.message});
		});
	}
});