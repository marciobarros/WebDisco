package br.unirio.dsw.service.auth;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * Classe responsável por gerar tokens de autenticação de usuários
 * 
 * @author Marcio
 */
class TokenAuthenticationService
{
	/**
	 * Prazo de duração do token, em milisegundos (10 dias pode ser muito para algumas aplicações)
	 */
	public static final long TOKEN_DURATION = 1000 * 60 * 60 * 24 * 10;
	
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
	TokenAuthenticationService(String secret)
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
	 * Cria um token de autenticação para um usuário, dado seu e-mail
	 */
	public String createAuthenticationToken(String email)
	{
		long expiracao = System.currentTimeMillis() + TOKEN_DURATION;
		String userToken = email + "#" + expiracao;
		byte[] userBytes = userToken.getBytes();

		byte[] hash = createHmac(userBytes);

		final StringBuilder sb = new StringBuilder(170);
		sb.append(DatatypeConverter.printBase64Binary(userBytes));
		sb.append(SEPARATOR);
		sb.append(DatatypeConverter.printBase64Binary(hash));
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
	 * Recupera o em-mail de um usuário a partir de um token de autenticação
	 */
	public String parseUserToken(String token)
	{
		final String[] parts = token.split("\\" + SEPARATOR);
		
		if (parts.length == 2 && parts[0].length() > 0 && parts[1].length() > 0)
		{
			try
			{
				byte[] userBytes = DatatypeConverter.parseBase64Binary(parts[0]);
				byte[] hash = DatatypeConverter.parseBase64Binary(parts[1]);

				boolean validHash = Arrays.equals(createHmac(userBytes), hash);
				
				if (validHash)
				{
					String tokens[] = new String(userBytes).split("#");

					if (tokens.length == 2 && tokens[0].length() > 0 && tokens[1].length() > 0)
					{
						String email = tokens[0];
						long expiracao = Long.parseLong(tokens[1]);

						if (System.currentTimeMillis() < expiracao)
							return email;
					}
				}
			}
			catch (IllegalArgumentException e)
			{
				// log tempering attempt here
			}
		}
		
		return null;
	}
}