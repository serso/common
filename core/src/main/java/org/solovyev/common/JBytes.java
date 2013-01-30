package org.solovyev.common;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:02 PM
 */
public final class JBytes {

    private final static String HEX = "0123456789ABCDEF";

    private JBytes() {
        throw new AssertionError();
    }

    @NotNull
    public static String toHex(@NotNull String s) {
        return toHex(s.getBytes());
    }

    @NotNull
    public static String fromHex(@NotNull String hex) {
        return new String(toBytes(hex));
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
    public static byte[] toBytes(@NotNull String hexString) {
        final int length = hexString.length() / 2;

        final byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }

        return result;
    }
}
