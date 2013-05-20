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
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org/java/jcl
 */

package org.solovyev.common.listeners;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * User: serso
 * Date: 3/10/13
 * Time: 2:22 PM
 */
public abstract class AbstractTypedJEvent<D, T> implements JEvent {

	@Nonnull
	private final D eventObject;

	@Nonnull
	private final T type;

	@Nullable
	private final Object data;

	protected AbstractTypedJEvent(@Nonnull D eventObject, @Nonnull T type, @Nullable Object data) {
		this.eventObject = eventObject;
		this.type = type;
		this.data = data;
	}

	/**
	 * Implementations of this class might want to add own public method with good naming (like, getUser() or getAccount())
	 *
	 * @return event object
	 */
	@Nonnull
	protected final D getEventObject() {
		return eventObject;
	}

	@Nonnull
	public final T getType() {
		return type;
	}

	@Nullable
	public final Object getData() {
		return data;
	}

	public boolean isOfType(@Nonnull T... types) {
		for (T type : types) {
			if (this.type.equals(type)) {
				return true;
			}
		}

		return false;
	}
}
