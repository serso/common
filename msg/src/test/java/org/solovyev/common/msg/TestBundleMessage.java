package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TestBundleMessage extends BundleMessage {

    private TestBundleMessage(@NotNull String messageCode, @NotNull MessageLevel messageLevel, @Nullable Object... parameters) {
        super("org/solovyev/common/msg/messages", Utf8Control.getInstance(), messageCode, messageLevel, parameters);
    }

    private TestBundleMessage(@NotNull String messageCode, @NotNull MessageLevel messageLevel, @NotNull List<?> parameters) {
        super("org/solovyev/common/msg/messages", Utf8Control.getInstance(), messageCode, messageLevel, parameters);
    }

    @NotNull
    public static TestBundleMessage newInstance(@NotNull String messageCode, @NotNull MessageLevel messageLevel, @NotNull List<?> parameters) {
        return new TestBundleMessage(messageCode, messageLevel, parameters);
    }

    @NotNull
    public static TestBundleMessage newInstance(@NotNull String messageCode, @NotNull MessageLevel messageLevel, @Nullable Object... parameters) {
        return new TestBundleMessage(messageCode, messageLevel, parameters);
    }
}
