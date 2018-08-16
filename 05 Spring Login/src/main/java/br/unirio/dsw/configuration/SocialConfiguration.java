package br.unirio.dsw.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import br.unirio.dsw.controller.LoginSocialController;

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
	
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) 
    {
        String twitterKey = env.getProperty("twitter.consumer.key");
    	String twitterSecret = env.getProperty("twitter.consumer.secret");
    	cfConfig.addConnectionFactory(new TwitterConnectionFactory(twitterKey, twitterSecret));
        
        String facebookId = env.getProperty("facebook.appId");
        String facebookSecret = env.getProperty("facebook.appSecret");
        cfConfig.addConnectionFactory(new FacebookConnectionFactory(facebookId, facebookSecret));
        
//        String linkedinKey = env.getProperty("linkedin.consumerKey");
//        String linkedinSecret = env.getProperty("linkedin.consumerSecret");
//        cfConfig.addConnectionFactory(new LinkedInConnectionFactory(linkedinKey, linkedinSecret));
    }
    
    @Override
    public UserIdSource getUserIdSource() 
    {
        return new AuthenticationNameUserIdSource();
    }
 
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) 
    {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setDatabaseName("uniriochamadas");
        dataSource.setUser(br.unirio.dsw.configuration.Configuration.getDatabaseUser());
        dataSource.setPassword(br.unirio.dsw.configuration.Configuration.getDatabasePassword());
        dataSource.setServerName("localhost");
    	return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
    }
 
    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) 
    {
        return new LoginSocialController(connectionFactoryLocator, connectionRepository);
    }
}