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
import org.solovyev.common.collections.Collections;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;

public final class Messages {

    private Messages() {
        throw new AssertionError();
    }

    @NotNull
    public static MessageRegistry newOrderedMessageRegistry() {
         return new ListMessageRegistry();
    }

    @NotNull
    public static MessageRegistry toSynchronizedMessageRegistry(@NotNull MessageRegistry messageRegistry) {
        return SynchronizedMessageRegistry.wrap(messageRegistry);
    }

    @NotNull
    public static MessageRegistry toSynchronizedMessageRegistry(@NotNull MessageRegistry messageRegistry, @NotNull Object mutex) {
        return SynchronizedMessageRegistry.wrap(messageRegistry, mutex);
    }

    /**
     *
     * @param locale locale for which default formatting will be applied
     * @param messagePattern message pattern which will be used for {@link java.text.MessageFormat}
     * @param parameters message parameters which will be used for {@link java.text.MessageFormat}
     *
     * @return formatted message string according to default locale formatting, nested messages are processed properly
     * (for each message from parameter method {@link Message#getLocalizedMessage(java.util.Locale)} is called)
     */
    @NotNull
    public static String prepareMessage(@NotNull Locale locale, @NotNull String messagePattern, @NotNull List<?> parameters) {
        String result;

        if (Collections.isEmpty(parameters)) {
            result = messagePattern;
        } else {
            final MessageFormat format = new MessageFormat(messagePattern);

            format.setLocale(locale);
            format.applyPattern(messagePattern);

            result = format.format(prepareParameters(parameters, locale));
        }

        return result;
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
}
