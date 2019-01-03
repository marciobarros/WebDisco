import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;

import br.unirio.dsw.MainProgram;

public class TestAuthentication
{
	// NOTE precisa de um usuário com email "fulano@somewhere.com" e senha "abc" no banco de dados antes de executar
	
	@BeforeClass
	public static void once()
	{
		SpringApplication.run(MainProgram.class, new String[] {});
	}
	
	@Test
	public void testSuccessfullAuthentication() throws Exception
	{
		WebResponse response = HTTP.post("http://localhost:8080/login", "{ \"email\": \"fulano@somewhere.com\", \"senha\": \"abc\" }");
		assertEquals(200, response.getCode());
		assertNotNull(response.get("X-AUTH-TOKEN"));
	}
	
	@Test
	public void testFailedAuthentication() throws Exception
	{
		WebResponse response = HTTP.post("http://localhost:8080/login", "{ \"email\": \"fulano2@somewhere.com\", \"senha\": \"xxx\" }");
		assertEquals(403, response.getCode());
		assertNull(response.get("X-AUTH-TOKEN"));
	}
	
	@Test
	public void testSuccessfullAuthenticationAndProtectedPage() throws Exception
	{
		WebResponse response = HTTP.post("http://localhost:8080/login", "{ \"email\": \"fulano@somewhere.com\", \"senha\": \"abc\" }");
		assertEquals(200, response.getCode());
		
		String auth = response.get("X-AUTH-TOKEN");
		assertNotNull(auth);
		
		WebResponse response2 = HTTP.get("http://localhost:8080/unidade?page=1&size=10&sigla=&nome=", auth);
		assertEquals(200, response2.getCode());
		assertTrue(response2.getBody().contains("\"result\":\"OK\","));
	}
	
	@Test
	public void testFailedAuthenticationAndProtectedPage() throws Exception
	{
		WebResponse response = HTTP.post("http://localhost:8080/login", "{ \"email\": \"fulano2@somewhere.com\", \"senha\": \"xxx\" }");
		assertEquals(403, response.getCode());
		
		WebResponse response2 = HTTP.get("http://localhost:8080/unidade?page=1&size=10&sigla=&nome=");
		assertEquals(403, response2.getCode());
	}
	
	// TODO testes com URL de administrador
	
	// TODO mock do banco de dados
}