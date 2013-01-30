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

import java.util.HashMap;
import java.util.Map;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 11:35 AM
 */
public class CalculatorFactory {

	private final Map<String, Calculator<?>> cache = new HashMap<String, Calculator<?>>();

	private static CalculatorFactory defaultInstance;

	public static CalculatorFactory getDefaultInstance () {

		if ( defaultInstance == null ) {
			defaultInstance = createInstance();
		}

		return defaultInstance;
	}

	private static synchronized CalculatorFactory createInstance() {
		CalculatorFactory result = defaultInstance;
		if ( defaultInstance == null ) {
			result = new CalculatorFactory();

			result.registerCalculator(new DoubleCalculator());
		}
		return result;
	}

	public <T extends Number> void registerCalculator(@NotNull Calculator<T> calculator) {
		cache.put(calculator.getObjectClass().getName(), calculator);
	}

	@NotNull
	public <T> Calculator<T> getCalculator (@NotNull Class<T> clazz) {
		//noinspection unchecked
		final Calculator<T> result = (Calculator<T>)cache.get(clazz.getName());

		if ( result == null ) {
			throw new IllegalArgumentException("Class " + clazz.getName() + " has not registered calculator!" );
		}

		return result;
	}

}
