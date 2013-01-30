package org.solovyev.common.text;

/**
 * User: serso
 * Date: 5/18/11
 * Time: 11:20 AM
 */

/**
 * Class represents an interface for mapping string value to the typed object.
 *
 * @param <T>
 * @see Formatter
 * @see Parser
 */
public interface Mapper<T> extends Formatter<T>, Parser<T> {
}
