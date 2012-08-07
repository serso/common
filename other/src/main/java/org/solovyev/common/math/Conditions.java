package org.solovyev.common.math;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 3:20 PM
 */
public class Conditions {

	@NotNull
	private Function entryConditions;

	@NotNull
	private Function startCondition0;

	@NotNull
	private Function startCondition1;

	public Conditions(@NotNull Function startCondition0, @NotNull Function startCondition1, @NotNull Function entryConditions) {
		this.startCondition0 = startCondition0;
		this.startCondition1 = startCondition1;
		this.entryConditions = entryConditions;
	}

	@NotNull
	public Function getEntryCondition() {
		return this.entryConditions;
	}

	@NotNull
	public Function getStartCondition0() {
		return this.startCondition0;
	}

	@NotNull
	public Function getStartCondition1() {
		return this.startCondition1;
	}
}
