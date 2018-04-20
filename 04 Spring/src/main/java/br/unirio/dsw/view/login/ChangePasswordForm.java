package br.unirio.dsw.view.login;

import lombok.Data;

/**
 * Classe do formulário de troca de senha
 * 
 * @author marciobarros
 */
public @Data class ChangePasswordForm 
{
	private String currentPassword;
	private String newPassword;
	private String repeatNewPassword;
}
