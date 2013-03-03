package org.solovyev.common.msg;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

class BundleMessage extends AbstractMessage {

    @Nonnull
    private final String bundleName;

    @Nullable
    private ResourceBundle.Control bundleControl;

    BundleMessage(@Nonnull String bundleName,
                            @Nullable ResourceBundle.Control bundleControl,
                            @Nonnull String messageCode,
                            @Nonnull MessageLevel messageLevel,
                            @Nullable Object... parameters) {
        super(messageCode, messageLevel, parameters);
        this.bundleName = bundleName;
        this.bundleControl = bundleControl;
    }

    BundleMessage(@Nonnull String bundleName,
                            @Nullable ResourceBundle.Control bundleControl,
                            @Nonnull String messageCode,
                            @Nonnull MessageLevel messageLevel,
                            @Nonnull List<?> parameters) {
        super(messageCode, messageLevel, parameters);
        this.bundleName = bundleName;
        this.bundleControl = bundleControl;
    }

    @Nullable
    @Override
    protected final String getMessagePattern(@Nonnull Locale locale) {
        final ResourceBundle bundle;

        if (bundleControl == null) {
            bundle = ResourceBundle.getBundle(bundleName, locale);
        } else {
            bundle = ResourceBundle.getBundle(bundleName, locale, bundleControl);
        }

        return bundle.getString(getMessageCode());
    }
}
