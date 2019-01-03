package br.unirio.dsw.service.auth;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import br.unirio.dsw.model.Usuario;
import lombok.Getter;
import lombok.Setter;

public class UserAuthentication implements Authentication
{
	// TODO não poderia substituir a classe usuário no gerador de tokens???
	
	private static final long serialVersionUID = 8124356846233199802L;
	
	private final Usuario user;
	private @Getter @Setter boolean authenticated = true;

	public UserAuthentication(Usuario user) 
	{
		this.user = user;
	}

	@Override
	public String getName()
	{
		return user.getNome();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return user.getAuthorities();
	}

	@Override
	public Object getCredentials()
	{
		return user.getPassword();
	}

	@Override
	public Usuario getDetails()
	{
		return user;
	}

	@Override
	public Object getPrincipal()
	{
		return user.getNome();
	}
}