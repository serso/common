package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InitialVectorDef {

    private static final String RANDOM_ALGORITHM = "SHA1PRNG";

    @NotNull
    private String randomAlgorithm;

    private int length;

    @Nullable
    private byte[] bytes;

    private InitialVectorDef(@NotNull String randomAlgorithm, int length) {
        this.length = length;
        this.randomAlgorithm = randomAlgorithm;
        this.bytes = null;
    }

    public InitialVectorDef(@NotNull byte[] bytes) {
        this.bytes = bytes;
        this.length = bytes.length;
        this.randomAlgorithm = "";
    }

    @NotNull
    public static InitialVectorDef newRandom(@NotNull String randomAlgorithm, int length) {
        return new InitialVectorDef(randomAlgorithm, length);
    }

    @NotNull
    public static InitialVectorDef newSha1PrngRandom(int length) {
        return new InitialVectorDef(RANDOM_ALGORITHM, length);
    }

    @NotNull
    public static InitialVectorDef newPredefined(byte[] bytes) {
        return new InitialVectorDef(bytes);
    }

    @NotNull
    public String getRandomAlgorithm() {
        return randomAlgorithm;
    }

    public int getLength() {
        return length;
    }

    @Nullable
    public byte[] getBytes() {
        return bytes;
    }

    public int getHexLength() {
        return getLength() * 2;
    }
}
