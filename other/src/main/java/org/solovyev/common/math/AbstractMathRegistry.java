/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ---------------------------------------------------------------------
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common.math;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.JPredicate;
import org.solovyev.common.collections.JCollections;
import org.solovyev.common.collections.SortedList;
import org.solovyev.common.JBuilder;

import java.util.*;

/**
 * User: serso
 * Date: 9/29/11
 * Time: 4:57 PM
 */
public abstract class AbstractMathRegistry<T extends MathEntity> implements MathRegistry<T> {

	private static final MathEntityComparator<MathEntity> MATH_ENTITY_COMPARATOR = new MathEntityComparator<MathEntity>();

	static class MathEntityComparator<T extends MathEntity> implements Comparator<T> {

		MathEntityComparator() {
		}

		@Override
		public int compare(T l, T r) {
			int result = r.getName().length() - l.getName().length();
			if (result == 0) {
				result = l.getName().compareTo(r.getName());
			}
			return result;
		}
	}

	@NotNull
	private static Integer counter = 0;

	@NotNull
	protected final List<T> entities = SortedList.newInstance(new ArrayList<T>(30), MATH_ENTITY_COMPARATOR);

	@NotNull
	protected final List<T> systemEntities = SortedList.newInstance(new ArrayList<T>(30), MATH_ENTITY_COMPARATOR);

	protected AbstractMathRegistry() {
	}

	@NotNull
	@Override
	public List<T> getEntities() {
		return Collections.unmodifiableList(new ArrayList<T>(entities));
	}

	@NotNull
	@Override
	public List<T> getSystemEntities() {
		return Collections.unmodifiableList(new ArrayList<T>(systemEntities));
	}

	protected void add(@NotNull T entity) {
		if (entity.isSystem()) {
			if (contains(entity.getName(), this.systemEntities)) {
				throw new IllegalArgumentException("Trying to add two system entities with same name: " + entity.getName());
			}

			this.systemEntities.add(entity);
		}

		if (!contains(entity.getName(), this.entities)) {
			addEntity(entity, this.entities);
		}
	}

	private void addEntity(@NotNull T entity, @NotNull List<T> list) {
		entity.setId(count());
		list.add(entity);
	}

	@Override
	public T add(@NotNull JBuilder<? extends T> JBuilder) {
		final T entity = JBuilder.create();

		T varFromRegister;

		if (entity.isIdDefined()) {
			varFromRegister = getById(entity.getId());
		} else {
			varFromRegister = get(entity.getName());
		}

		if (varFromRegister == null) {
			varFromRegister = entity;

			addEntity(entity, this.entities);
			if (entity.isSystem()) {
				this.systemEntities.add(entity);
			}

		} else {
			varFromRegister.copy(entity);
			Collections.sort(this.entities, MATH_ENTITY_COMPARATOR);
			Collections.sort(this.systemEntities, MATH_ENTITY_COMPARATOR);
		}

		return varFromRegister;
	}

	@Override
	public void remove(@NotNull T entity) {
		if (!entity.isSystem()) {
			JCollections.removeFirst(this.entities, new MathEntity.Finder<T>(entity.getName()));
		}
	}

	@Override
	@NotNull
	public List<String> getNames() {
		final List<String> result = new ArrayList<String>();

		for (T entity : entities) {
			result.add(entity.getName());
		}

		return result;
	}

	@Override
	@Nullable
	public T get(@NotNull final String name) {
		return JCollections.find(entities, new MathEntity.Finder<T>(name));
	}

	@Override
	public T getById(@NotNull final Integer id) {
		return JCollections.find(entities, new JPredicate<T>() {
            @Override
            public boolean apply(@Nullable T t) {
                return t != null && t.getId().equals(id);
            }
        });
	}

	@Override
	public boolean contains(@NotNull final String name) {
		return contains(name, this.entities);
	}

	private boolean contains(final String name, @NotNull Collection<T> entities) {
		return JCollections.find(entities, new MathEntity.Finder<T>(name)) != null;
	}

	@NotNull
	private static synchronized Integer count() {
		final Integer result = counter;
		counter++;
		return result;
	}
}
