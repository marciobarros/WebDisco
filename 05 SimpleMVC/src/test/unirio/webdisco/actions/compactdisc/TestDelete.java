package test.unirio.webdisco.actions.compactdisc;

import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.test.TestCaseAction;
import br.unirio.webdisco.actions.ActionCompactDisc;
import br.unirio.webdisco.dao.DAOFactory;
import br.unirio.webdisco.dao.compactdisc.TestCompactDiscDAO;
import br.unirio.webdisco.model.CompactDisc;

public class TestDelete extends TestCaseAction
{
	public void testRemocaoExistente()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		dao.setup(1, new CompactDisc(1, "Meu CD Teste", 10, 20));
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("id", "1");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.SUCCESS, execute(action, "delete"));
		
		assertEquals(0, dao.conta());
	}

	public void testRemocaoInexistente()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		dao.setup(2, new CompactDisc(2, "Meu CD Teste", 10, 20));
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("id", "1");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.ERROR, execute(action, "delete"));
		assertEquals("O CD selecionado não está registrado no sistema", getErrorMessage(action));
		
		assertEquals(1, dao.conta());
	}

	public void testRemocaoPreservacaoOutros()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		dao.setup(1, new CompactDisc(1, "Meu CD Teste 1", 10, 20));
		dao.setup(2, new CompactDisc(2, "Meu CD Teste 2", 15, 25));
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("id", "1");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.SUCCESS, execute(action, "delete"));
		
		assertEquals(1, dao.conta());
		assertEquals("Meu CD Teste 2", dao.getCompactDiscId(2).getTitle());
	}
}