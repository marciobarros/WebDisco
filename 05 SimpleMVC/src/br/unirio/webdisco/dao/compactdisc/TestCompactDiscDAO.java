package br.unirio.webdisco.dao.compactdisc;

import java.util.List;

import br.unirio.simplemvc.dao.ListMockDAO;
import br.unirio.webdisco.model.CompactDisc;

public class TestCompactDiscDAO extends ListMockDAO<CompactDisc> implements ICompactDiscDAO
{
	@Override
	public CompactDisc getCompactDiscId(int id) 
	{
		return find(id);
	}

	@Override
	public int conta() 
	{
		return count();
	}

	@Override
	public List<CompactDisc> lista(int pagina, int tamanho) 
	{
		return null; // page(pagina, tamanho);
	}

	@Override
	public boolean insere(CompactDisc cd) 
	{
		return super.add(cd.getId(), cd);
	}

	@Override
	public boolean atualiza(CompactDisc cd) 
	{
		return super.update(cd.getId(), cd);
	}

	@Override
	public boolean remove(int id) 
	{
		return super.remove(id);
	}
}