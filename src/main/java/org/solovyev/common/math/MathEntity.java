package org.solovyev.common.math;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * User: serso
 * Date: 10/29/11
 * Time: 12:39 PM
 */
public interface MathEntity {

	public static final MathEntityComparator mathEntityComparator = new MathEntityComparator();

	@NotNull
	String getName();

	boolean isSystem();

	void copy(@NotNull MathEntity that);

	static class MathEntityComparator implements Comparator<String> {

		private MathEntityComparator() {
		}

		@Override
		public int compare(String s, String s1) {
			return s1.length() - s.length();
		}
	}
}
