/**
 * Traduz uma mensagem
 */
function translate($rootScope, item) {
	if ($rootScope.i18n)
		return $rootScope.i18n[item];

	return item;
}

/**
 * Cria um filtro de traducao
 */
App.filter('translate', doTranslation);

function doTranslation($rootScope) {
	filter.$stateful = true;
	return filter;
  
	function filter(item) {
		return translate($rootScope, item);
	};
}

doTranslation.$inject = ['$rootScope'];

/**
 * Carrega um conjunto de strings de traducao, dado um namespace
 */
function loadTranslations(namespace, $rootScope, $http) {
	$http.get(contextPath + "/translation?namespace=" + namespace).then(function(data) {
		if ($rootScope.i18n)
			$rootScope.i18n = Object.assign($rootScope.i18n, data.data.data);
		else
			$rootScope.i18n = data.data.data; 
	});
}
