package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.ResourceBundle;

class BundleMessageFactory implements MessageFactory {

    @NotNull
    private final String bundleName;

    @Nullable
    private ResourceBundle.Control bundleControl;

    BundleMessageFactory(@NotNull String bundleName, @Nullable ResourceBundle.Control bundleControl) {
        this.bundleName = bundleName;
        this.bundleControl = bundleControl;
    }

    @NotNull
    @Override
    public Message newMessage(@NotNull String messageCode, @NotNull MessageType messageType, @Nullable Object... parameters) {
        return new BundleMessage(bundleName, bundleControl, messageCode, messageType, parameters);
    }

    @NotNull
    @Override
    public Message newMessage(@NotNull String messageCode, @NotNull MessageType messageType, @NotNull List<?> parameters) {
        return new BundleMessage(bundleName, bundleControl, messageCode, messageType, parameters);
    }
}
