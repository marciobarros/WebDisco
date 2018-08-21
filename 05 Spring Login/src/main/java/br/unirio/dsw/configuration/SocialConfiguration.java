package br.unirio.dsw.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;

import br.unirio.dsw.model.usuario.Usuario;
import br.unirio.dsw.service.dao.UsuarioDAO;

/**
 * Spring Social Configuration
 * 
 * @author marcio.barros
 */
@Configuration
@EnableSocial
public class SocialConfiguration implements SocialConfigurer
{
	// https://www.petrikainulainen.net/programming/spring-framework/adding-social-sign-in-to-a-spring-mvc-web-application-configuration/
	// https://docs.spring.io/autorepo/docs/spring-social/1.1.0.RELEASE/reference/htmlsingle/#section_signin
	
	@Autowired
	private UsuarioDAO userDAO;
	
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) 
    {
        String twitterKey = env.getProperty("twitter.consumer.key");
    	String twitterSecret = env.getProperty("twitter.consumer.secret");
    	cfConfig.addConnectionFactory(new TwitterConnectionFactory(twitterKey, twitterSecret));
        
        String facebookId = env.getProperty("facebook.appId");
        String facebookSecret = env.getProperty("facebook.appSecret");
        cfConfig.addConnectionFactory(new FacebookConnectionFactory(facebookId, facebookSecret));
        
        String linkedinKey = env.getProperty("linkedin.client.key");
        String linkedinSecret = env.getProperty("linkedin.client.secret");
        cfConfig.addConnectionFactory(new LinkedInConnectionFactory(linkedinKey, linkedinSecret));
        
        String googleKey = env.getProperty("google.client.key");
        String googleSecret = env.getProperty("google.client.secret");
        cfConfig.addConnectionFactory(new GoogleConnectionFactory(googleKey, googleSecret));
    }
    
    @Override
    public UserIdSource getUserIdSource() 
    {
        return new AuthenticationNameUserIdSource();
    }
 
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) 
    {
//        MysqlDataSource dataSource = new MysqlDataSource();
//        dataSource.setDatabaseName("uniriochamadas");
//        dataSource.setUser(br.unirio.dsw.configuration.Configuration.getDatabaseUser());
//        dataSource.setPassword(br.unirio.dsw.configuration.Configuration.getDatabasePassword());
//        dataSource.setServerName("localhost");
    	return new SocialUserConnectionRepository(userDAO, connectionFactoryLocator, Encryptors.noOpText());
    }
 
    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) 
    {
        return new LoginSocialController(connectionFactoryLocator, connectionRepository);
    }
}

@RequestMapping("/connect")
class LoginSocialController extends ConnectController
{
	public LoginSocialController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository)
	{
		super(connectionFactoryLocator, connectionRepository);
	}

	@Override
	protected String connectedView(String providerId)
	{
		return "login/" + providerId + "/" + providerId + "Connected";
	}
	
	@Override
	protected String connectView(String providerId) {
		return "login/" + providerId + "/" + providerId + "Connect";		
	}
}

class SocialUserConnectionRepository implements UsersConnectionRepository
{
	private final UsuarioDAO usuarioDAO;
	
	private final ConnectionFactoryLocator connectionFactoryLocator;

	private final TextEncryptor textEncryptor;

	public SocialUserConnectionRepository(UsuarioDAO usuarioDAO, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) 
	{
		this.usuarioDAO = usuarioDAO;
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.textEncryptor = textEncryptor;
	}

	@Override
	public List<String> findUserIdsWithConnection(Connection<?> connection)
	{
		ConnectionKey key = connection.getKey();
		Usuario usuario = usuarioDAO.carregaUsuarioProvedor(key.getProviderId(), key.getProviderUserId());
		
//		UserProfile profile = connection.fetchUserProfile();
//		String email = profile.getEmail();

		if (usuario == null) 
		{
			usuario = new Usuario(connection.getDisplayName(), connection.getKey().getProviderUserId(), "", false);
			usuarioDAO.criaNovoUsuario(usuario);
			createConnectionRepository("" + usuario.getId()).addConnection(connection);
		}
		
		return Arrays.asList("" + usuario.getId());
	}

	@Override
	public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds)
	{
		Set<String> ids = new HashSet<String>();
		
		for (String providerUserId : providerUserIds)
		{
			Usuario usuario = usuarioDAO.carregaUsuarioProvedor(providerId, providerUserId);
			ids.add("" + usuario.getId());
		}
		
		return ids;
	}

	@Override
	public ConnectionRepository createConnectionRepository(String userId)
	{
		if (userId == null)
			throw new IllegalArgumentException("userId cannot be null");

		return new UsuarioConnectionRepository(Integer.parseInt(userId), usuarioDAO, connectionFactoryLocator, textEncryptor);
	}
}

class UsuarioConnectionRepository implements ConnectionRepository
{
	private final int userId;

	private final UsuarioDAO usuarioDAO;
	
	private final ConnectionFactoryLocator connectionFactoryLocator;

	private final TextEncryptor textEncryptor;

	private final ServiceProviderConnectionMapper connectionMapper;

	public UsuarioConnectionRepository(int userId, UsuarioDAO usuarioDAO, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor)
	{
		this.userId = userId;
		this.usuarioDAO = usuarioDAO;
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.textEncryptor = textEncryptor;
		this.connectionMapper = new ServiceProviderConnectionMapper(connectionFactoryLocator, textEncryptor);
	}

