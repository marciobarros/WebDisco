package br.unirio.crud.actions;

import br.unirio.crud.dao.DAOFactory;
import br.unirio.crud.model.Estado;
import br.unirio.crud.model.Municipio;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.authentication.DisableUserVerification;
import br.unirio.simplemvc.actions.qualifiers.Ajax;
import br.unirio.simplemvc.json.JSONArray;
import br.unirio.simplemvc.json.JSONObject;

/**
 * Ações gerais da aplicação
 * 
 * @author Marcio
 */
public class ActionCommon extends Action
{
	/**
	 * Ação que lista os municípios de um estado
	 */
	@DisableUserVerification
	@Ajax
	public String listaMunicipios() throws ActionException
	{
		Estado estado = Estado.get(getParameter("estado", ""));
		check(estado != null, "Selecione o estado para carregar seus municípios.");
		
		Iterable<Municipio> municipios = DAOFactory.getMunicipioDAO().getMunicipiosEstado(estado);
		check(municipios != null, "Não foi possível recuperar os municípios do estado desejado.");
		
		JSONArray jsonMunicipios = new JSONArray();

		for (Municipio municipio : municipios)
		{
			jsonMunicipios.add(new JSONObject()
				.add("nome", municipio.getNome())
				.add("lat", municipio.getLatitude())
				.add("lng", municipio.getLongitude()));
		}

		return ajaxSuccess(jsonMunicipios);
	}
}