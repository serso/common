package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.HexUtils;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:13 PM
 */
public class SaltGeneratorImpl implements SaltGenerator {

    private static final int SALT_LENGTH = 20;
    private static final String RANDOM_ALGORITHM = "SHA1PRNG";

    @NotNull
    private final String randomAlgorithm;
    private final int saltLength;

    public SaltGeneratorImpl(@NotNull String randomAlgorithm, int saltLength) {
        this.randomAlgorithm = randomAlgorithm;
        this.saltLength = saltLength;
    }

    @NotNull
    public static SaltGenerator newAndroidDefaultInstance() {
        return new SaltGeneratorImpl(RANDOM_ALGORITHM, SALT_LENGTH);
    }

    @Override
    @NotNull
    public String generateSalt() throws CiphererException {
        try {
            byte[] salt = SecurityUtils.generateRandomBytes(randomAlgorithm, saltLength);
            return HexUtils.toHex(salt);
        } catch (Exception e) {
            throw new CiphererException("Unable to generate salt due to some errors!", e);
        }
    }
}
