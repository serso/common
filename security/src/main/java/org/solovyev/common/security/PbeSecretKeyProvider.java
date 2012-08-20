package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.HexUtils;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:08 PM
 */
public class PbeSecretKeyProvider implements SecretKeyProvider {

    private static final int PBE_ITERATION_COUNT = 100;
    private static final String PBE_ALGORITHM = "PBEWithSHA256And256BitAES-CBC-BC";
    private static final String PROVIDER = "BC";
    private static final String SECRET_KEY_ALGORITHM = "AES";
    private static final int PBE_KEY_LENGTH = 256;

    private final int iterationCount;

    @NotNull
    private final String algorithm;

    @Nullable
    private final String provider;

    @NotNull
    private final String secretKeyAlgorithm;

    public PbeSecretKeyProvider(int iterationCount,
                                @NotNull String algorithm,
                                @Nullable String provider,
                                @NotNull String secretKeyAlgorithm) {
        this.iterationCount = iterationCount;
        this.algorithm = algorithm;
        this.provider = provider;
        this.secretKeyAlgorithm = secretKeyAlgorithm;
    }

    @NotNull
    public static SecretKeyProvider newAndroidDefaultInstance() {
        return new PbeSecretKeyProvider(PBE_ITERATION_COUNT, PBE_ALGORITHM, PROVIDER, SECRET_KEY_ALGORITHM);
    }

    @Override
    @NotNull
    public SecretKey getSecretKey(@NotNull String password, @NotNull String salt) throws CiphererException {
        try {
            final PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), HexUtils.toBytes(salt), iterationCount, PBE_KEY_LENGTH);
            final SecretKeyFactory factory;
            if (provider != null) {
                factory = SecretKeyFactory.getInstance(algorithm, provider);
            } else {
                factory = SecretKeyFactory.getInstance(algorithm);
            }
            final SecretKey tmp = factory.generateSecret(pbeKeySpec);

            return new SecretKeySpec(tmp.getEncoded(), secretKeyAlgorithm);
        } catch (Exception e) {
            throw new CiphererException("Unable to get secret key due to some errors!", e);
        }
    }
}
