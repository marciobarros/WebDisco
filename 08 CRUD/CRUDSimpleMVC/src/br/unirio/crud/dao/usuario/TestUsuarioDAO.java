package br.unirio.crud.dao.usuario;

import java.util.List;

import org.joda.time.DateTime;

import br.unirio.crud.model.Usuario;
import br.unirio.simplemvc.dao.ListMockDAO;
import br.unirio.simplemvc.dao.OrderingType;

/**
 * Mock do gerenciador de banco de dados dos usuários
 * 
 * @author marcio.barros
 */
public class TestUsuarioDAO extends ListMockDAO<Usuario> implements IUsuarioDAO
{
	/**
	 * Lista dos tokens de login dos usuários
	 */
//	private List<UsuarioToken> tokens;
	
	/**
	 * Inicializa o mock
	 */
	public TestUsuarioDAO()
	{
//		this.tokens = new ArrayList<UsuarioToken>();
	}
	
	/**
	 * Carrega um usuário, dada seu identificador
	 */
	@Override
	public Usuario getUsuarioId(int id)
	{
		return find(id);
	}

	/**
	 * Retorna um usuário, dado seu nome
	 */
	@Override
	public Usuario getUsuarioNome(String nome)
	{
		if (this.instances() == null)
			return null;
		
		for (Usuario p : this.instances())
			if (p.getNome().compareToIgnoreCase(nome) == 0)
				return p;
				
		return null;
	}

	/**
	 * Retorna um usuário, dado seu e-mail
	 */
	@Override
	public Usuario getUsuarioEmail(String email)
	{
		if (this.instances() == null)
			return null;
		
		for (Usuario p : this.instances())
			if (p.getEmail().compareToIgnoreCase(email) == 0)
				return p;
				
		return null;
	}

	/**
	 * Conta o número de usuários que atendem a um filtro
	 */
	@Override
	public int conta(String filtroNome, String filtroEmail, String filtroEstado)
	{
		return 0;
	}

	/**
	 * Retorna a lista de usuários que atendem a um filtro
	 */
	@Override
	public List<Usuario> lista(int pagina, int tamanho, OrdenacaoUsuario campoOrdenacao, OrderingType tipoOrdenacao, String filtroNome, String filtroEmail, String filtroEstado)
	{
		return null;
	}

	/**
	 * Adiciona um usuário
	 */
	@Override
	public boolean adiciona(Usuario usuario)
	{
		return this.add(usuario.getId(), usuario);
	}

	/**
	 * Atualiza os dados de um usuário
	 */
	@Override
	public boolean atualiza(Usuario usuario)
	{
		return update(usuario.getId(), usuario);
	}

	/**
	 * Remove um usuário do sistema
	 */
	@Override
	public boolean remove(int id)
	{
		return remove(id);
	}

	/**
	 * Atualiza a senha de um usuário
	 */
	@Override
	public boolean atualizaSenha(int idUsuario, String senhaCodificada)
	{
		return false;
	}

	/**
	 * Registra o login de um usuário com sucesso
	 */
	@Override
	public boolean registraLoginSucesso(int idUsuario)
	{
		return false;
	}

	/**
	 * Registra o login de um usuário com falha
	 */
	@Override
	public boolean registraLoginFalha(int idUsuario)
	{
		return false;
	}

	/**
	 * Pega a data/hora do último login de um usuário
	 */
	@Override
	public DateTime pegaDataUltimoLogin(int idUsuario)
	{
		return null;
	}

	/**
	 * Pega o número de tentativas de login com falha de um usuário
	 */
	@Override
	public int pegaNumeroTentativasFalha(int idUsuario)
	{
		return 0;
	}
	
	/**
	 * Armazena um token para troca de senha
	 */
	@Override
	public boolean armazenaTokenTrocaSenha(int idUsuario, String token)
	{
//		tokens.add(new UsuarioToken(idUsuario, token));
		return true;
	}
	
	/**
	 * Determina se um token para troca de senha é válido para um usuário
	 */
	@Override
	public boolean verificaTokenTrocaSenha(int idUsuario, String token, int maxHoras)
	{
//		for (UsuarioToken pToken : tokens)
//			if (pToken.getIdUsuario() == idUsuario && pToken.getToken().compareTo(token) == 0)
//				return true;
//		
		return false;
	}
}