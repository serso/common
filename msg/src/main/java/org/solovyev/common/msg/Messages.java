package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.collections.JCollections;

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

        if (JCollections.isEmpty(parameters)) {
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
