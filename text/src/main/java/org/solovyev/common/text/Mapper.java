package org.solovyev.common.text;

/**
 * User: serso
 * Date: 5/18/11
 * Time: 11:20 AM
 */

/**
 * Class represents interface for mapping string value with typed object
 * @param <T>
 */
public interface Mapper<T> extends Formatter<T>, Parser<T> {

}
