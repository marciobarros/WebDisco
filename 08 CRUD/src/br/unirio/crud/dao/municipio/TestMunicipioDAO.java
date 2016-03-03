package br.unirio.crud.dao.municipio;

import java.util.List;

import br.unirio.crud.model.Estado;
import br.unirio.crud.model.Municipio;
import br.unirio.simplemvc.dao.ListMockDAO;

/**
 * Classe responsável pela persistência de municípios nos casos de teste
 * 
 * @author Marcio Barros
 */
public class TestMunicipioDAO extends ListMockDAO<Municipio> implements IMunicipioDAO
{
	/**
	 * Carrega os nomes dos municípios de um determinado estado
	 */
	@Override
	public List<Municipio> getMunicipiosEstado(Estado estado)
	{
		return null;
	}

	/**
	 * Retorna um municipio, dado seu nome
	 */
	@Override
	public Municipio pegaMunicipioNome(Estado estado, String nomeMunicipio)
	{
		return null;
	}
}