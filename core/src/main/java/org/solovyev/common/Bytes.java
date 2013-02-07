package org.solovyev.common;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:02 PM
 */
public final class Bytes {

    private final static String HEX = "0123456789ABCDEF";

    private Bytes() {
        throw new AssertionError();
    }

    @NotNull
    public static String toHex(@NotNull String s) {
        return toHex(s.getBytes());
    }

    @NotNull
    public static String fromHex(@NotNull CharSequence hex) {
        try {
            return new String(hexToBytes(hex), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    @NotNull
    public static String toHex(byte[] bytes) {
        if (bytes == null) {
            return "";
        }

        final StringBuilder result = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            appendHex(result, b);
        }

        return result.toString();
    }

    private static void appendHex(@NotNull StringBuilder out, byte b) {
        out.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    @NotNull
    public static byte[] hexToBytes(@NotNull CharSequence hexString) {
        final int length = hexString.length() / 2;

        final byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = Integer.valueOf(hexString.subSequence(2 * i, 2 * i + 2).toString(), 16).byteValue();
        }

        return result;
    }

    public static byte[] generateRandomBytes(@NotNull String randomAlgorithm, int length) throws NoSuchAlgorithmException, NoSuchProviderException {
        assert length >= 0;

        final Random random = SecureRandom.getInstance(randomAlgorithm);
        byte[] result = new byte[length];
        random.nextBytes(result);
        return result;
    }
}
