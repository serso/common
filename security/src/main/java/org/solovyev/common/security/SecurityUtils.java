package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:14 PM
 */
public final class SecurityUtils {

    private SecurityUtils() {
        throw new AssertionError();
    }

    public static byte[] generateRandomBytes(@NotNull String randomAlgorithm, int length) throws NoSuchAlgorithmException, NoSuchProviderException {
        final Random random = SecureRandom.getInstance(randomAlgorithm);
        byte[] result = new byte[length];
        random.nextBytes(result);
        return result;
    }
}
