package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MessageFactory {


    @NotNull
    Message newMessage(@NotNull String messageCode, @NotNull MessageLevel messageLevel, @Nullable Object... parameters);

    @NotNull
    Message newMessage(@NotNull String messageCode, @NotNull MessageLevel messageLevel, @NotNull List<?> parameters);
}
