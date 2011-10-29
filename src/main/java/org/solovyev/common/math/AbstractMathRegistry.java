/*
 * Copyright (c) 2009-2011. Created by serso aka se.solovyev.
 * For more information, please, contact se.solovyev@gmail.com
 * or visit http://se.solovyev.org
 */

package org.solovyev.common.math;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.definitions.IBuilder;
import org.solovyev.common.utils.CollectionsUtils;
import org.solovyev.common.utils.Finder;

import java.util.*;

/**
 * User: serso
 * Date: 9/29/11
 * Time: 4:57 PM
 */
public abstract class AbstractMathRegistry<T extends MathEntity> implements MathRegistry<T> {

	@NotNull
	private final List<T> entities = new ArrayList<T>();

	@NotNull
	private final List<T> systemEntities = new ArrayList<T>();

	protected AbstractMathRegistry() {
	}

	@NotNull
	@Override
	public List<T> getEntities() {
		return Collections.unmodifiableList(entities);
	}

	@NotNull
	@Override
	public List<T> getSystemEntities() {
		return Collections.unmodifiableList(systemEntities);
	}

	protected void add(@NotNull T entity) {
		if (entity.isSystem()) {
			if (contains(entity.getName(), this.systemEntities)) {
				throw new IllegalArgumentException("Trying to add two system entities with same name: " + entity.getName());
			}
			this.systemEntities.add(entity);
		}

		if (!contains(entity.getName(), this.entities)) {
			this.entities.add(entity);
		}
	}

	@Override
	public T add(@Nullable String name, @NotNull IBuilder<T> IBuilder) {
		final T entity = IBuilder.create();

		T varFromRegister = get(name == null ? entity.getName() : name);
		if (varFromRegister == null) {
			varFromRegister = entity;
			entities.add(entity);
		} else {
			varFromRegister.copy(entity);
		}

		return varFromRegister;
	}

	@Override
	public void remove(@NotNull T entity) {
		// todo serso: remove by name
		while (contains(entity.getName(), this.entities)) {
			this.entities.remove(entity);
		}
	}

	@Override
	@NotNull
	public List<String> getNames() {
		final List<String> result = new ArrayList<String>();

		for (T entity : entities) {
			result.add(entity.getName());
		}

		Collections.sort(result, MathEntity.mathEntityComparator);

		return result;
	}

	@Override
	@Nullable
	public T get(@NotNull final String name) {
		return CollectionsUtils.get(entities, new Finder<T>() {
			@Override
			public boolean isFound(@Nullable T entity) {
				return entity != null && name.equals(entity.getName());
			}
		});
	}

	@Override
	public boolean contains(@NotNull final String name) {
		return contains(name, this.entities);
	}

	private boolean contains(final String name, @NotNull List<T> entities) {
		return CollectionsUtils.get(entities, new Finder<T>() {
			@Override
			public boolean isFound(@Nullable T entity) {
				return entity != null && name.equals(entity.getName());
			}
		}) != null;
	}

}
