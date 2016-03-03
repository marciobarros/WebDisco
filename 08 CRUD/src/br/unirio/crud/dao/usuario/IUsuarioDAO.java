package br.unirio.crud.dao.usuario;

import java.util.List;

import org.joda.time.DateTime;

import br.unirio.crud.model.Usuario;
import br.unirio.simplemvc.dao.OrderingType;

/**
 * Interface para os DAO de usu�rio
 * 
 * @author marcio.barros
 */
public interface IUsuarioDAO
{
	/**
	 * Carrega um usu�rio, dado seu identificador
	 */
	Usuario getUsuarioId(int id);

	/**
	 * Retorna um usu�rio, dado seu nome
	 */
	Usuario getUsuarioNome(String nome);

	/**
	 * Retorna um usu�rio, dado seu e-mail
	 */
	Usuario getUsuarioEmail(String email);

	/**
	 * Conta o n�mero de usu�rios que atendem a um filtro
	 */
	int conta(String filtroNome, String filtroEmail, String filtroEstado);

	/**
	 * Retorna a lista de usu�rios que atendem a um filtro
	 */
	List<Usuario> lista(int pagina, int tamanho, OrdenacaoUsuario campoOrdenacao, OrderingType tipoOrdenacao, String filtroNome, String filtroEmail, String filtroEstado);

	/**
	 * Adiciona um usu�rio no sistema
	 */
	boolean adiciona(Usuario usuario);

	/**
	 * Atualiza os dados de um usu�rio
	 */
	boolean atualiza(Usuario usuario);

	/**
	 * Remove um usu�rio do sistema
	 */
	boolean remove(int id);

	/**
	 * Atualiza a senha de um usu�rio
	 */
	boolean atualizaSenha(int idUsuario, String senhaCodificada);

	/**
	 * Registra o login de um usu�rio com sucesso
	 */
	boolean registraLoginSucesso(int idUsuario);

	/**
	 * Registra o login de um usu�rio com falha
	 */
	boolean registraLoginFalha(int idUsuario);

	/**
	 * Pega a data/hora do �ltimo login de um usu�rio
	 */
	DateTime pegaDataUltimoLogin(int idUsuario);
	
	/**
	 * Pega o n�mero de tentativas de login com falha de um usu�rio
	 */
	int pegaNumeroTentativasFalha(int idUsuario);
	
	/**
	 * Armazena um token de troca de senha para um usu�rio
	 */
	boolean armazenaTokenTrocaSenha(int id, String token);
	
	/**
	 * Verifica se um token de troca de senha � v�lido para um usu�rio
	 */
	boolean verificaTokenTrocaSenha(int id, String token, int maxHoras);
}