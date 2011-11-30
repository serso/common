package org.solovyev.common.msg;

import junit.framework.Assert;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * User: serso
 * Date: 11/30/11
 * Time: 10:48 PM
 */
public class CompositeMessageTest {

	@Test
	public void testMessage() throws Exception {
		final List<Message> messages = new ArrayList<Message>();
		messages.add(new MessageImpl("msg_1", MessageType.info));
		messages.add(new MessageImpl("msg_2", MessageType.error));
		messages.add(new MessageImpl("msg_3", MessageType.warning));
		Message e = new MessageImpl("msg_4", MessageType.error);
		messages.add(e);

		CompositeMessage compositeMessage = CompositeMessage.newInstance(new MessageImpl("msg_16", MessageType.info), messages);
		Assert.assertTrue(compositeMessage.getMessagePattern(Locale.ENGLISH).contains("{3}"));
		Assert.assertFalse(compositeMessage.getMessagePattern(Locale.ENGLISH).contains("\\"));
		Assert.assertTrue(compositeMessage.getMessagePattern(Locale.ENGLISH).contains("{2}"));
		Assert.assertTrue(compositeMessage.getMessagePattern(Locale.ENGLISH).contains("{1}"));
		Assert.assertTrue(compositeMessage.getMessagePattern(Locale.ENGLISH).contains("{0}"));

		Assert.assertFalse(compositeMessage.getMessagePattern(Locale.ENGLISH).contains("{5}"));
		Assert.assertTrue(compositeMessage.getLocalizedMessage(Locale.ENGLISH).contains(e.getLocalizedMessage(Locale.ENGLISH)));
	}

	private class  MessageImpl extends AbstractMessage {

		protected MessageImpl(@NotNull String messageCode, @NotNull MessageType messageType, @Nullable Object... parameters) {
			super(messageCode, messageType, parameters);
		}

		@Override
		protected String getMessagePattern(@NotNull Locale locale) {
			return getMessageCode();
		}
	}

}
