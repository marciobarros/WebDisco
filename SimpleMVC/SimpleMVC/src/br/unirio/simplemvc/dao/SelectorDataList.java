package br.unirio.simplemvc.dao;

import java.util.ArrayList;

/**
 * Classe que representa uma lista de seletores para consultas rápidas ao banco de dados
 * 
 * @author marcio.barros
 */
public class SelectorDataList extends ArrayList<SelectorData>
{
	private static final long serialVersionUID = -1416743202391397351L;
	
	/**
	 * Retorna o nome de um seletor, dado seu ID
	 */
	public String getNameById(int id)
	{
		for (SelectorData selector : this)
			if (selector.getId() == id)
				return selector.getName();
		
		return null;
	}
	
	/**
	 * Retorna o ID de um seletor, dado seu nome
	 */
	public int getIdByName(String name)
	{
		for (SelectorData selector : this)
			if (selector.getName().compareToIgnoreCase(name) == 0)
				return selector.getId();
		
		return -1;
	}
}