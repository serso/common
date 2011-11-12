package org.solovyev.common.math;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 10/29/11
 * Time: 12:39 PM
 */
public interface MathEntity {

	@NotNull
	String getName();

	boolean isSystem();

	@NotNull
	Integer getId();

	boolean isIdDefined();

	void setId(@NotNull Integer id);

	void copy(@NotNull MathEntity that);

	public static class Finder<T extends MathEntity> implements org.solovyev.common.utils.Finder<T> {

		@NotNull
		private final String name;

		public Finder(@NotNull String name) {
			this.name = name;
		}

		@Override
		public boolean isFound(@Nullable T entity) {
			return entity != null && name.equals(entity.getName());
		}
	}
}
