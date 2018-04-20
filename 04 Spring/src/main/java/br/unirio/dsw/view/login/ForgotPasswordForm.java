package br.unirio.dsw.view.login;

import lombok.Data;

/**
 * Classe do formulário de recuperação de senha
 * 
 * @author marciobarros
 */
public @Data class ForgotPasswordForm
{
	private String email = "";
}