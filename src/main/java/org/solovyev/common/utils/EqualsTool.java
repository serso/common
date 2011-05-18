package org.solovyev.common.utils;

/**
 * User: serso
 * Date: 5/18/11
 * Time: 11:18 AM
 */

import org.jetbrains.annotations.Nullable;

public class EqualsTool {

	public static Result getEqualsResult(@Nullable Object o1, @Nullable Object o2) {
		return new Result<Object>(o1, o2, null);
	}

	public static <T> boolean areEqual (@Nullable T o1, @Nullable T o2) {
		return new Result<T>(o1, o2, null).areEqual();
	}

	public static <T> boolean areEqual (@Nullable T o1, @Nullable T o2, @Nullable Equalizer<T> equalizer ) {
		return new Result<T>(o1, o2, equalizer).areEqual();
	}

	public static class Result<T> {

		public static Integer BOTH_NULLS_CONST = 0;

		@Nullable
		private final Integer result;

		@Nullable
		private final T o1;

		@Nullable
		private final T o2;

		@Nullable
		private final Equalizer<T> equalizer;

		private Result(@Nullable T o1, @Nullable T o2, @Nullable Equalizer<T> equalizer) {
			this.equalizer = equalizer;
			if (o1 == null && o2 == null) {
				result = Result.BOTH_NULLS_CONST;
			} else if (o1 == null) {
				result = -1;
			} else if (o2 == null) {
				result = 1;
			} else {
				//both not nulls
				result = null;
			}
			this.o1 = o1;
			this.o2 = o2;
		}

		@Nullable
		public Integer getResult() {
			return result;
		}

		public boolean areBothNotNulls () {
			return result == null;
		}

		public boolean areBothNulls () {
			return result != null && result.equals(BOTH_NULLS_CONST);
		}

		public boolean areEqual () {
			//noinspection ConstantConditions
			return areBothNulls() || o1 == o2 || (areBothNotNulls() && (equalizer == null ? o1.equals(o2) : equalizer.equals(o1, o2)));
		}
	}
}
