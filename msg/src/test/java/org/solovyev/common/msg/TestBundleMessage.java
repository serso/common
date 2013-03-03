package org.solovyev.common.msg;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;

public class TestBundleMessage extends BundleMessage {

    private TestBundleMessage(@Nonnull String messageCode, @Nonnull MessageLevel messageLevel, @Nullable Object... parameters) {
        super("org/solovyev/common/msg/messages", Utf8Control.getInstance(), messageCode, messageLevel, parameters);
    }

    private TestBundleMessage(@Nonnull String messageCode, @Nonnull MessageLevel messageLevel, @Nonnull List<?> parameters) {
        super("org/solovyev/common/msg/messages", Utf8Control.getInstance(), messageCode, messageLevel, parameters);
    }

    @Nonnull
    public static TestBundleMessage newInstance(@Nonnull String messageCode, @Nonnull MessageLevel messageLevel, @Nonnull List<?> parameters) {
        return new TestBundleMessage(messageCode, messageLevel, parameters);
    }

    @Nonnull
    public static TestBundleMessage newInstance(@Nonnull String messageCode, @Nonnull MessageLevel messageLevel, @Nullable Object... parameters) {
        return new TestBundleMessage(messageCode, messageLevel, parameters);
    }
}
