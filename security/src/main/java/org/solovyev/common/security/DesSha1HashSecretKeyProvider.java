package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.text.HexString;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class DesSha1HashSecretKeyProvider implements SecretKeyProvider {

    @NotNull
    private static final SecretKeyProvider instance = new DesSha1HashSecretKeyProvider();

    private DesSha1HashSecretKeyProvider() {
    }

    @NotNull
    public static SecretKeyProvider newInstance() {
        return instance;
    }

    @NotNull
    @Override
    public SecretKey getSecretKey(@NotNull String password, @NotNull String salt) throws CiphererException {
        final String secretKey = password + salt;
        try {
            final MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] secretKeyHash = sha.digest(secretKey.getBytes("UTF-8"));
            secretKeyHash = Arrays.copyOf(secretKeyHash, 16); // use only first 128 bit
            return new SecretKeySpec(HexString.fromString(secretKey).getBytes(), "DES");
        } catch (NoSuchAlgorithmException e) {
            throw new CiphererException(e);
        } catch (UnsupportedEncodingException e) {
            throw new CiphererException(e);
        }
    }
}
