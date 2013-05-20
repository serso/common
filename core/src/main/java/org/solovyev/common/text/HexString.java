package org.solovyev.common.text;

import org.solovyev.common.Bytes;

import javax.annotation.Nonnull;
import java.nio.charset.Charset;

public final class HexString implements java.io.Serializable, Comparable<HexString>, CharSequence {

	@Nonnull
	private static final HexString EMPTY = newInstance("", "");

	@Nonnull
	private final String hex;

	@Nonnull
	private final String original;

	/*
	**********************************************************************
	*
	*                           CONSTRUCTORS
	*
	**********************************************************************
	*/

	private HexString(@Nonnull String hex, @Nonnull String original) {
		this.hex = hex;
		this.original = original;
	}

	private HexString(@Nonnull CharSequence hex, @Nonnull String original) {
		this.hex = hex.toString();
		this.original = original;
	}

	@Nonnull
	public static HexString fromString(@Nonnull String value) {
		return newInstance(Strings.toHex(value), value);
	}

	@Nonnull
	public static HexString fromHexString(@Nonnull CharSequence hex) {
		return newInstance(hex, Strings.fromHex(hex));
	}

	@Nonnull
	public static HexString fromBytes(@Nonnull byte[] bytes) {
		return newInstance(Bytes.toHex(bytes), new String(bytes, Charset.forName("UTF-8")));
	}

	@Nonnull
	public static HexString fromHexBytes(@Nonnull byte[] hexBytes) {
		return fromHexString(new String(hexBytes, Charset.forName("UTF-8")));
	}


	@Nonnull
	public static HexString newEmpty() {
		return EMPTY;
	}

	@Nonnull
	private static HexString newInstance(@Nonnull String hex, @Nonnull String original) {
		return new HexString(hex, original);
	}

	@Nonnull
	private static HexString newInstance(@Nonnull CharSequence hex, @Nonnull String original) {
		return new HexString(hex, original);
	}

	/*
	**********************************************************************
	*
	*                           METHODS
	*
	**********************************************************************
	*/

	@Nonnull
	public String toString() {
		return hex;
	}

	@Nonnull
	public String getHex() {
		return hex;
	}

	@Nonnull
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

	@Nonnull
	public HexString concat(@Nonnull HexString that) {
		return newInstance(this.hex + that.hex, this.original + that.original);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof HexString)) {
			return false;
		}

		final HexString that = (HexString) o;

		if (!hex.equals(that.hex)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return hex.hashCode();
	}

	public boolean isEmpty() {
		return hex.isEmpty();
	}

	@Nonnull
	public byte[] getBytes() {
		return this.hex.getBytes(Charset.forName("UTF-8"));
	}

	@Nonnull
	public byte[] getOriginalBytes() {
		return Bytes.hexToBytes(this.hex);
	}

	@Nonnull
	public HexString substring(int beginIndex, int endIndex) {
		return fromHexString(this.hex.substring(beginIndex, endIndex));
	}

	@Nonnull
	public HexString substring(int beginIndex) {
		return fromHexString(this.hex.substring(beginIndex));
	}
}
