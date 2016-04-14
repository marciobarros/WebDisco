package br.unirio.crud.dao.usuario;

import java.util.List;

import org.joda.time.DateTime;

import br.unirio.crud.model.Usuario;
import br.unirio.simplemvc.dao.OrderingType;

/**
 * Interface para os DAO de usuario
 * 
 * @author marcio.barros
 */
public interface IUsuarioDAO
{
	/**
	 * Carrega um usuario, dado seu identificador
	 */
	Usuario getUsuarioId(int id);

	/**
	 * Retorna um usuario, dado seu nome
	 */
	Usuario getUsuarioNome(String nome);

	/**
	 * Retorna um usuario, dado seu e-mail
	 */
	Usuario getUsuarioEmail(String email);

	/**
	 * Conta o numero de usuarios que atendem a um filtro
	 */
	int conta(String filtroNome, String filtroEmail, String filtroEstado);

	/**
	 * Retorna a lista de usuarios que atendem a um filtro
	 */
	List<Usuario> lista(int pagina, int tamanho, OrdenacaoUsuario campoOrdenacao, OrderingType tipoOrdenacao, String filtroNome, String filtroEmail, String filtroEstado);

	/**
	 * Adiciona um usuario no sistema
	 */
	boolean adiciona(Usuario usuario);

	/**
	 * Atualiza os dados de um usuario
	 */
	boolean atualiza(Usuario usuario);

	/**
	 * Remove um usuario do sistema
	 */
	boolean remove(int id);

	/**
	 * Atualiza a senha de um usuario
	 */
	boolean atualizaSenha(int idUsuario, String senhaCodificada);

	/**
	 * Registra o login de um usuario com sucesso
	 */
	boolean registraLoginSucesso(int idUsuario);

	/**
	 * Registra o login de um usuario com falha
	 */
	boolean registraLoginFalha(int idUsuario);

	/**
	 * Pega a data/hora do ultimo login de um usuario
	 */
	DateTime pegaDataUltimoLogin(int idUsuario);
	
	/**
	 * Pega o numero de tentativas de login com falha de um usuario
	 */
	int pegaNumeroTentativasFalha(int idUsuario);
	
	/**
	 * Armazena um token de troca de senha para um usuario
	 */
	boolean armazenaTokenTrocaSenha(int id, String token);
	
	/**
	 * Verifica se um token de troca de senha e valido para um usuario
	 */
	boolean verificaTokenTrocaSenha(int id, String token, int maxHoras);
}