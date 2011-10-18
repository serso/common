/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

/**
 * User: serso
 * Date: Mar 29, 2010
 * Time: 10:45:56 PM
 */
public interface Message<T> {

	@NotNull
	public T getMessageCode();

	@Nullable
	public List<Object> getArguments();

	@NotNull
	public MessageType getMessageType();

	@NotNull
	public String formatMessage(@NotNull String messagePattern, @NotNull Locale locale);
}
