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
 * A��es gerais da aplica��o
 * 
 * @author Marcio
 */
public class ActionCommon extends Action
{
	/**
	 * A��o que lista os munic�pios de um estado
	 */
	@DisableUserVerification
	@Ajax
	public String listaMunicipios() throws ActionException
	{
		Estado estado = Estado.get(getParameter("estado", ""));
		check(estado != null, "Selecione o estado para carregar seus munic�pios.");
		
		Iterable<Municipio> municipios = DAOFactory.getMunicipioDAO().getMunicipiosEstado(estado);
		check(municipios != null, "N�o foi poss�vel recuperar os munic�pios do estado desejado.");
		
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