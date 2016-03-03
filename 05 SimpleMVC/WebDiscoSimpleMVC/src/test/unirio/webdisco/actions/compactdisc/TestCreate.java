package test.unirio.webdisco.actions.compactdisc;

import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.test.TestCaseAction;
import br.unirio.webdisco.actions.ActionCompactDisc;
import br.unirio.webdisco.model.CompactDisc;

public class TestCreate extends TestCaseAction
{
	public void testSimple()
	{
		ActionCompactDisc action = new ActionCompactDisc();
		assertEquals(Action.SUCCESS, execute(action, "create"));
		
		CompactDisc cd = (CompactDisc) dataConnector.getAttribute("cd");
		assertNotNull(cd);
		assertTrue(cd.getId() <= 0);
	}
}