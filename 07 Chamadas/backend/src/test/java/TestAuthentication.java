import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import br.unirio.dsw.MainProgram;
import br.unirio.dsw.model.Usuario;
import br.unirio.dsw.service.dao.UsuarioDAO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainProgram.class)
@AutoConfigureMockMvc
public class TestAuthentication
{
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private UsuarioDAO usuarioDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private Usuario fulano;
	
	private Usuario administrador;

	@Before
	public void setup()
	{	
		this.fulano = new Usuario();
		this.fulano.setId(1);
		this.fulano.setEmail("fulano@somewhere.com");
		this.fulano.setNome("Fulano da Slva");
		this.fulano.setSenha(passwordEncoder.encode("abc"));
		this.fulano.setContadorFalhasLogin(0);
		this.fulano.setBloqueado(false);
		this.fulano.setAdministrador(false);
		
		this.administrador = new Usuario();
		this.administrador.setId(2);
		this.administrador.setEmail("admin@somewhere.com");
		this.administrador.setNome("Administrador");
		this.administrador.setSenha(passwordEncoder.encode("root"));
		this.administrador.setContadorFalhasLogin(0);
		this.administrador.setBloqueado(false);
		this.administrador.setAdministrador(true);

		Mockito.when(usuarioDAO.carregaUsuarioEmail("fulano@somewhere.com")).thenReturn(this.fulano);
		Mockito.when(usuarioDAO.carregaUsuarioEmail("admin@somewhere.com")).thenReturn(this.administrador);
		
		Mockito.doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
            	fulano.setContadorFalhasLogin(0);
                return null;
            }
        }).when(usuarioDAO).registraLoginSucesso("fulano@somewhere.com");
		
		Mockito.doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
            	fulano.setContadorFalhasLogin(fulano.getContadorFalhasLogin() + 1);
                return null;
            }
        }).when(usuarioDAO).registraLoginFalha("fulano@somewhere.com");
	}
	
	@Test
	public void testSuccessfullAuthentication() throws Exception
	{
		MockHttpServletRequestBuilder request = post("/login")
				.contentType(APPLICATION_JSON_UTF8)
                .content("{ \"email\": \"fulano@somewhere.com\", \"senha\": \"abc\" }");
        
		this.mockMvc.perform(request)
        	.andExpect(status().isOk())
        	.andExpect(header().exists("X-AUTH-TOKEN"));
	}
	
	@Test
	public void testFailedAuthentication() throws Exception
	{
		MockHttpServletRequestBuilder request = post("/login")
				.contentType(APPLICATION_JSON_UTF8)
                .content("{ \"email\": \"fulano@somewhere.com\", \"senha\": \"xxx\" }");
        
		this.mockMvc.perform(request)
        	.andExpect(status().is(401))
        	.andExpect(header().string("X-AUTH-TOKEN", nullValue()));
	}
	
	@Test
	public void testSuccessfullAuthenticationAndProtectedPage() throws Exception
	{
		MockHttpServletRequestBuilder authRequest = post("/login")
				.contentType(APPLICATION_JSON_UTF8)
                .content("{ \"email\": \"fulano@somewhere.com\", \"senha\": \"abc\" }");
        
		MvcResult authResult = this.mockMvc.perform(authRequest)
        	.andExpect(status().isOk())
        	.andExpect(header().exists("X-AUTH-TOKEN"))
        	.andReturn();
		
	    String token = authResult.getResponse().getHeader("X-AUTH-TOKEN");
		
		MockHttpServletRequestBuilder secondRequest = get("/unidade?page=1&size=10&sigla=&nome=").
				header("X-AUTH-TOKEN", token);
        
		this.mockMvc.perform(secondRequest)
        	.andExpect(status().isOk())
        	.andExpect(content().string(containsString("\"result\":\"OK\",")));
	}
	
	@Test
	public void testFailedAuthenticationAndProtectedPage() throws Exception
	{
		MockHttpServletRequestBuilder authRequest = post("/login")
				.contentType(APPLICATION_JSON_UTF8)
                .content("{ \"email\": \"fulano@somewhere.com\", \"senha\": \"xxx\" }");
        
		this.mockMvc.perform(authRequest)
        	.andExpect(status().is(401))
        	.andExpect(header().string("X-AUTH-TOKEN", nullValue()));
		
		MockHttpServletRequestBuilder secondRequest = get("/unidade?page=1&size=10&sigla=&nome=");
        
		this.mockMvc.perform(secondRequest)
        	.andExpect(status().is(403));
	}
	
	// TODO testes com URL de administrador
	
	// Mockar o DAo de unidades para evitar os erros
}