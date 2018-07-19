app.service("unidadeService", function () {
	var self = this;

	self.unidade = null;

    return {
        getUnidade: function () {
            return self.unidade;
        },
        setUnidade: function (unidade) {
            self.unidade = unidade;
        }
    };
});