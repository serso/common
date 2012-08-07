/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.common;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: Apr 28, 2010
 * Time: 2:27:12 AM
 */
public class Selectable<T> {

	private Boolean selected = false;
	@NotNull
	final private T value;

	public Selectable(@NotNull T value) {
		this.value = value;
	}

	public Selectable(Boolean selected, @NotNull T value) {
		this.selected = selected;
		this.value = value;
	}

	@NotNull
	public T getValue() {
		return value;
	}

	@NotNull
	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(@NotNull Boolean selected) {
		this.selected = selected;
	}
}
