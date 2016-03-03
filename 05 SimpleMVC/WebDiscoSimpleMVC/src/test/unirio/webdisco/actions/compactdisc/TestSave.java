package test.unirio.webdisco.actions.compactdisc;

import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.test.TestCaseAction;
import br.unirio.webdisco.actions.ActionCompactDisc;
import br.unirio.webdisco.dao.DAOFactory;
import br.unirio.webdisco.dao.compactdisc.TestCompactDiscDAO;
import br.unirio.webdisco.model.CompactDisc;

public class TestSave extends TestCaseAction
{
	public void testSalvaNovoSucesso()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("id", "-1");
		dataConnector.setupParameter("title", "Novo CD");
		dataConnector.setupParameter("price", "10");
		dataConnector.setupParameter("stock", "20");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.SUCCESS, execute(action, "save"));
		
		assertEquals(1, dao.conta());
		CompactDisc cd = dao.lista(0, 1).get(0);
		assertEquals("Novo CD", cd.getTitle());
		assertEquals(10.0, cd.getPrice());
		assertEquals(20.0, cd.getStock());
	}

	public void testSalvaNovoSemTitulo()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("id", "-1");
		dataConnector.setupParameter("title", "");
		dataConnector.setupParameter("price", "10");
		dataConnector.setupParameter("stock", "20");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.ERROR, execute(action, "save"));
		assertEquals("O título do CD não pode ser vazio", getErrorMessage(action));
		
		assertEquals(0, dao.conta());
	}

	public void testSalvaNovoPrecoNegativo()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("id", "-1");
		dataConnector.setupParameter("title", "Novo CD");
		dataConnector.setupParameter("price", "-10");
		dataConnector.setupParameter("stock", "20");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.ERROR, execute(action, "save"));
		assertEquals("O preço do CD deve ser maior do que zero", getErrorMessage(action));
		
		assertEquals(0, dao.conta());
	}

	public void testSalvaNovoPrecoZero()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("id", "-1");
		dataConnector.setupParameter("title", "Novo CD");
		dataConnector.setupParameter("price", "0");
		dataConnector.setupParameter("stock", "20");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.ERROR, execute(action, "save"));
		assertEquals("O preço do CD deve ser maior do que zero", getErrorMessage(action));
		
		assertEquals(0, dao.conta());
	}

	public void testSalvaNovoEstoqueNegativo()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("id", "-1");
		dataConnector.setupParameter("title", "Novo CD");
		dataConnector.setupParameter("price", "10");
		dataConnector.setupParameter("stock", "-20");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.ERROR, execute(action, "save"));
		assertEquals("A quantidade em estoque do CD deve ser maior ou igual a zero", getErrorMessage(action));
		
		assertEquals(0, dao.conta());
	}

	public void testSalvaNovoEstoqueZero()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("id", "-1");
		dataConnector.setupParameter("title", "Novo CD");
		dataConnector.setupParameter("price", "10");
		dataConnector.setupParameter("stock", "0");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.SUCCESS, execute(action, "save"));
	}

	public void testSalvaEditadoSucesso()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		dao.setup(100, new CompactDisc(100, "CD Teste", 10, 20));
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("id", "100");
		dataConnector.setupParameter("title", "CD Editado");
		dataConnector.setupParameter("price", "11");
		dataConnector.setupParameter("stock", "21");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.SUCCESS, execute(action, "save"));
		
		assertEquals(1, dao.conta());
		CompactDisc cd = dao.getCompactDiscId(100);
		assertEquals("CD Editado", cd.getTitle());
		assertEquals(11.0, cd.getPrice());
		assertEquals(21.0, cd.getStock());
	}

	public void testSalvaEditadoSemTitulo()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		dao.setup(100, new CompactDisc(100, "CD Teste", 10, 20));
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("id", "100");
		dataConnector.setupParameter("title", "");
		dataConnector.setupParameter("price", "11");
		dataConnector.setupParameter("stock", "21");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.ERROR, execute(action, "save"));
		assertEquals("O título do CD não pode ser vazio", getErrorMessage(action));
		
		assertEquals(1, dao.conta());
	}

	public void testSalvaEditadoPrecoNegativo()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		dao.setup(100, new CompactDisc(100, "CD Teste", 10, 20));
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("id", "100");
		dataConnector.setupParameter("title", "CD Editado");
		dataConnector.setupParameter("price", "-11");
		dataConnector.setupParameter("stock", "21");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.ERROR, execute(action, "save"));
		assertEquals("O preço do CD deve ser maior do que zero", getErrorMessage(action));
		
		assertEquals(1, dao.conta());
	}

	public void testSalvaEditadoPrecoZero()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		dao.setup(100, new CompactDisc(100, "CD Teste", 10, 20));
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("id", "100");
		dataConnector.setupParameter("title", "CD Editado");
		dataConnector.setupParameter("price", "0");
		dataConnector.setupParameter("stock", "21");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.ERROR, execute(action, "save"));
		assertEquals("O preço do CD deve ser maior do que zero", getErrorMessage(action));
		
		assertEquals(1, dao.conta());
	}

	public void testSalvaEditadoEstoqueNegativo()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		dao.setup(100, new CompactDisc(100, "CD Teste", 10, 20));
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("id", "100");
		dataConnector.setupParameter("title", "CD Editado");
		dataConnector.setupParameter("price", "11");
		dataConnector.setupParameter("stock", "-21");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.ERROR, execute(action, "save"));
		assertEquals("A quantidade em estoque do CD deve ser maior ou igual a zero", getErrorMessage(action));
		
		assertEquals(1, dao.conta());
	}

	public void testSalvaEditadoInexistente()
	{
		TestCompactDiscDAO dao = new TestCompactDiscDAO();
		dao.setup(100, new CompactDisc(100, "CD Teste", 10, 20));
		DAOFactory.setCompactDiscDAO(dao);
		
		dataConnector.setupParameter("id", "1001");
		dataConnector.setupParameter("title", "CD Editado");
		dataConnector.setupParameter("price", "11");
		dataConnector.setupParameter("stock", "-21");
		
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.ERROR, execute(action, "save"));
		assertEquals("O CD selecionado não está registrado no sistema", getErrorMessage(action));
		
		assertEquals(1, dao.conta());
	}
}