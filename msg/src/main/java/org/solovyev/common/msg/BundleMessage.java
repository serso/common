package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

class BundleMessage extends AbstractMessage {

    @NotNull
    private final String bundleName;

    @Nullable
    private ResourceBundle.Control bundleControl;

    BundleMessage(@NotNull String bundleName,
                            @Nullable ResourceBundle.Control bundleControl,
                            @NotNull String messageCode,
                            @NotNull MessageLevel messageLevel,
                            @Nullable Object... parameters) {
        super(messageCode, messageLevel, parameters);
        this.bundleName = bundleName;
        this.bundleControl = bundleControl;
    }

    BundleMessage(@NotNull String bundleName,
                            @Nullable ResourceBundle.Control bundleControl,
                            @NotNull String messageCode,
                            @NotNull MessageLevel messageLevel,
                            @NotNull List<?> parameters) {
        super(messageCode, messageLevel, parameters);
        this.bundleName = bundleName;
        this.bundleControl = bundleControl;
    }

    @Nullable
    @Override
    protected final String getMessagePattern(@NotNull Locale locale) {
        final ResourceBundle bundle;

        if (bundleControl == null) {
            bundle = ResourceBundle.getBundle(bundleName, locale);
        } else {
            bundle = ResourceBundle.getBundle(bundleName, locale, bundleControl);
        }

        return bundle.getString(getMessageCode());
    }
}
