package br.unirio.crud.dao.municipio;

import java.util.List;

import br.unirio.crud.model.Estado;
import br.unirio.crud.model.Municipio;

/**
 * Interface para os DAO de municipios
 * 
 * @author marcio.barros
 */
public interface IMunicipioDAO
{
	/**
	 * Carrega os nomes dos municipios de um determinado estado
	 */
	List<Municipio> getMunicipiosEstado(Estado estado);

	/**
	 * Retorna um municipio, dado seu nome
	 */
	Municipio pegaMunicipioNome(Estado estado, String nomeMunicipio);
}