App.factory("dataService", ["$http", function ($http) {
	return {
		lista: function(params) {
			return $http.get("cd?page=" + params.page + "&size=" + params.size + "&nome=" + (params.nome || ""));
		},
		
		salva: function(cd) {
			return $http.post("cd", cd);
		},
		
		remove: function(id) {
			return $http.delete("cd/" + id);
		}
	};
}]);