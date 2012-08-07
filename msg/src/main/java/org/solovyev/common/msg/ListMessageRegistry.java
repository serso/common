package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: 10/18/11
 * Time: 11:22 PM
 */
public class ListMessageRegistry implements MessageRegistry {

	@NotNull
	private final List<Message> messages = new ArrayList<Message>();

	@Override
	public void addMessage(@NotNull Message message) {
		if ( !messages.contains(message) ) {
			messages.add(message);
		}
	}

	@NotNull
	@Override
	public Message getMessage() {
		return this.messages.remove(0);
	}

	@Override
	public boolean hasMessage() {
		return !this.messages.isEmpty();
	}
}
