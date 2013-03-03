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
import org.solovyev.common.HashCodeBuilder;
import org.solovyev.common.Objects;
import org.solovyev.common.collections.Collections;
import org.solovyev.common.equals.ListEqualizer;
import org.solovyev.common.text.Strings;

import java.util.ArrayList;
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
	private final List<Object> parameters;

	@NotNull
	private final MessageLevel messageLevel;

	protected AbstractMessage(@NotNull String messageCode, @NotNull MessageLevel messageType, @Nullable Object... parameters) {
		this(messageCode, messageType, Collections.asList(parameters));
	}

	protected AbstractMessage(@NotNull String messageCode, @NotNull MessageLevel messageType, @NotNull List<?> parameters) {
		this.messageCode = messageCode;
		this.parameters = new ArrayList<Object>(parameters);
		this.messageLevel = messageType;
	}

	@Override
	@NotNull
	public String getMessageCode() {
		return this.messageCode;
	}

	@NotNull
	@Override
	public List<Object> getParameters() {
		return java.util.Collections.unmodifiableList(this.parameters);
	}

	@NotNull
	@Override
	public MessageLevel getMessageLevel() {
		return this.messageLevel;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final AbstractMessage that = (AbstractMessage) o;

        if (!Objects.areEqual(parameters, that.parameters, ListEqualizer.newWithNaturalEquals(true))) return false;
		if (!messageCode.equals(that.messageCode)) return false;
		if (!messageLevel.equals(that.messageLevel)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder hcb = HashCodeBuilder.newInstance();

		hcb.append(messageCode);
		hcb.append(messageLevel);
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
		if (!Strings.isEmpty(messagePattern)) {
            result = Messages.prepareMessage(locale, messagePattern, parameters);
        }

		return Strings.getNotEmpty(result, messageLevel.getName() + ": message code = " + messageCode);
	}

    @NotNull
	@Override
	public String getLocalizedMessage() {
		return this.getLocalizedMessage(Locale.getDefault());
	}

	@Nullable
	protected abstract String getMessagePattern(@NotNull Locale locale);
}
