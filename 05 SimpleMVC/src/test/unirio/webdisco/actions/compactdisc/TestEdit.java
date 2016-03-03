package test.unirio.webdisco.actions.compactdisc;

import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.test.TestCaseAction;
import br.unirio.webdisco.actions.ActionCompactDisc;
import br.unirio.webdisco.dao.DAOFactory;
import br.unirio.webdisco.dao.compactdisc.TestCompactDiscDAO;
import br.unirio.webdisco.model.CompactDisc;

public class TestEdit extends TestCaseAction
{
	public void testEdicaoExistente()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		dao.setup(1, new CompactDisc(1, "Meu CD Teste", 10, 20));
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("id", "1");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.SUCCESS, execute(action, "edit"));
		
		CompactDisc cd = (CompactDisc) dataConnector.getAttribute("cd");
		assertNotNull(cd);
		assertEquals(1, cd.getId());
		assertEquals("Meu CD Teste", cd.getTitle());
	}

	public void testEdicaoInexistente()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("id", "1");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.ERROR, execute(action, "edit"));
		assertEquals("O CD selecionado não está registrado no sistema", getErrorMessage(action));
		
		CompactDisc cd = (CompactDisc) dataConnector.getAttribute("cd");
		assertNull(cd);
	}
}