	public MultiValueMap<String, Connection<?>> findAllConnections()
	{
		MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();

		Usuario usuario = usuarioDAO.carregaUsuarioId(userId);

		if (usuario == null)
			return connections;
		
		String providerId = usuario.getProviderId();
		connections.put(providerId, new LinkedList<Connection<?>>());
		connections.add(providerId, connectionMapper.mapConnection(usuario));
		return connections;
	}

	public List<Connection<?>> findConnections(String providerId)
	{
		List<Connection<?>> connections = new ArrayList<Connection<?>>();
		Usuario usuario = usuarioDAO.carregaUsuarioId(userId);

		if (usuario == null)
			return connections;

		if (usuario.getProviderId().compareTo(providerId) != 0)
			return connections;
		
		connections.add(connectionMapper.mapConnection(usuario));
		return connections;
	}

	@SuppressWarnings("unchecked")
	public <A> List<Connection<A>> findConnections(Class<A> apiType)
	{
		List<?> connections = findConnections(getProviderId(apiType));
		return (List<Connection<A>>) connections;
	}

	public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUsers)
	{
		if (providerUsers == null || providerUsers.isEmpty())
			throw new IllegalArgumentException("Unable to execute find: no providerUsers provided");

		MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();
		Usuario usuario = usuarioDAO.carregaUsuarioId(userId);

		if (usuario == null)
			return connections;
		
		String providerId = usuario.getProviderId();
		connections.put(providerId, new LinkedList<Connection<?>>());
		connections.add(providerId, connectionMapper.mapConnection(usuario));
		return connections;
	}

	public Connection<?> getConnection(ConnectionKey connectionKey)
	{
		Usuario usuario = usuarioDAO.carregaUsuarioId(userId);

		if (usuario == null)
			return null;
		
		return connectionMapper.mapConnection(usuario);
	}

	@SuppressWarnings("unchecked")
	public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId)
	{
		String providerId = getProviderId(apiType);
		return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId));
	}

	@SuppressWarnings("unchecked")
	public <A> Connection<A> getPrimaryConnection(Class<A> apiType)
	{
		String providerId = getProviderId(apiType);
		Usuario usuario = usuarioDAO.carregaUsuarioId(userId);

		if (usuario == null)
			throw new NotConnectedException(providerId);

		if (usuario.getProviderId().compareTo(providerId) != 0)
			throw new NotConnectedException(providerId);
		
		return (Connection<A>) connectionMapper.mapConnection(usuario);
	}

	public <A> Connection<A> findPrimaryConnection(Class<A> apiType)
	{
		return getPrimaryConnection(apiType);
	}

	public void addConnection(Connection<?> connection)
	{
		updateConnection(connection);
	}

	public void updateConnection(Connection<?> connection)
	{
		ConnectionData data = connection.createData();

		Usuario usuario = usuarioDAO.carregaUsuarioId(userId);
		usuario.setProviderId(data.getProviderId());
		usuario.setProviderUserId(data.getProviderUserId());
		usuario.setProfileUrl(data.getProfileUrl());
		usuario.setImageUrl(data.getImageUrl());
		usuario.setAccessToken(encrypt(data.getAccessToken()));
		usuario.setRefreshToken(encrypt(data.getRefreshToken()));
		usuario.setSecret(encrypt(data.getSecret()));
		usuario.setExpireTime(data.getExpireTime());
		usuarioDAO.conectaUsuario(usuario);
		//throw new DuplicateConnectionException(connection.getKey());
	}

	public void removeConnections(String providerId)
	{
		Usuario usuario = usuarioDAO.carregaUsuarioId(userId);
		
		if (usuario != null  && usuario.getProviderId().compareTo(providerId) == 0)
		{
			usuario.setProviderId("");
			usuario.setProviderUserId("");
			usuario.setProfileUrl("");
			usuario.setImageUrl("");
			usuario.setAccessToken("");
			usuario.setRefreshToken("");
			usuario.setSecret("");
			usuario.setExpireTime(0);
			usuarioDAO.conectaUsuario(usuario);
		}
	}

	public void removeConnection(ConnectionKey connectionKey)
	{
		removeConnections(connectionKey.getProviderId());
	}

	private <A> String getProviderId(Class<A> apiType)
	{
		return connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
	}

	private String encrypt(String text)
	{
		return text != null ? textEncryptor.encrypt(text) : text;
	}
}

class ServiceProviderConnectionMapper
{
	private final ConnectionFactoryLocator connectionFactoryLocator;

	private final TextEncryptor textEncryptor;

	public ServiceProviderConnectionMapper(ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) 
	{
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.textEncryptor = textEncryptor;
	}

	public List<Connection<?>> mapConnections(List<Usuario> usuarios)
	{
		List<Connection<?>> connections = new ArrayList<Connection<?>>();
		
		for (Usuario userConnection : usuarios)
			connections.add(mapConnection(userConnection));
		
		return connections;
	}

	public Connection<?> mapConnection(Usuario usuario)
	{
		ConnectionData connectionData = mapConnectionData(usuario);
		ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId());
		return connectionFactory.createConnection(connectionData);
	}

	private ConnectionData mapConnectionData(Usuario usuario)
	{
		return new ConnectionData(usuario.getProviderId(), usuario.getProviderUserId(),
				usuario.getNome(), usuario.getProfileUrl(), usuario.getImageUrl(),
				decrypt(usuario.getAccessToken()), decrypt(usuario.getSecret()),
				decrypt(usuario.getRefreshToken()), usuario.getExpireTime());
	}

	private String decrypt(String encryptedText)
	{
		return encryptedText != null ? textEncryptor.decrypt(encryptedText) : encryptedText;
	}
}