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

import junit.framework.Assert;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * User: serso
 * Date: 11/30/11
 * Time: 10:48 PM
 */
public class CompoundMessageTest {

    @Test
    public void testMessage() throws Exception {
        final List<Message> messages = new ArrayList<Message>();
        messages.add(new MessageImpl("msg_1", MessageType.info));
        messages.add(new MessageImpl("msg_2", MessageType.error));
        messages.add(new MessageImpl("msg_3", MessageType.warning));
        Message e = new MessageImpl("msg_4", MessageType.error);
        messages.add(e);

        CompoundMessage compoundMessage = CompoundMessage.newInstance(new MessageImpl("msg_16", MessageType.info), messages);
        Assert.assertTrue(compoundMessage.getMessagePattern(Locale.ENGLISH).contains("{3}"));
        Assert.assertFalse(compoundMessage.getMessagePattern(Locale.ENGLISH).contains("\\"));
        Assert.assertTrue(compoundMessage.getMessagePattern(Locale.ENGLISH).contains("{2}"));
        Assert.assertTrue(compoundMessage.getMessagePattern(Locale.ENGLISH).contains("{1}"));
        Assert.assertTrue(compoundMessage.getMessagePattern(Locale.ENGLISH).contains("{0}"));

        Assert.assertFalse(compoundMessage.getMessagePattern(Locale.ENGLISH).contains("{5}"));
        Assert.assertTrue(compoundMessage.getLocalizedMessage(Locale.ENGLISH).contains(e.getLocalizedMessage(Locale.ENGLISH)));
    }

    private class MessageImpl extends AbstractMessage {

        protected MessageImpl(@Nonnull String messageCode, @Nonnull MessageLevel messageLevel, @Nullable Object... parameters) {
            super(messageCode, messageLevel, parameters);
        }

        @Override
        protected String getMessagePattern(@Nonnull Locale locale) {
            return getMessageCode();
        }
    }

}
