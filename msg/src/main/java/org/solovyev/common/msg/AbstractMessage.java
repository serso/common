/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.collections.CollectionsUtils;
import org.solovyev.common.equals.EqualsTool;
import org.solovyev.common.equals.ListEqualizer;
import org.solovyev.common.text.StringUtils;
import org.solovyev.common.HashCodeBuilder;

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
public abstract class AbstractMessage implements Message {

	@NotNull
	private final String messageCode;

	@NotNull
	private final List<?> parameters;

	@NotNull
	private final MessageType messageType;

	protected AbstractMessage(@NotNull String messageCode, @NotNull MessageType messageType, @Nullable Object... parameters) {
		this(messageCode, messageType, CollectionsUtils.asList(parameters));
	}

	protected AbstractMessage(@NotNull String messageCode, @NotNull MessageType messageType, @NotNull List<?> parameters) {
		this.messageCode = messageCode;
		this.parameters = new ArrayList<Object>(parameters);
		this.messageType = messageType;
	}

	@Override
	@NotNull
	public String getMessageCode() {
		return this.messageCode;
	}

	@NotNull
	@Override
	public List<Object> getParameters() {
		return Collections.unmodifiableList(this.parameters);
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

		if (!EqualsTool.areEqual(parameters, abstractMessage.parameters, new ListEqualizer(true, null))) return false;
		if (!messageCode.equals(abstractMessage.messageCode)) return false;
		if (messageType != abstractMessage.messageType) return false;

		return true;
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder hcb = new HashCodeBuilder();

		hcb.append(messageCode);
		hcb.append(messageType);
		hcb.append(parameters);

		return hcb.toHashCode();
	}

	/**
	 * Method converts message to string setting passed message parameters and translating some of them.
	 *
	 * @param locale language to which parameters should be translated (if possible)
	 * @return message as string with properly translated and set parameters
	 */
	@NotNull
	public String getLocalizedMessage(@NotNull Locale locale) {
		String result = null;

		final String messagePattern = getMessagePattern(locale);
		if (!StringUtils.isEmpty(messagePattern)) {
			if (CollectionsUtils.isEmpty(parameters)) {
				result = messagePattern;
			} else {
				final MessageFormat format = new MessageFormat(messagePattern);

				format.setLocale(locale);
				format.applyPattern(messagePattern);

				result = format.format(prepareParameters(parameters, locale));
			}
		}

		return StringUtils.getNotEmpty(result, messageType.getStringValue() + ": message code = " + messageCode);
	}

	@NotNull
	private static Object[] prepareParameters(@NotNull List<?> parameters, @NotNull Locale locale) {
		final Object[] result = new Object[parameters.size()];

		for (int i = 0; i<parameters.size(); i++){
			result[i] = substituteParameter(parameters.get(i), locale);
		}

		return result;
	}

	@Nullable
	private static Object substituteParameter(@Nullable Object object, @NotNull Locale locale) {
		if (object instanceof Message) {
			return ((Message) object).getLocalizedMessage(locale);
		} else {
			return object;
		}
	}

	@NotNull
	@Override
	public String getLocalizedMessage() {
		return this.getLocalizedMessage(Locale.getDefault());
	}

	@Nullable
	protected abstract String getMessagePattern(@NotNull Locale locale);
}
