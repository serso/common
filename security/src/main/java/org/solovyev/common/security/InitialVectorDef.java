package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;

public class InitialVectorDef {

    private static final String RANDOM_ALGORITHM = "SHA1PRNG";

    @NotNull
    private String randomAlgorithm;

    private int length;

    private InitialVectorDef(@NotNull String randomAlgorithm, int length) {
        this.length = length;
        this.randomAlgorithm = randomAlgorithm;
    }

    @NotNull
    public static InitialVectorDef newInstance(@NotNull String randomAlgorithm, int length) {
        return new InitialVectorDef(randomAlgorithm, length);
    }

    @NotNull
    public static InitialVectorDef newSha1Prng(int length) {
        return new InitialVectorDef(RANDOM_ALGORITHM, length);
    }

    @NotNull
    public String getRandomAlgorithm() {
        return randomAlgorithm;
    }

    public int getLength() {
        return length;
    }

    public int getHexLength() {
        return getLength() * 2;
    }
}
