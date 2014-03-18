package br.unirio.simplemvc.actions.authentication;

import java.util.ArrayList;
import java.util.List;

import br.unirio.simplemvc.servlets.IUser;

/**
 * Implementation of the authentication provider for testing purposes
 * 
 * @author marcio.barros
 */
public class TestAuthenticationProvider implements IAuthenticationProvider
{
	private IUser currentUser;
	private List<IUser> knownUsers;
	
	/**
	 * Initializes the authentication provider
	 */
	public TestAuthenticationProvider()
	{
		this.currentUser = null;
		this.knownUsers = new ArrayList<IUser>();
	}
	
	/**
	 * Sets the current logged user - for testing purposes
	 */
	public TestAuthenticationProvider setupCurrentUser(IUser user)
	{
		this.currentUser = user;
		return this;
	}
	
	/**
	 * Adds a known user to the authentication provider - for testing purposes
	 */
	public TestAuthenticationProvider addKnownUser(IUser user)
	{
		this.knownUsers.add(user);
		return this;
	}

	/**
	 * Sets the currently logged user
	 */
	@Override
	public void setCurrentUser(IUser user)
	{
		this.currentUser = user;
	}

	/**
	 * Gets the currently logged user
	 */
	@Override
	public IUser getCurrentUser()
	{
		return this.currentUser;
	}

	/**
	 * Invalidates the currently logged user
	 */
	@Override
	public void invalidadeCurrentUser()
	{
		this.currentUser = null;
	}

	/**
	 * Returns a user, given his/her e-mails
	 */
	@Override
	public IUser getUserByEmail(String email)
	{
		for (IUser user : knownUsers)
			if (user.getEmail().compareToIgnoreCase(email) == 0)
				return user;
		
		return null;
	}
}