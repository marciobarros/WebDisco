package br.unirio.inscricaoppgi.actions;

import br.unirio.inscricaoppgi.dao.DAOFactory;
import br.unirio.inscricaoppgi.model.Municipio;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.authentication.DisableUserVerification;
import br.unirio.simplemvc.actions.qualifiers.Ajax;
import br.unirio.simplemvc.json.JSONArray;

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
		String estado = getParameter("estado");
		Iterable<Municipio> municipios = DAOFactory.getMunicipioDAO().getMunicipiosEstado(estado);
		JSONArray result = new JSONArray();

		if (municipios != null)
		{
			for (Municipio municipio : municipios)
				result.add(municipio.getNome());
		}
		else
		{
			result.add(estado);
		}

		return ajaxSuccess(result);
	}
}