package br.unirio.crud.dao.usuario;

import java.util.List;

import org.joda.time.DateTime;

import br.unirio.crud.model.Usuario;
import br.unirio.simplemvc.dao.OrderingType;

/**
 * Interface para os DAO de usuário
 * 
 * @author marcio.barros
 */
public interface IUsuarioDAO
{
	/**
	 * Carrega um usuário, dado seu identificador
	 */
	Usuario getUsuarioId(int id);

	/**
	 * Retorna um usuário, dado seu nome
	 */
	Usuario getUsuarioNome(String nome);

	/**
	 * Retorna um usuário, dado seu e-mail
	 */
	Usuario getUsuarioEmail(String email);

	/**
	 * Conta o número de usuários que atendem a um filtro
	 */
	int conta(String filtroNome, String filtroEmail, String filtroEstado);

	/**
	 * Retorna a lista de usuários que atendem a um filtro
	 */
	List<Usuario> lista(int pagina, int tamanho, OrdenacaoUsuario campoOrdenacao, OrderingType tipoOrdenacao, String filtroNome, String filtroEmail, String filtroEstado);

	/**
	 * Adiciona um usuário no sistema
	 */
	boolean adiciona(Usuario usuario);

	/**
	 * Atualiza os dados de um usuário
	 */
	boolean atualiza(Usuario usuario);

	/**
	 * Remove um usuário do sistema
	 */
	boolean remove(int id);

	/**
	 * Atualiza a senha de um usuário
	 */
	boolean atualizaSenha(int idUsuario, String senhaCodificada);

	/**
	 * Registra o login de um usuário com sucesso
	 */
	boolean registraLoginSucesso(int idUsuario);

	/**
	 * Registra o login de um usuário com falha
	 */
	boolean registraLoginFalha(int idUsuario);

	/**
	 * Pega a data/hora do último login de um usuário
	 */
	DateTime pegaDataUltimoLogin(int idUsuario);
	
	/**
	 * Pega o número de tentativas de login com falha de um usuário
	 */
	int pegaNumeroTentativasFalha(int idUsuario);
	
	/**
	 * Armazena um token de troca de senha para um usuário
	 */
	boolean armazenaTokenTrocaSenha(int id, String token);
	
	/**
	 * Verifica se um token de troca de senha é válido para um usuário
	 */
	boolean verificaTokenTrocaSenha(int id, String token, int maxHoras);
}