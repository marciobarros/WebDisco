function ajaxCall(url, postData, async, success) {
	$.ajax({
		url: url,
		type: 'POST',
		dataType: 'json',
		data: postData,
		async: async,
		success: function (data) {
			if (data.Result == "OK")
				success(data.data);
			else
				alert(data.message);
		},
		error: function () {
			alert("Erro ao conectar com o servidor");
		}
	});
}

function ajaxCallError(url, postData, async, success, errorFnc) {
	$.ajax({
		url: url,
		type: 'POST',
		dataType: 'json',
		data: postData,
		async: async,
		success: function (data) {
			if (data.Result == "OK")
				success(data.data);
			else
				errorFnc(data.message);
		},
		error: function () {
			errorFnc("Erro ao conectar com o servidor");
		}
	});
}

function ajaxCallNoError (url, postData, async, success) {
	$.ajax({
		url: url,
		type: 'POST',
		dataType: 'json',
		data: postData,
		async: async,
		success: function (data) {
			if (data.Result == "OK")
				success(data.data);
		},
		error: function () {
		}
	});
}

function capturaMunicipios(controleEstado, controleMunicipio, selecionado) {
	var cbEstado = $("#" + controleEstado);
	var cbMunicipio = $("#" + controleMunicipio);
	var estado = cbEstado.val();
	
	if (estado.length == 0) {
		var s = "<option value=''>Selecione um munic\u00edpio ...</option>";			
		cbMunicipio.html(s).val("").trigger('change');
		return;
	}
	
	ajaxCall("/common/listaMunicipios.do", "estado=" + estado, true, function(data) {
		var s = "<option value=''>Selecione um munic\u00edpio ...</option>";			
		$(data).each(function() { s = s + "<option value=\"" + this.nome + "\">" + this.nome + "</option>"; });
		cbMunicipio.html(s);
		cbMunicipio.val(selecionado);
		cbMunicipio.trigger('change');
	});
}