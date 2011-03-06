package org.solovyev.common.math;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 15.04.2009
 * Time: 11:42:06
 */
public interface Algorithm<INPUT, RESULT> {

	Algorithm<INPUT, RESULT> init(@NotNull INPUT input);

    public RESULT doAlgorithm();

	@NotNull
	public RESULT getResult();
}
