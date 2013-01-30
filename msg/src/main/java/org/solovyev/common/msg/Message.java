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

/**
 * Interface represents translatable user message.
 * Implementation of this class will likely contains
 * some logic for translation message according to
 * it's message code and list of parameters.
 */
public interface Message {

    /**
     * @return message code
     */
	@NotNull
	public String getMessageCode();

    /**
     * @return list of message parameters
     */
	@NotNull
	public List<Object> getParameters();

    /**
     * @return type of message
     */
	@NotNull
	public MessageType getMessageType();

    /**
     *
     * @param locale locate to which current message should be translated
     *
     * @return message string translated to specified locale
     */
	@NotNull
	public String getLocalizedMessage(@NotNull Locale locale);

    /**
     *
     * @return message string translated to deault locale (Locale.getDefault())
     */
	@NotNull
	public String getLocalizedMessage();
}
