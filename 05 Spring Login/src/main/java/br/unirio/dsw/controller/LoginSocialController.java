package br.unirio.dsw.controller;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
@RequestMapping("/connect")
public class LoginSocialController extends ConnectController
{
	public LoginSocialController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository)
	{
		super(connectionFactoryLocator, connectionRepository);
	}

	@Override
	protected String connectedView(String providerId)
	{
		return "login/facebook/facebookConnected";
	}
}