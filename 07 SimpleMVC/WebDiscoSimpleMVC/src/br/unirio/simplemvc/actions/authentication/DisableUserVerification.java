package br.unirio.simplemvc.actions.authentication;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Esta anotação permite que as ações desliguem a verificação de usuários que
 * precisam trocar suas senhas e de usuários desativados pelo administrador.
 * Isto é útil para as ações que não dependem de usuário logado e para as que
 * tratam da troca de senha.
 * 
 * @author marcio.barros
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DisableUserVerification
{
}