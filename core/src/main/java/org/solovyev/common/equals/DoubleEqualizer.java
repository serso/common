package org.solovyev.common.equals;

import org.jetbrains.annotations.Nullable;
import org.solovyev.common.equals.Equalizer;
import org.solovyev.common.equals.EqualsUtils;
import org.solovyev.common.math.MathUtils;

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
		return result.areBothNulls() || (result.areBothNotNulls() && MathUtils.equals(first, second, precision));
	}
}
