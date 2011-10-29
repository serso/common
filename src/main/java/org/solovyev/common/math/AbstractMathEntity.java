package org.solovyev.common.math;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/29/11
 * Time: 12:49 PM
 */
public abstract class AbstractMathEntity implements MathEntity {

	@NotNull
	private String name;

	private boolean system;

	protected AbstractMathEntity() {
	}

	@NotNull
	@Override
	public String getName() {
		return this.name;
	}

	public boolean isSystem() {
		return system;
	}
}
