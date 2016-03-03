package br.unirio.simplemvc.actions.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.unirio.simplemvc.servlets.AuthenticationService;
import br.unirio.simplemvc.servlets.IUser;

/**
 * Implementation of the authentication provider for actions enacted by a servlet
 * 
 * @author marcio.barros
 */
public class ServletAuthenticationProvider implements IAuthenticationProvider
{
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	/**
	 * Initializes the user authenticator for servlets
	 */
	public ServletAuthenticationProvider(HttpServletRequest request, HttpServletResponse response)
	{
		this.request = request;
		this.response = response;
	}
	
	/**
	 * Sets the user currently logged into the system
	 */
	@Override
	public void setCurrentUser(IUser user)
	{
		AuthenticationService.setCurrentUser(request, response, user);
	}

	/**
	 * Returns the user currently logged in the system
	 */
	@Override
	public IUser getCurrentUser()
	{
		return AuthenticationService.getUser(request);
	}

	/**
	 * Invalidates the user currently logged in the system
	 */
	@Override
	public void invalidadeCurrentUser()
	{
		AuthenticationService.invalidateCurrentUser(request, response);
	}

	/**
	 * Returns a user, given his/her e-mail
	 */
	@Override
	public IUser getUserByEmail(String email)
	{
        return AuthenticationService.getUserService(request).getUserEmail(email);
	}
}