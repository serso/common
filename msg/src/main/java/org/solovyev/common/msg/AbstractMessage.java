/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ---------------------------------------------------------------------
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.collections.JCollections;
import org.solovyev.common.JObjects;
import org.solovyev.common.equals.ListEqualizer;
import org.solovyev.common.text.JStrings;
import org.solovyev.common.HashCodeBuilder;

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
		this(messageCode, messageType, JCollections.asList(parameters));
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

		if (!JObjects.areEqual(parameters, abstractMessage.parameters, new ListEqualizer(true, null))) return false;
		if (!messageCode.equals(abstractMessage.messageCode)) return false;
		if (messageType != abstractMessage.messageType) return false;

		return true;
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder hcb = HashCodeBuilder.newInstance();

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
		if (!JStrings.isEmpty(messagePattern)) {
            result = Messages.prepareMessage(locale, messagePattern, parameters);
        }

		return JStrings.getNotEmpty(result, messageType.getStringValue() + ": message code = " + messageCode);
	}

    @NotNull
	@Override
	public String getLocalizedMessage() {
		return this.getLocalizedMessage(Locale.getDefault());
	}

	@Nullable
	protected abstract String getMessagePattern(@NotNull Locale locale);
}
