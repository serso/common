/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

/**
 * User: serso
 * Date: Mar 29, 2010
 * Time: 10:45:56 PM
 */
public interface Message {

	@NotNull
	public String getMessageCode();

	@NotNull
	public List<Object> getParameters();

	@NotNull
	public MessageType getMessageType();

	@NotNull
	public String getLocalizedMessage(@NotNull Locale locale);

	@NotNull
	public String getLocalizedMessage();
}
