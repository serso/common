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

import java.util.List;
import java.util.Locale;

/**
 * User: serso
 * Date: 11/30/11
 * Time: 10:45 PM
 */
public class CompoundMessage extends AbstractMessage {

	@NotNull
	private final Message compoundMessage;

	private CompoundMessage(@NotNull Message compoundMessage, @NotNull MessageLevel messageLevel, @NotNull List<?> parameters) {
		super(compoundMessage.getMessageCode(), messageLevel, parameters);
		this.compoundMessage = compoundMessage;
	}

	public static CompoundMessage newInstance(@NotNull Message compoundMessage, @NotNull List<? extends Message> messages) {
		MessageLevel messageType = MessageType.info;
		for (Message message : messages) {
			messageType = Messages.getMessageLevelWithHigherLevel(messageType, message.getMessageLevel());
		}
		return new CompoundMessage(compoundMessage, messageType, messages);
	}

	@Override
	protected String getMessagePattern(@NotNull Locale locale) {
		final StringBuilder result = new StringBuilder();

		result.append(compoundMessage.getLocalizedMessage(locale)).append("\n");

		for (int i = 0; i < getParameters().size(); i++) {
			if (i != 0) {
				result.append(",\n");
			}
			result.append("{").append(i).append("}");
		}

		return result.toString();
	}
}
