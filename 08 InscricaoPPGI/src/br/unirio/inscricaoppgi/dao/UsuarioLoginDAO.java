package br.unirio.inscricaoppgi.dao;

import java.util.Date;
import java.util.List;

import lombok.Data;
import br.unirio.inscricaoppgi.model.Usuario;
import br.unirio.simplemvc.gae.datastore.AbstractDAO;
import br.unirio.simplemvc.gae.datastore.DataObject;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

/**
 * Classe que trata da persistência de registros de login de usuários
 * 
 * @author Marcio
 */
public class UsuarioLoginDAO extends AbstractDAO<LoginUsuario>
{
	/**
	 * Inicializa a classe
	 */
	protected UsuarioLoginDAO()
	{
		super("UsuarioLogin");
	}

	/**
	 * Carrega os dados de um registro de login
	 */
	@Override
	protected LoginUsuario load(Entity e)
	{
		LoginUsuario login = new LoginUsuario();
		login.setId((int)e.getKey().getId());
		login.setIdUsuario(getIntProperty(e, "idUsuario", -1));
		login.setSucesso(getBooleanProperty(e, "sucesso", false));
		login.setTimestamp(getDateProperty(e, "timestamp"));
		return login;
	}

	/**
	 * Salva os dados de um registro de login
	 */
	@Override
	protected void save(LoginUsuario login, Entity e)
	{
		e.setProperty("idUsuario", login.getIdUsuario());
		e.setProperty("sucesso", login.isSucesso());
		e.setProperty("timestamp", login.getTimestamp());
	}

	/**
	 * Registra uma falha de login de um usuário
	 */
	public void registraLoginFalha(int idUsuario)
	{
		this.put(new LoginUsuario(idUsuario, false));

		List<LoginUsuario> logins = list(0, 3, exactFilter("idUsuario", FilterOperator.EQUAL, idUsuario), "timestamp", SortDirection.DESCENDING);
		
		for (LoginUsuario login : logins)
			if (login.isSucesso())
				return;

		if (logins.size() == 3)
		{
			Usuario usuario = DAOFactory.getUsuarioDAO().get(idUsuario);
			usuario.setForcaResetSenha(true);
			DAOFactory.getUsuarioDAO().put(usuario);
		}
	}

	/**
	 * Registra um login de sucesso de um usuário
	 */
	public void registraLoginSucesso(int idUsuario)
	{
		this.put(new LoginUsuario(idUsuario, true));

		Usuario usuario = DAOFactory.getUsuarioDAO().get(idUsuario);
		usuario.setForcaResetSenha(false);
		DAOFactory.getUsuarioDAO().put(usuario);
	}

	/**
	 * Pega a data de último login de um usuário
	 */
	public Date pegaDataUltimoLogin(int idUsuario)
	{
		List<LoginUsuario> logins = list(0, 1, exactFilter("idUsuario", FilterOperator.EQUAL, idUsuario), "timestamp", SortDirection.DESCENDING);
		return (logins.size() > 0) ? logins.get(0).getTimestamp() : null;
	}
}

/**
 * Classe que representa um registro de login
 */
@Data class LoginUsuario implements DataObject
{
	private int id;
	private int idUsuario;
	private boolean sucesso;
	private Date timestamp;
	
	public LoginUsuario(int idUsuario, boolean sucesso)
	{
		this.id = -1;
		this.idUsuario = idUsuario;
		this.sucesso = sucesso;
		this.timestamp = new Date();
	}

	public LoginUsuario()
	{
		this(-1, false);
	}
}