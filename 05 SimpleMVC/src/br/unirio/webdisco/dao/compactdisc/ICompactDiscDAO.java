package br.unirio.webdisco.dao.compactdisc;

import java.util.List;

import br.unirio.webdisco.model.CompactDisc;

/**
 * Interface para persistência de CDs
 * 
 * @author marcio.barros
 */
public interface ICompactDiscDAO
{
	/**
	 * Retorna um CD, dado seu identificador
	 */
	public CompactDisc getCompactDiscId(int id);

	/**
	 * Retorna o número de CDs registrados no sistema
	 */
	public int conta();
	
	/**
	 * Retorna a lista de CDs armazenados no sistema
	 */
	public List<CompactDisc> lista(int pagina, int tamanho);
	
	/**
	 * Insere um CD no sistema
	 */
	public boolean insere(CompactDisc cd);
	
	/**
	 * Atualiza os dados de um CD no sistema
	 */
	public boolean atualiza(CompactDisc cd);
	
	/**
	 * Remove um CD do sistema
	 */
	public boolean remove(int id);
}