/*
 * Copyright (c) 2009-2011. Created by serso aka se.solovyev.
 * For more information, please, contact se.solovyev@gmail.com
 * or visit http://se.solovyev.org
 */

package org.solovyev.common.math;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.definitions.IBuilder;

import java.util.List;

/**
 * User: serso
 * Date: 10/6/11
 * Time: 9:31 PM
 */
public interface MathRegistry<T extends MathEntity> {

	@NotNull
	List<T> getEntities();

	@NotNull
	List<T> getSystemEntities();

	T add(@NotNull IBuilder<? extends T> IBuilder);

	void remove(@NotNull T var);

	@NotNull
	List<String> getNames();

	boolean contains(@NotNull final String name);

	@Nullable
	T get(@NotNull String name);

	@Nullable
	T getById(@NotNull Integer id);
}
