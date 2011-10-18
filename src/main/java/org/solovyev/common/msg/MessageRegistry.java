package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/18/11
 * Time: 11:18 PM
 */
public interface MessageRegistry<T extends Message<?>> {

	void addMessage(@NotNull T message);

	@NotNull
	T getMessage();

	boolean hasMessage();
}
