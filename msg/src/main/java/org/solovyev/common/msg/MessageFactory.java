package org.solovyev.common.msg;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;

public interface MessageFactory {


    @Nonnull
    Message newMessage(@Nonnull String messageCode, @Nonnull MessageLevel messageLevel, @Nullable Object... parameters);

    @Nonnull
    Message newMessage(@Nonnull String messageCode, @Nonnull MessageLevel messageLevel, @Nonnull List<?> parameters);
}
