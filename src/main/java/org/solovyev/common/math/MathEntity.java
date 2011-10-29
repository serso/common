package org.solovyev.common.math;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

/**
 * User: serso
 * Date: 10/29/11
 * Time: 12:39 PM
 */
public interface MathEntity {

	public static final MathEntityNameComparator MATH_ENTITY_NAME_COMPARATOR = new MathEntityNameComparator();

	@NotNull
	String getName();

	@NotNull
	String getDescription();

	boolean isSystem();

	void copy(@NotNull MathEntity that);

	static class MathEntityNameComparator implements Comparator<String> {

		private MathEntityNameComparator() {
		}

		@Override
		public int compare(String s, String s1) {
			return s1.length() - s.length();
		}
	}

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
