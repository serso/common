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

import javax.annotation.Nonnull;

import java.util.List;
import java.util.Locale;

/**
 * User: serso
 * Date: 11/30/11
 * Time: 10:45 PM
 */
public class CompoundMessage extends AbstractMessage {

    @Nonnull
    private final Message compoundMessage;

    private CompoundMessage(@Nonnull Message compoundMessage, @Nonnull MessageLevel messageLevel, @Nonnull List<?> parameters) {
        super(compoundMessage.getMessageCode(), messageLevel, parameters);
        this.compoundMessage = compoundMessage;
    }

    public static CompoundMessage newInstance(@Nonnull Message compoundMessage, @Nonnull List<? extends Message> messages) {
        MessageLevel messageLevel = MessageType.info;
        for (Message message : messages) {
            messageLevel = Messages.getMessageLevelWithHigherLevel(messageLevel, message.getMessageLevel());
        }
        return new CompoundMessage(compoundMessage, messageLevel, messages);
    }

    @Override
    protected String getMessagePattern(@Nonnull Locale locale) {
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
