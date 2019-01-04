package br.unirio.dsw.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa um usuário do sistema
 * 
 * @author marciobarros
 */
public class Usuario implements UserDetails
{
	private static final long serialVersionUID = 7512107428170018274L;

	private @Setter @Getter int id;
	private @Setter @Getter String email;
	private @Setter @Getter String nome;
	private @Setter @Getter String senha;
	private @Setter @Getter String tokenLogin;
	private @Setter @Getter DateTime dataTokenLogin;
	private @Setter @Getter int contadorFalhasLogin;
	private @Setter @Getter DateTime dataUltimoLogin;
	private @Setter @Getter boolean bloqueado;
	private @Setter @Getter boolean administrador;

	/**
	 * Inicializa um usuário
	 */
	public Usuario()
	{
		this.id = -1;
		this.email = "";
		this.nome = "";
		this.senha = "";
		this.tokenLogin = "";
		this.dataTokenLogin = null;
		this.contadorFalhasLogin = 0;
		this.dataUltimoLogin = null;
		this.bloqueado = false;
		this.administrador = false;
	}
	
	/**
	 * Retorna a data do último login formatada
	 */
	public String getDataUltimoLoginFormatada()
	{
		DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
		return dtf.print(dataUltimoLogin);
	}

	/**
	 * Retorna o login do usuário
	 */
	@Override
	public String getUsername()
	{
		return email;
	}

	/**
	 * Retorna a senha do usuário
	 */
	@Override
	public String getPassword()
	{
		return senha;
	}

	/**
	 * Verifica se a conta expirou
	 */
	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	/**
	 * Verifica se a conta está desbloqueada
	 */
	@Override
	public boolean isAccountNonLocked()
	{
		return !bloqueado;
	}

	/**
	 * Verifica se as credenciais da conta expiraram
	 */
	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	/**
	 * Verifica se a conta está ativa
	 */
	@Override
	public boolean isEnabled()
	{
		return !bloqueado;
	}

	/**
	 * Retorna os direitos de acesso do usuário
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_BASIC"));
		
		if (administrador)
	        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		
		return authorities;
	}
}