package org.solovyev.common.utils;

/**
 * User: serso
 * Date: 5/18/11
 * Time: 11:20 AM
 */

import org.jetbrains.annotations.Nullable;

/**
 * Class represents interface for mapping string value with typed object
 * @param <T>
 */
public interface Mapper<T> extends Formatter<T>, Parser<T> {

}
