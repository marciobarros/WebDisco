/**
 * Interface que representa um usu�rio gen�rico para sistemas Web
 *
 * @author M�rcio Barros
 * @version 1.0
 */

package br.unirio.logging.tags;

public interface GenericUser
{
    abstract boolean checkLevel (String level);
}