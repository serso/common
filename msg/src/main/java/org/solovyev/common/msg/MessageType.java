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

/**
 * User: serso
 * Date: Mar 29, 2010
 * Time: 12:43:22 AM
 */
public enum MessageType implements MessageLevel {

    error(ERROR_LEVEL, "ERROR"),

    warning(WARNING_LEVEL, "WARNING"),

    info(INFO_LEVEL, "INFO");

    private final int messageLevel;

    @NotNull
    private final String stringValue;

    MessageType(int messageLevel, @NotNull String stringValue) {
        this.messageLevel = messageLevel;
        this.stringValue = stringValue;
    }

    @NotNull
    public String getStringValue() {
        return stringValue;
    }

    @NotNull
    public static MessageType getLowestMessageType() {
        return info;
    }

    @Override
    public int getMessageLevel() {
        return messageLevel;
    }

    @NotNull
    @Override
    public String getName() {
        return stringValue;
    }
}
