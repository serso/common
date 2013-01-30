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
public enum MessageType {

	error(1000, "ERROR"),

	warning(500, "WARNING"),

	info(100, "INFO");

	private final int errorLevel;

	@NotNull
	private final String stringValue;

	MessageType(int errorLevel, @NotNull String stringValue) {
		this.errorLevel = errorLevel;
		this.stringValue = stringValue;
	}

	@NotNull
	public String getStringValue() {
		return stringValue;
	}

	@NotNull
	public static MessageType getMessageTypeWithHigherLevel( @NotNull MessageType l, @NotNull MessageType r ) {
		MessageType result;

		if ( l.errorLevel > r.errorLevel  ) {
			result = l;
		} else {
			result = r;
		}

		return result;
	}

	@NotNull
	public static MessageType getLowestMessageType() {
		return info;
	}
}
