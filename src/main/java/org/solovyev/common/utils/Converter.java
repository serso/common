package org.solovyev.common.utils;

/**
 * User: serso
 * Date: 9/19/11
 * Time: 4:32 PM
 */

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Interface converts one object to another
 *
 * @param <FROM> type of object to be converted
 * @param <TO> type of result object (converted object)
 */
public interface Converter<FROM, TO> {

	@Nullable
	TO convert(@NotNull FROM from);
}

