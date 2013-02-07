package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.text.HexString;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AesSecretKeyProvider implements SecretKeyProvider {

    @NotNull
    private static final SecretKeyProvider instance = new AesSecretKeyProvider();

    private AesSecretKeyProvider() {
    }

    @NotNull
    public static SecretKeyProvider newInstance() {
        return instance;
    }

    @NotNull
    @Override
    public SecretKey getSecretKey(@NotNull String password, @NotNull String salt) throws CiphererException {
        final String secretKey = password + salt;
        return new SecretKeySpec(HexString.fromString(secretKey).getBytes(), "AES");
    }
}
