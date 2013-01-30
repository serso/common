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

package org.solovyev.common.math.calculators;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 11:32 AM
 */
public abstract class AbstractCalculator<T extends Number> implements Calculator<T> {

	private final Class<T> clazz;

	public AbstractCalculator(Class<T> clazz) {
		this.clazz = clazz;
	}

	@NotNull
	@Override
	public Class<T> getObjectClass() {
		return this.clazz;
	}
}
