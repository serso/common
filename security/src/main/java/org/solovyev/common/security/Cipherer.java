package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;

import javax.crypto.SecretKey;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 7:42 PM
 */
public interface Cipherer {

    @NotNull
    String encrypt(@NotNull SecretKey secret,
                   @NotNull String plainText) throws CiphererException;

    @NotNull
    String encrypt(@NotNull SecretKey secret,
                   @NotNull String plainText,
                   @NotNull String ivHex) throws CiphererException;

    @NotNull
    String decrypt(@NotNull SecretKey secret, @NotNull String encryptedText) throws CiphererException;

    @NotNull
    String getIvHexFromEncrypted(@NotNull String encryptedText) throws CiphererException;
}
