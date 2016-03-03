package br.unirio.crud.model;

import lombok.Data;

import org.joda.time.DateTime;

import br.unirio.simplemvc.servlets.IUser;

/**
 * Classe que representa um usuário no sistema
 * 
 * @author Marcio
 */
public @Data class Usuario implements IUser
{
	private int id = -1;
	private DateTime dataRegistro = null;
	private String nome = "";
	private String email = "";
	private String senhaCodificada = "";
	private String endereco = "";
	private String complemento = "";
	private Estado estado = null;
	private String municipio = "";
	private String cep = "";
	private String telefoneFixo = "";
	private String telefoneCelular = "";
	private boolean deveTrocarSenha = false;
	
	@Override
	public String getName()
	{
		return nome;
	}

	@Override
	public boolean isActive()
	{
		return true;
	}

	@Override
	public boolean mustChangePassword()
	{
		return deveTrocarSenha;
	}

	@Override
	public boolean checkLevel(String nivel)
	{
		return true;
	}
}