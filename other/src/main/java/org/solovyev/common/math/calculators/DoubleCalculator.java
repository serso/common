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
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.math.MathConstants;
import org.solovyev.common.equals.DoubleEqualizer;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 11:33 AM
 */
public class DoubleCalculator extends AbstractCalculator<Double> {

	private DoubleEqualizer equalizer = new DoubleEqualizer(MathConstants.precisionDigits);

	public DoubleCalculator() {
		super(Double.class);
	}

	@NotNull
	@Override
	public Double multiply(@NotNull Double l, @NotNull Double r) {
		return l * r;
	}

	@NotNull
	@Override
	public Double divide(@NotNull Double l, @NotNull Double r) {
		return l / r;
	}

	@NotNull
	@Override
	public Double summarize(@NotNull Double l, @NotNull Double r) {
		return l + r;
	}

	@NotNull
	@Override
	public Double subtract(@NotNull Double l, @NotNull Double r) {
		return l - r;
	}



	@NotNull
	@Override
	public Double getZero() {
		return 0d;
	}

	@Override
	public boolean equals(@Nullable Double first, @Nullable Double second) {
		return this.equalizer.equals(first, second);
	}
}
