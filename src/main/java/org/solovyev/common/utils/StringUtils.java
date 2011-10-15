package org.solovyev.common.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: 27.04.2009
 * Time: 10:23:29
 */
public class StringUtils {

	public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];

	public static String[] split(String source, String subString) {
		String[] params = source.split(subString);
		List<String> result = new ArrayList<String>();

		for (String s : params) {
			if (!s.isEmpty() && !s.equals(subString)) {
				result.add(s);
			}
		}

		return result.toArray(new String[result.size()]);
	}

	public static String convertStream(InputStream in) throws IOException {
		int i;
		StringBuilder sb = new StringBuilder();
		while ((i = in.read()) != -1) {
			sb.append((char) i);
		}
		return sb.toString();
	}

	public static boolean notEmpty(@Nullable String s) {
		return !isEmpty(s);
	}

	public static boolean isEmpty(@Nullable CharSequence s) {
		return s == null || s.length() == 0;
	}

	@NotNull
	public static String getNotEmpty(@Nullable CharSequence s, @NotNull String defaultValue) {
		return isEmpty(s) ? defaultValue : s.toString();
	}

	public static String[] toString(@NotNull Enum... enums) {
		String[] result = new String[enums.length];
		LoopData ld = new LoopData(enums);
		for (Enum anEnum : enums) {
			result[(int) ld.next()] = anEnum.name();
		}
		return result;
	}

	@NotNull
	public static Character[] toObject(char[] array) {
		if (array == null || array.length == 0) {
			return EMPTY_CHARACTER_OBJECT_ARRAY;
		}

		final Character[] result = new Character[array.length];

		for (int i = 0; i < array.length; i++) {
			result[i] = new Character(array[i]);
		}
		return result;
	}
}
