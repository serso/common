package org.solovyev.common;

/**
 * User: serso
 * Date: 9/19/11
 * Time: 4:32 PM
 */

import org.jetbrains.annotations.NotNull;

/**
 * Interface converts one object to another
 *
 * @param <FROM> type of object to be converted
 * @param <TO> type of result object (converted object)
 */
public interface Converter<FROM, TO> {

	@NotNull
	TO convert(@NotNull FROM from);
}

