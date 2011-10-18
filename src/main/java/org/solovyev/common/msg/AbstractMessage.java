/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.utils.CollectionsUtils;
import org.solovyev.common.utils.EqualsTool;
import org.solovyev.common.utils.ListEqualizer;
import org.solovyev.common.utils.StringUtils;
import org.solovyev.common.utils.history.HashCodeBuilder;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * User: serso
 * Date: Mar 29, 2010
 * Time: 10:44:26 PM
 */
public abstract class AbstractMessage<T> implements Message<T> {

	private @Nullable List<?> arguments = null;
	private @NotNull T messageCode;
	private @NotNull MessageType messageType;

	protected AbstractMessage(@NotNull T messageCode, @NotNull MessageType messageType, @Nullable Object... arguments) {
		this(messageCode, messageType, CollectionsUtils.asList(arguments));
	}

	protected AbstractMessage(@NotNull T messageCode, @NotNull MessageType messageType, @Nullable List<?> arguments) {
		this.messageCode = messageCode;
		this.arguments = (arguments == null ? Collections.EMPTY_LIST : new ArrayList<Object>(arguments));
		this.messageType = messageType;
	}

	@Override
	public @NotNull T getMessageCode() {
		return this.messageCode;
	}

	@Override
	public @Nullable List<Object> getArguments() {
		return Collections.unmodifiableList(this.arguments);
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

		AbstractMessage abstractMessage = (AbstractMessage) o;

		if (!EqualsTool.areEqual(arguments, abstractMessage.arguments, new ListEqualizer(true, null))) return false;
		if (!messageCode.equals(abstractMessage.messageCode)) return false;
		if (messageType != abstractMessage.messageType) return false;

		return true;
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder hcb = new HashCodeBuilder();

		hcb.append(messageCode);
		hcb.append(messageType);
		hcb.append(arguments);

		return hcb.toHashCode();
	}

	/**
	 * Method converts message to string setting passed message parameters and translating some of them.
	 *
	 * @param messagePattern pattern of message
	 * @param locale		 language to which parameters should be translated (if possible)
	 * @return message as string with properly translated and set parameters
	 */
	@NotNull
	public String formatMessage(@NotNull String messagePattern, @NotNull Locale locale) {
		String result = null;

		if (!StringUtils.isEmpty(messagePattern)) {
			final MessageFormat format = new MessageFormat(messagePattern);

			format.setLocale(locale);
			format.applyPattern(messagePattern);

			result = format.format(arguments.toArray(new Object[arguments.size()]));
		}

		return StringUtils.getNotEmpty(result, "");
	}
}
