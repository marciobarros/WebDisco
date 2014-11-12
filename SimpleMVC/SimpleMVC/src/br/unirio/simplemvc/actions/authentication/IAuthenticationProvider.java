package br.unirio.simplemvc.actions.authentication;

import br.unirio.simplemvc.servlets.IUser;

public interface IAuthenticationProvider
{
	/**
	 * Sets the user currently logged into the system
	 */
	void setCurrentUser(IUser user);

	/**
	 * Returns the user currently logged in the system
	 */
	IUser getCurrentUser();

	/**
	 * Invalidates the user currently logged in the system
	 */
	void invalidadeCurrentUser();

	/**
	 * Returns a user, given his/her e-mail
	 */
	IUser getUserByEmail(String email);	
}