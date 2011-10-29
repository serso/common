package org.solovyev.common.definitions;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/29/11
 * Time: 12:36 PM
 */
public interface IBuilder<T> {

	@NotNull
	T create();
}
