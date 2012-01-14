package org.solovyev.common.text;

import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 5/18/11
 * Time: 11:21 AM
 */
public class StringMapper implements Mapper<String>{

	@Override
	public String formatValue(@Nullable String value) {
		return value;
	}

	@Override
	public String parseValue(@Nullable String value) {
		return value;
	}
}
