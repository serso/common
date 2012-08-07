package org.solovyev.common.math;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 15.04.2009
 * Time: 11:42:06
 */
public interface Algorithm<IN, OUT> {

	Algorithm<IN, OUT> init(@NotNull IN in);

    public OUT doAlgorithm();

	@NotNull
	public OUT getResult();
}
