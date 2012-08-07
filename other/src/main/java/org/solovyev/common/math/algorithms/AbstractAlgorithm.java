package org.solovyev.common.math.algorithms;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.math.Algorithm;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 4:49 PM
 */
public abstract class AbstractAlgorithm<INPUT, RESULT> implements Algorithm<INPUT, RESULT>{

	@NotNull
	protected INPUT input;

	@NotNull
	protected RESULT result;

	public AbstractAlgorithm() {
	}

	@Override
	public AbstractAlgorithm<INPUT, RESULT> init(@NotNull INPUT input) {
		this.input = input;

		return this;
	}

	@NotNull
	public final RESULT getResult() {
		return this.result;
	}
}
