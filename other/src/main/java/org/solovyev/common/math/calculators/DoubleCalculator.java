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
