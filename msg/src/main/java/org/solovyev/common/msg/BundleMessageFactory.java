package org.solovyev.common.msg;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;
import java.util.ResourceBundle;

class BundleMessageFactory implements MessageFactory {

    @Nonnull
    private final String bundleName;

    @Nullable
    private ResourceBundle.Control bundleControl;

    BundleMessageFactory(@Nonnull String bundleName, @Nullable ResourceBundle.Control bundleControl) {
        this.bundleName = bundleName;
        this.bundleControl = bundleControl;
    }

    @Nonnull
    @Override
    public Message newMessage(@Nonnull String messageCode, @Nonnull MessageLevel messageLevel, @Nullable Object... parameters) {
        return new BundleMessage(bundleName, bundleControl, messageCode, messageLevel, parameters);
    }

    @Nonnull
    @Override
    public Message newMessage(@Nonnull String messageCode, @Nonnull MessageLevel messageLevel, @Nonnull List<?> parameters) {
        return new BundleMessage(bundleName, bundleControl, messageCode, messageLevel, parameters);
    }
}
