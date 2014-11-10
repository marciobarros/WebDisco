package br.unirio.inscricaoppgi.dao;

import java.util.Date;
import java.util.List;

import lombok.Data;
import br.unirio.inscricaoppgi.gae.datastore.AbstractDAO;
import br.unirio.inscricaoppgi.gae.datastore.DataObject;
import br.unirio.simplemvc.utils.DateUtils;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

/**
 * Classe que trata da persistência de tokens de recuperação de senha
 * 
 * @author Marcio
 */
public class UsuarioTokenSenhaDAO extends AbstractDAO<TokenSenhaUsuario>
{
	/**
	 * Inicializa a classe
	 */
	protected UsuarioTokenSenhaDAO()
	{
		super("UsuarioTokenSenha");
	}

	/**
	 * Carrega um token de recuperação de senha
	 */
	@Override
	protected TokenSenhaUsuario load(Entity e)
	{
		TokenSenhaUsuario token = new TokenSenhaUsuario();
		token.setId((int)e.getKey().getId());
		token.setIdUsuario(getIntProperty(e, "idUsuario", -1));
		token.setToken(getStringProperty(e, "token", ""));
		token.setTimestamp(getDateProperty(e, "timestamp"));
		return token;
	}

	/**
	 * Salva um token de recuperação de senha
	 */
	@Override
	protected void save(TokenSenhaUsuario token, Entity e)
	{
		e.setProperty("idUsuario", token.getIdUsuario());
		e.setProperty("token", token.getToken());
		e.setProperty("timestamp", token.getTimestamp());
	}

	/**
	 * Armazena um token de recuperação de senha
	 */
	public void armazenaTokenTrocaSenha(long idUsuario, String token)
	{
		TokenSenhaUsuario tokenSenha = new TokenSenhaUsuario();
		tokenSenha.setIdUsuario(idUsuario);
		tokenSenha.setToken(token);
		this.put(tokenSenha);
	}

	/**
	 * Verifica se um token de recuperação de senha é válido
	 */
	public boolean verificaTokenTrocaSenha(long idUsuario, String token, int numeroHoras)
	{
		Filter filter1 = exactFilter("idUsuario", FilterOperator.EQUAL, idUsuario);
		Filter filter2 = exactFilter("token", FilterOperator.EQUAL, token);
		List<TokenSenhaUsuario> tokensSenha = this.list(and(filter1, filter2));
		
		if (tokensSenha.size() > 0)
		{
			TokenSenhaUsuario tokenSenha = tokensSenha.get(0);
			long seconds = (DateUtils.now().getTime() - tokenSenha.getTimestamp().getTime()) / 1000;
			double hours = seconds / 3600.0;
			return hours <= numeroHoras;
		}
		
		return false;
	}
}

/**
 * Classe que representa um token de recuperação de senha
 */
@Data class TokenSenhaUsuario implements DataObject
{
	private long id;
	private long idUsuario;
	private String token;
	private Date timestamp;
	
	public TokenSenhaUsuario()
	{
		this.id = -1;
		this.idUsuario = -1;
		this.token = "";
		this.timestamp = new Date();
	}
}