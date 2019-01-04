package br.unirio.dsw.service.auth;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import br.unirio.dsw.model.Usuario;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa o usuário logado para o Spring Security
 * 
 * @author Marcio
 */
public class UserAuthentication implements Authentication
{
	private static final long serialVersionUID = 8124356846233199802L;
	
	/**
	 * Usuário logado
	 */
	private final Usuario usuario;
	
	/**
	 * Indica se o usuário está logado
	 */
	private @Getter @Setter boolean authenticated = true;

	/**
	 * Inicializa a autenticação do usuário
	 */
	public UserAuthentication(Usuario user)
	{
		this.usuario = user;
	}

	/**
	 * Retorna o nome do usuário
	 */
	@Override
	public String getName()
	{
		return usuario.getNome();
	}

	/**
	 * Retorna a senha do usuário
	 */
	@Override
	public Object getCredentials()
	{
		return usuario.getPassword();
	}

	/**
	 * Retorna todos os detalhes do usuário
	 */
	@Override
	public Usuario getDetails()
	{
		return usuario;
	}

	/**
	 * Retorna o usuário como principal do Spring Security
	 */
	@Override
	public Usuario getPrincipal()
	{
		return usuario;
	}

	/**
	 * Retorna os direitos de acesso do usuário
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return usuario.getAuthorities();
	}

}