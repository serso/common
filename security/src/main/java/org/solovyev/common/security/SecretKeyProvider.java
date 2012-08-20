package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;

import javax.crypto.SecretKey;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:08 PM
 */
public interface SecretKeyProvider {

    @NotNull
    SecretKey getSecretKey(@NotNull String password, @NotNull String salt) throws CiphererException;
}
