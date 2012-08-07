package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/18/11
 * Time: 11:18 PM
 */
public interface MessageRegistry {

	void addMessage(@NotNull Message message);

	@NotNull
	Message getMessage();

	boolean hasMessage();
}
