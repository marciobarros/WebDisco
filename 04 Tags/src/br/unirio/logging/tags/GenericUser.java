/**
 * Interface que representa um usuário genérico para sistemas Web
 *
 * @author Márcio Barros
 * @version 1.0
 */

package br.unirio.logging.tags;

public interface GenericUser
{
    abstract boolean checkLevel (String level);
}