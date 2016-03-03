package test.unirio.webdisco.actions.compactdisc;

import java.util.List;

import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.test.TestCaseAction;
import br.unirio.webdisco.actions.ActionCompactDisc;
import br.unirio.webdisco.dao.DAOFactory;
import br.unirio.webdisco.dao.compactdisc.TestCompactDiscDAO;
import br.unirio.webdisco.model.CompactDisc;

@SuppressWarnings("unchecked")
public class TestRetrieve extends TestCaseAction
{
	private void adicionaDiscos(TestCompactDiscDAO dao, int numero)
	{
		for (int i = 1; i <= numero; i++)
			dao.setup(i, new CompactDisc(i, "CD " + i, 10, 20));
	}
	
	public void testListaPaginaVazio()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("page", "0");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.SUCCESS, execute(action, "retrieve"));
		
		List<CompactDisc> cds = (List<CompactDisc>) dataConnector.getAttribute("discs");
		assertEquals(0, cds.size());
		assertFalse((Boolean) dataConnector.getAttribute("hasNextPage"));
		assertFalse((Boolean) dataConnector.getAttribute("hasPriorPage"));
	}

	public void testListaPaginaIncompleta()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		adicionaDiscos(dao, 5);
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("page", "0");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.SUCCESS, execute(action, "retrieve"));
		
		List<CompactDisc> cds = (List<CompactDisc>) dataConnector.getAttribute("discs");
		assertEquals(5, cds.size());
		assertFalse((Boolean) dataConnector.getAttribute("hasNextPage"));
		assertFalse((Boolean) dataConnector.getAttribute("hasPriorPage"));
	}

	public void testListaPaginaExata()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		adicionaDiscos(dao, 10);
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("page", "0");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.SUCCESS, execute(action, "retrieve"));
		
		List<CompactDisc> cds = (List<CompactDisc>) dataConnector.getAttribute("discs");
		assertEquals(10, cds.size());
		assertFalse((Boolean) dataConnector.getAttribute("hasNextPage"));
		assertFalse((Boolean) dataConnector.getAttribute("hasPriorPage"));
	}

	public void testListaPaginaInexistente()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		adicionaDiscos(dao, 10);
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("page", "200");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.SUCCESS, execute(action, "retrieve"));
		
		List<CompactDisc> cds = (List<CompactDisc>) dataConnector.getAttribute("discs");
		assertEquals(0, cds.size());
		assertFalse((Boolean) dataConnector.getAttribute("hasNextPage"));
		assertTrue((Boolean) dataConnector.getAttribute("hasPriorPage"));
	}

	public void testListaPrimeiraPaginaExemploGrande()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		adicionaDiscos(dao, 100);
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("page", "0");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.SUCCESS, execute(action, "retrieve"));
		
		List<CompactDisc> cds = (List<CompactDisc>) dataConnector.getAttribute("discs");
		assertEquals(10, cds.size());
		assertTrue((Boolean) dataConnector.getAttribute("hasNextPage"));
		assertFalse((Boolean) dataConnector.getAttribute("hasPriorPage"));
	}

	public void testListaSegundaPaginaExemploGrande()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		adicionaDiscos(dao, 100);
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("page", "1");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.SUCCESS, execute(action, "retrieve"));
		
		List<CompactDisc> cds = (List<CompactDisc>) dataConnector.getAttribute("discs");
		assertEquals(10, cds.size());
		assertTrue((Boolean) dataConnector.getAttribute("hasNextPage"));
		assertTrue((Boolean) dataConnector.getAttribute("hasPriorPage"));
	}

	public void testListaUltimaPaginaExemploGrande()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		adicionaDiscos(dao, 100);
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("page", "9");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.SUCCESS, execute(action, "retrieve"));
		
		List<CompactDisc> cds = (List<CompactDisc>) dataConnector.getAttribute("discs");
		assertEquals(10, cds.size());
		assertFalse((Boolean) dataConnector.getAttribute("hasNextPage"));
		assertTrue((Boolean) dataConnector.getAttribute("hasPriorPage"));
	}
}