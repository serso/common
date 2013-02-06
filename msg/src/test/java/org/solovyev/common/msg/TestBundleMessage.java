package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TestBundleMessage extends BundleMessage {

    private TestBundleMessage(@NotNull String messageCode, @NotNull MessageType messageType, @Nullable Object... parameters) {
        super("org/solovyev/common/msg/messages", Utf8Control.getInstance(), messageCode, messageType, parameters);
    }

    private TestBundleMessage(@NotNull String messageCode, @NotNull MessageType messageType, @NotNull List<?> parameters) {
        super("org/solovyev/common/msg/messages", Utf8Control.getInstance(), messageCode, messageType, parameters);
    }

    @NotNull
    public static TestBundleMessage newInstance(@NotNull String messageCode, @NotNull MessageType messageType, @NotNull List<?> parameters) {
        return new TestBundleMessage(messageCode, messageType, parameters);
    }

    @NotNull
    public static TestBundleMessage newInstance(@NotNull String messageCode, @NotNull MessageType messageType, @Nullable Object... parameters) {
        return new TestBundleMessage(messageCode, messageType, parameters);
    }
}
