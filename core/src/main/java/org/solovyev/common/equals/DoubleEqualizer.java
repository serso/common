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

package org.solovyev.common.equals;

import org.jetbrains.annotations.Nullable;
import org.solovyev.common.math.Maths;

/**
 * User: serso
 * Date: 1/28/11
 * Time: 11:41 AM
 */
public class DoubleEqualizer implements Equalizer<Double> {

	private final int precision;

	public DoubleEqualizer(int precision) {
		this.precision = precision;
	}

	@Override
	public boolean equals(@Nullable Double first, @Nullable Double second) {
		final EqualsUtils.Result result =  EqualsUtils.getEqualsResult(first, second);
		//noinspection ConstantConditions
		return result.areBothNulls() || (result.areBothNotNulls() && Maths.equals(first, second, precision));
	}
}
