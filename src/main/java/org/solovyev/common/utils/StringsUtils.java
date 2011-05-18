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
public class StringsUtils {

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
		return s != null && s.length() > 0;
	}

	public static String[] toString(@NotNull Enum... enums) {
		String[] result = new String[enums.length];
		LoopData ld = new LoopData(enums);
		for (Enum anEnum : enums) {
			result[(int)ld.next()] = anEnum.name();
		}
		return result;
	}
}
