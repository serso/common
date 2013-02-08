package org.solovyev.common.text;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.Bytes;

import java.nio.charset.Charset;

public final class HexString implements java.io.Serializable, Comparable<HexString>, CharSequence {

    @NotNull
    private static final HexString EMPTY = newInstance("", "");

    @NotNull
    private final String hex;

    @NotNull
    private final String original;

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    private HexString(@NotNull String hex, @NotNull String original) {
        this.hex = hex;
        this.original = original;
    }

    private HexString(@NotNull CharSequence hex, @NotNull String original) {
        this.hex = hex.toString();
        this.original = original;
    }

    @NotNull
    public static HexString fromString(@NotNull String value) {
        return newInstance(Strings.toHex(value), value);
    }

    @NotNull
    public static HexString fromHexString(@NotNull CharSequence hex) {
        return newInstance(hex, Strings.fromHex(hex));
    }

    @NotNull
    public static HexString fromBytes(@NotNull byte[] bytes) {
        return newInstance(Bytes.toHex(bytes), new String(bytes, Charset.forName("UTF-8")));
    }

    @NotNull
    public static HexString fromHexBytes(@NotNull byte[] hexBytes) {
        return fromHexString(new String(hexBytes, Charset.forName("UTF-8")));
    }


    @NotNull
    public static HexString newEmpty() {
        return EMPTY;
    }

    @NotNull
    private static HexString newInstance(@NotNull String hex, @NotNull String original) {
        return new HexString(hex, original);
    }

    @NotNull
    private static HexString newInstance(@NotNull CharSequence hex, @NotNull String original) {
        return new HexString(hex, original);
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @NotNull
    public String toString() {
        return hex;
    }

    @NotNull
    public String getHex() {
        return hex;
    }

    @NotNull
    public String getOriginal() {
        return original;
    }

    @Override
    public int length() {
        return hex.length();
    }

    @Override
    public char charAt(int index) {
        return hex.charAt(index);
    }

    @Override
    public HexString subSequence(int start, int end) {
        return fromHexString(hex.subSequence(start, end));
    }

    @Override
    public int compareTo(HexString that) {
        return this.hex.compareTo(that.hex);
    }

    @NotNull
    public HexString concat(@NotNull HexString that) {
        return newInstance(this.hex + that.hex, this.original + that.original);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HexString)) return false;

        final HexString that = (HexString) o;

        if (!hex.equals(that.hex)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return hex.hashCode();
    }

    public boolean isEmpty() {
        return hex.isEmpty();
    }

    @NotNull
    public byte[] getBytes() {
        return this.hex.getBytes(Charset.forName("UTF-8"));
    }

    @NotNull
    public byte[] getOriginalBytes() {
        return Bytes.hexToBytes(this.hex);
    }

    @NotNull
    public HexString substring(int beginIndex, int endIndex) {
        return fromHexString(this.hex.substring(beginIndex, endIndex));
    }

    @NotNull
    public HexString substring(int beginIndex) {
        return fromHexString(this.hex.substring(beginIndex));
    }
}
