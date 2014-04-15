/*
 * Converte um número para string, usando um determinado número de casas decimais (c), um separador de decimais (d) e um separador de milhares (t) 
 */
Number.prototype.formatMoney = function(c, d, t) {
	var n = this;
	c = isNaN(c = Math.abs(c)) ? 2 : c;
	d = d == undefined ? "," : d;
	t = t == undefined ? "." : t;
	s = n < 0 ? "-" : "";
	i = parseInt(n = Math.abs(+n || 0).toFixed(c)) + "";
	j = (j = i.length) > 3 ? j % 3 : 0;
	return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
};
	 
/*
 * Converte um número para string no padrão brasileiro, usando um determinado número de casas decimais (c) 
 */
Number.prototype.formatMoneyBR = function(c){
	c = isNaN(c = Math.abs(c)) ? 2 : c;
	return this.formatMoney(c, ",", ".");
};

/*
 * Converte uma string no padrão numérico brasileiro para número
 */
String.prototype.parseMoneyBR = function() {
	return parseFloat(this.replace(".", "").replace(",", "."));
};
