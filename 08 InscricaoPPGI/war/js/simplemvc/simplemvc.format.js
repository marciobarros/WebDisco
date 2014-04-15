/*
 * Converte um n�mero para string, usando um determinado n�mero de casas decimais (c), um separador de decimais (d) e um separador de milhares (t) 
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
 * Converte um n�mero para string no padr�o brasileiro, usando um determinado n�mero de casas decimais (c) 
 */
Number.prototype.formatMoneyBR = function(c){
	c = isNaN(c = Math.abs(c)) ? 2 : c;
	return this.formatMoney(c, ",", ".");
};

/*
 * Converte uma string no padr�o num�rico brasileiro para n�mero
 */
String.prototype.parseMoneyBR = function() {
	return parseFloat(this.replace(".", "").replace(",", "."));
};
