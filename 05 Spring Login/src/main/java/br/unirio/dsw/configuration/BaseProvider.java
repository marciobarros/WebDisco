package br.unirio.dsw.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.google.api.Google;
import org.springframework.social.linkedin.api.LinkedIn;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BaseProvider
{
	private @Getter @Setter Facebook facebook;
	private @Getter @Setter Google google;
	private @Getter @Setter LinkedIn linkedIn;
	private @Getter @Setter ConnectionRepository connectionRepository;

	public BaseProvider(Facebook facebook, Google google, LinkedIn linkedIn, ConnectionRepository connectionRepository)
	{
		this.facebook = facebook;
		this.connectionRepository = connectionRepository;
		this.google = google;
		this.linkedIn = linkedIn;
	}
}