package org.solovyev.common;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/29/11
 * Time: 12:36 PM
 */
public interface JBuilder<T> {

	@NotNull
	T create();
}
