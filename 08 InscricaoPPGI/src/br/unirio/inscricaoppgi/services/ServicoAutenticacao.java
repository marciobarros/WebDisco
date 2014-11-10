package br.unirio.inscricaoppgi.services;

import br.unirio.inscricaoppgi.dao.DAOFactory;
import br.unirio.simplemvc.servlets.IUser;
import br.unirio.simplemvc.servlets.IUserService;

public class ServicoAutenticacao implements IUserService
{
	/**
	 * Retorna um usuário, dado seu identificador
	 */
	@Override
	public IUser getUserId(long id) 
	{
		return DAOFactory.getUsuarioDAO().get(id);
	}

	/**
	 * Retorna um usuário, dado seu e-mail
	 */
	@Override
	public IUser getUserEmail(String email) 
	{
		return DAOFactory.getUsuarioDAO().getUsuarioEmail(email);
	}

	/**
	 * Retorna a URL para login
	 */
	@Override
	public String getLoginAction() 
	{
		return "/login/login.do";
	}

	/**
	 * Retorna a URL para troca de senha
	 */
	@Override
	public String getChangePasswordAction() 
	{
		return "/login/preparaTrocaSenha.do";
	}
}