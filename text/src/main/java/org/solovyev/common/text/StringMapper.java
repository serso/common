package org.solovyev.common.text;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 5/18/11
 * Time: 11:21 AM
 */
public class StringMapper implements Mapper<String> {

    @NotNull
    private static final Mapper<String> instance = new StringMapper();

    @NotNull
    public static Mapper<String> getInstance() {
        return instance;
    }

    /**
     * Use org.solovyev.common.text.StringMapper#getInstance() method instead
     */
    @Deprecated
    public StringMapper() {
    }

    @Override
	public String formatValue(@Nullable String value) {
		return value;
	}

	@Override
	public String parseValue(@Nullable String value) {
		return value;
	}
}
