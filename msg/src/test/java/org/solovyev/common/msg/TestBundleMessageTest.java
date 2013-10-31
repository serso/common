package org.solovyev.common.msg;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class TestBundleMessageTest {

	@Test
	public void testGetMessage() throws Exception {
		final TestBundleMessage m1 = TestBundleMessage.newInstance("test.message.01", MessageType.info);
		final TestBundleMessage m2 = TestBundleMessage.newInstance("test.message.02", MessageType.info);
		final TestBundleMessage m3 = TestBundleMessage.newInstance("test.message.03", MessageType.info, "1", "2", "3");

		Assert.assertEquals("test.message.01", m1.getLocalizedMessage(Locale.ENGLISH));
		Assert.assertEquals("test.message.02", m2.getLocalizedMessage(Locale.ENGLISH));
		Assert.assertEquals("test.message.03 3 1 2", m3.getLocalizedMessage(Locale.ENGLISH));

		final Locale ruLocale = new Locale("ru", "RU");
		Assert.assertEquals("тестовое.сообщение.01", m1.getLocalizedMessage(ruLocale));
		Assert.assertEquals("тестовое.сообщение.02", m2.getLocalizedMessage(ruLocale));
		Assert.assertEquals("тестовое.сообщение.03 3 1 2", m3.getLocalizedMessage(ruLocale));
	}
}
