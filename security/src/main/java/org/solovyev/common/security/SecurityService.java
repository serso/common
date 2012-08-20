package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:22 PM
 */
public interface SecurityService {

    @NotNull
    SaltGenerator getSaltGenerator();

    @NotNull
    SecretKeyProvider getSecretKeyProvider();

    @NotNull
    Cipherer getCipherer();
}
