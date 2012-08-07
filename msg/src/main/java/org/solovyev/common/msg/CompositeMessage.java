package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

/**
 * User: serso
 * Date: 11/30/11
 * Time: 10:45 PM
 */
public class CompositeMessage extends AbstractMessage {

	@NotNull
	private final Message compositeMessage;

	private CompositeMessage(@NotNull Message compositeMessage, @NotNull MessageType messageType, @NotNull List<?> parameters) {
		super(compositeMessage.getMessageCode(), messageType, parameters);
		this.compositeMessage = compositeMessage;
	}

	public static CompositeMessage newInstance(@NotNull Message compositeMessage, @NotNull List<? extends Message> messages) {
		MessageType messageType = MessageType.info;
		for (Message message : messages) {
			messageType = MessageType.getMessageTypeWithHigherLevel(messageType, message.getMessageType());
		}
		return new CompositeMessage(compositeMessage, messageType, messages);
	}

	@Override
	protected String getMessagePattern(@NotNull Locale locale) {
		final StringBuilder result = new StringBuilder();

		result.append(compositeMessage.getLocalizedMessage(locale)).append("\n");

		for (int i = 0; i < getParameters().size(); i++) {
			if (i != 0) {
				result.append(",\n");
			}
			result.append("{").append(i).append("}");
		}

		return result.toString();
	}
}
