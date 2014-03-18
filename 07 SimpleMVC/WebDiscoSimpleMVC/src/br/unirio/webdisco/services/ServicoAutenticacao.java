package br.unirio.webdisco.services;

import br.unirio.simplemvc.servlets.IUser;
import br.unirio.simplemvc.servlets.IUserService;

public class ServicoAutenticacao implements IUserService
{
	@Override
	public IUser getUserId(int id) 
	{
		return null;
	}

	@Override
	public IUser getUserEmail(String email) 
	{
		return null;
	}

	@Override
	public String getChangePasswordAction() 
	{
		return null;
	}

	@Override
	public String getLoginAction() 
	{
		return null;
	}
}