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

function ajaxCall(url, postData, async, success, errorFnc) {
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

function capturaMunicipios(controleEstado, controleMunicipio, selecionado) 
{
	ajaxCall("/common/listaMunicipios.do", "estado=" + $("#" + controleEstado).val(), true, function(data) {
		s = "";			
		$(data).each(function() { s = s + "<option value=\"" + this + "\">" + this + "</option>"; });
		$('#' + controleMunicipio).html(s);
		$('#' + controleMunicipio).val(selecionado);
		$('#' + controleMunicipio).trigger('change');
	});
}