/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.common.definitions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: Mar 29, 2010
 * Time: 10:45:56 PM
 */
public interface Message {

	@NotNull
	public String getMessageCode();

	@Nullable
	public Object[] getArguments();

	@NotNull
	public MessageType getMessageType();
}
