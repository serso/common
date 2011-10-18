package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: 10/18/11
 * Time: 11:22 PM
 */
public class ListMessageRegistry<T extends Message<?>> implements MessageRegistry<T> {

	@NotNull
	private final List<T> messages = new ArrayList<T>();

	@Override
	public void addMessage(@NotNull T message) {
		if ( !messages.contains(message) ) {
			messages.add(message);
		}
	}

	@NotNull
	@Override
	public T getMessage() {
		return this.messages.remove(0);
	}

	@Override
	public boolean hasMessage() {
		return !this.messages.isEmpty();
	}
}
