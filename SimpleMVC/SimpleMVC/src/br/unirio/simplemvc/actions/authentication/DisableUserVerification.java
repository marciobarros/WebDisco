package br.unirio.simplemvc.actions.authentication;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Esta anota��o permite que as a��es desliguem a verifica��o de usu�rios que
 * precisam trocar suas senhas e de usu�rios desativados pelo administrador.
 * Isto � �til para as a��es que n�o dependem de usu�rio logado e para as que
 * tratam da troca de senha.
 * 
 * @author marcio.barros
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DisableUserVerification
{
}