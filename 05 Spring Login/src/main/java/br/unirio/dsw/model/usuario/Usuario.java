package br.unirio.dsw.model.usuario;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.social.security.SocialUserDetails;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa um usuário do sistema
 * 
 * @author marciobarros
 */
public class Usuario extends User implements SocialUserDetails
{
	private static final long serialVersionUID = 7512107428170018274L;

	private @Setter @Getter int id;
	private @Setter @Getter String nome;
	private @Setter @Getter String tokenLogin;
	private @Setter @Getter DateTime dataTokenLogin;
	private @Setter @Getter int contadorFalhasLogin;
	private @Setter @Getter DateTime dataUltimoLogin;
	private @Setter @Getter boolean bloqueado;
	private @Setter @Getter boolean administrador;
	private @Setter @Getter String providerId;
	private @Setter @Getter String providerUserId;
	private @Setter @Getter String profileUrl;
	private @Setter @Getter String imageUrl;
	private @Setter @Getter String accessToken;
	private @Setter @Getter String secret;
	private @Setter @Getter String refreshToken;
	private @Setter @Getter long expireTime;

	/**
	 * Inicializa um usuário
	 */
	public Usuario(String nome, String email, String senha, boolean bloqueado)
	{
        super(email, senha, true, true, true, !bloqueado, createAuthoritiesFromBasicRole());
		this.id = -1;
		this.nome = nome;
		this.tokenLogin = "";
		this.dataTokenLogin = null;
		this.contadorFalhasLogin = 0;
		this.dataUltimoLogin = null;
		this.bloqueado = bloqueado;
		this.administrador = false;
		this.providerId = "";
		this.providerUserId = "";
		this.profileUrl = "";
		this.imageUrl = "";
		this.accessToken = "";
		this.secret = "";
		this.refreshToken = "";
		this.expireTime = 0;
	}

	/**
	 * Cria os direitos de acesso relacionado ao papel básico do usuário
	 */
	private static Set<GrantedAuthority> createAuthoritiesFromBasicRole()
	{
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_BASIC"));
		return authorities;
	}

	/**
	 * Retorna o identificador do usuário
	 */
	@Override
	public String getUserId()
	{
		return "" + id;
	}
	
	/**
	 * Retorna a data do último login formatada
	 */
	public String getDataUltimoLoginFormatada()
	{
		DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
		return dtf.print(dataUltimoLogin);
	}
}