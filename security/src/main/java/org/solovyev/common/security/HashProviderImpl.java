package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.HexUtils;

import java.security.MessageDigest;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:17 PM
 */
public class HashProviderImpl implements HashProvider {

    private static final String PROVIDER = "BC";
    private static final String HASH_ALGORITHM = "SHA-512";

    @NotNull
    private final String hashAlgorithm;

    @NotNull
    private final String provider;

    public HashProviderImpl(@NotNull String hashAlgorithm, @NotNull String provider) {
        this.hashAlgorithm = hashAlgorithm;
        this.provider = provider;
    }

    @NotNull
    public static HashProvider newAndroidDefaultInstance() {
        return new HashProviderImpl(HASH_ALGORITHM, PROVIDER);
    }

    @Override
    @NotNull
    public String getHash(@NotNull String text, @NotNull String salt) throws CiphererException {
        try {
            final String input = text + salt;
            final MessageDigest md = MessageDigest.getInstance(hashAlgorithm, provider);
            return HexUtils.toHex(md.digest(input.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new CiphererException("Unable to get hash due to some errors!", e);
        }
    }

}
