/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.common.definitions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * User: serso
 * Date: Mar 29, 2010
 * Time: 10:44:26 PM
 */
public class MessageImpl implements Message {

	private @Nullable Object[] arguments = null;
	private @NotNull String messageCode;
	private @NotNull MessageType messageType;

	public MessageImpl(@NotNull String messageCode, @NotNull MessageType messageType, @Nullable Object... arguments) {
		this.messageCode = messageCode;
		this.arguments = arguments;
		this.messageType = messageType;
	}

	@Override
	public @NotNull String getMessageCode() {
		return this.messageCode;
	}

	@Override
	public @Nullable Object[] getArguments() {
		return this.arguments;
	}

	@NotNull
	@Override
	public MessageType getMessageType() {
		return this.messageType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MessageImpl message = (MessageImpl) o;

		if (!Arrays.equals(arguments, message.arguments)) return false;
		if (!messageCode.equals(message.messageCode)) return false;
		if (messageType != message.messageType) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = arguments != null ? Arrays.hashCode(arguments) : 0;
		result = 31 * result + messageCode.hashCode();
		result = 31 * result + messageType.hashCode();
		return result;
	}
}
