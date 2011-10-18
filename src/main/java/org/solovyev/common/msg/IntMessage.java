package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * User: serso
 * Date: 10/18/11
 * Time: 11:16 PM
 */
public final class IntMessage extends AbstractMessage<Integer> {

	public IntMessage(@NotNull Integer messageCode, @NotNull MessageType messageType, @Nullable Object... arguments) {
		super(messageCode, messageType, arguments);
	}

	public IntMessage(@NotNull Integer messageCode, @NotNull MessageType messageType, @Nullable List<?> arguments) {
		super(messageCode, messageType, arguments);
	}
}
