/**
 * Interface que representa um usuario generico para sistemas Web
 *
 * @author Marcio Barros
 * @version 1.0
 */

package br.unirio.logging.tags;

public interface GenericUser
{
    abstract boolean checkLevel (String level);
}