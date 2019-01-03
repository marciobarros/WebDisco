package br.unirio.dsw.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.unirio.dsw.model.Usuario;
import br.unirio.dsw.service.dao.UsuarioDAO;

/**
 * Classe que permite que os mecanismos de segurança tenham acesso aos dados dos usuários
 * 
 * @author Marcio
 */
@Service
class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService
{
	/**
	 * Serviço de acesso ao banco de dados de usuários
	 */
	@Autowired
	private UsuarioDAO usuarioDAO;

	/**
	 * Carrega um usuário, dado seu e-mail
	 */
	@Override
	public final Usuario loadUserByUsername(String username) throws UsernameNotFoundException
	{
		final Usuario user = usuarioDAO.carregaUsuarioEmail(username);

		if (user == null)
			throw new UsernameNotFoundException("user not found");

		return user;
	}
	
	/**
	 * Registra um login realizado com sucesso
	 */
	public void registraLoginSucesso(String email)
	{
		usuarioDAO.registraLoginSucesso(email);
	}
	
	/**
	 * Registra um login realizado com falha
	 */
	public void registraLoginFalha(String email)
	{
		usuarioDAO.registraLoginFalha(email);
	}
}