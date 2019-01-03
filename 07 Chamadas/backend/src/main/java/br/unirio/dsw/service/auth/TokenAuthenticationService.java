package br.unirio.dsw.service.auth;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.unirio.dsw.model.Usuario;

/**
 * Classe responsável por gerar tokens de autenticação de usuários
 * 
 * @author Marcio
 */
@Service
class TokenAuthenticationService
{
	/**
	 * Nome do header que será enviado como resposta ao login e recebido em cada ação autenticada
	 */
	private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
	
	/**
	 * Prazo de duração do token, em milisegundos (10 dias pode ser muito para algumas aplicações)
	 */
	private static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;

	/**
	 * Algoritmo utilizado na geração de tokens de autenticação
	 */
	private static final String HMAC_ALGO = "HmacSHA256";
	
	/**
	 * Separador de partes no token
	 */
	private static final String SEPARATOR = ".";

	/**
	 * Gerador de identificador único para cada token de autenticação
	 */
	private final Mac hmac;

	/**
	 * Inicializa o gerador de tokens de autenticação
	 */
	@Autowired
	TokenAuthenticationService(@Value("${auth.token.secret}") String secret)
	{
		byte[] secretKey = DatatypeConverter.parseBase64Binary(secret);
		
		try
		{
			hmac = Mac.getInstance(HMAC_ALGO);
			hmac.init(new SecretKeySpec(secretKey, HMAC_ALGO));
		}
		catch (NoSuchAlgorithmException | InvalidKeyException e)
		{
			throw new IllegalStateException("failed to initialize HMAC: " + e.getMessage(), e);
		}
	}

	/**
	 * Adiciona um token de usuário na resposta a uma requisição
	 */
	public void addAuthentication(HttpServletResponse response, UserAuthentication authentication)
	{
		final Usuario user = authentication.getDetails();
		user.setTempoExpiracaoCredenciais(System.currentTimeMillis() + TEN_DAYS);
		response.addHeader(AUTH_HEADER_NAME, createTokenForUser(user));
	}

	/**
	 * Cria um token para um usuário
	 */
	public String createTokenForUser(Usuario user)
	{
		byte[] userBytes = userToString(user);
		byte[] hash = createHmac(userBytes);
		final StringBuilder sb = new StringBuilder(170);
		sb.append(toBase64(userBytes));
		sb.append(SEPARATOR);
		sb.append(toBase64(hash));
		return sb.toString();
	}

	/**
	 * Cria o identificador único de máquina (método sincronizado)
	 */
	private synchronized byte[] createHmac(byte[] content)
	{
		return hmac.doFinal(content);
	}

	/**
	 * Recupera o usuário logado a partir de um token
	 */
	public Authentication getAuthentication(HttpServletRequest request)
	{
		final String token = request.getHeader(AUTH_HEADER_NAME);
		
		if (token != null)
		{
			final Usuario user = parseUserFromToken(token);

			if (user != null)
				return new UserAuthentication(user);
		}
		
		return null;
	}

	/**
	 * Recupera os dados de um usuário a partir de um token de autenticação
	 */
	public Usuario parseUserFromToken(String token)
	{
		final String[] parts = token.split("\\" + SEPARATOR);
		
		if (parts.length == 2 && parts[0].length() > 0 && parts[1].length() > 0)
		{
			try
			{
				final byte[] userBytes = fromBase64(parts[0]);
				final byte[] hash = fromBase64(parts[1]);

				boolean validHash = Arrays.equals(createHmac(userBytes), hash);
				
				if (validHash)
				{
					final Usuario user = stringToUser(userBytes);
					
					if (user.isCredentialsNonExpired())
						return user;
				}
			}
			catch (IllegalArgumentException e)
			{
				// log tempering attempt here
			}
		}
		
		return null;
	}

	/**
	 * Captura os dados de um usuário a partir de sua representação em string
	 */
	private Usuario stringToUser(final byte[] userBytes)
	{
		String s = new String(userBytes);
		String tokens[] = s.split("#");
		Usuario user = new Usuario();
		user.setId(Integer.parseInt(tokens[0]));
		user.setEmail(tokens[1]);
		user.setNome(tokens[2]);
		user.setTempoExpiracaoCredenciais(Long.parseLong(tokens[3]));
		return user;
	}

	/**
	 * Gera a representação em string do usuário
	 */
	private byte[] userToString(Usuario user)
	{
		// TODO somente o token precisa do tempo de validade de credenciais; o usuário não precisa

		// TODO adicionar indicação de usuário administrador e data do último login
		
		String s = user.getId() + "#" + user.getEmail() + "#" + user.getNome() + "#" + user.getTempoExpiracaoCredenciais();
		return s.getBytes();
	}

	/**
	 * Converte uma string do formato BASE-64 para o formato nativo
	 */
	private String toBase64(byte[] content)
	{
		return DatatypeConverter.printBase64Binary(content);
	}

	/**
	 * Converte uma string do formato nativo para o formato BASE-64
	 */
	private byte[] fromBase64(String content)
	{
		return DatatypeConverter.parseBase64Binary(content);
	}
}