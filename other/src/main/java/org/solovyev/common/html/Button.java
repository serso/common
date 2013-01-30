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

package org.solovyev.common.html;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: Apr 29, 2010
 * Time: 11:13:24 PM
 */
public class Button implements Cloneable{

	@NotNull
	private final String value;

	@NotNull
	private final String action;

	public Button(@NotNull String value, @NotNull String action) {
		this.value = value;
		this.action = action;
	}

	@NotNull
	public String getValue() {
		return value;
	}

	@NotNull
	public String getAction() {
		return action;
	}

	@Override
	public Button clone() {
		Button clone = null;
		try {
			clone = (Button)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(e);
		}
		return clone;
	}
}
