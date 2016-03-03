package br.unirio.crud.dao.usuario;

import br.unirio.simplemvc.dao.OrderingType;

/**
 * Enumeração dos tipos de ordenação de usuários
 * 
 * @author Marcio Barros
 */
public enum OrdenacaoUsuario
{
	Nenhuma(""),
	Identificador("id"),
	Nome("nome"),
	Email("email");
	
	private String campo;
	
	private OrdenacaoUsuario(String campo)
	{
		this.campo = campo;
	}
	
	public String geraClausula(OrderingType tipoOrdenacao)
	{
		if (campo.length() == 0)
			return "";
		
		return "ORDER BY " + campo + " " + tipoOrdenacao.getClause() + " ";
	}
}