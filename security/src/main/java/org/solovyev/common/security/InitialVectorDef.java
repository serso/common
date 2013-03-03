package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.Bytes;
import org.solovyev.common.collections.Collections;

import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

public class InitialVectorDef {

    private static final String RANDOM_ALGORITHM = "SHA1PRNG";
    private static final byte[] EMPTY = new byte[]{};

    @NotNull
    private final String randomAlgorithm;

    private final int length;

    @Nullable
    private final byte[] bytes;

    private InitialVectorDef(@NotNull String randomAlgorithm, int length) {
        this.length = length;
        this.randomAlgorithm = randomAlgorithm;
        this.bytes = null;
    }

    public InitialVectorDef(@NotNull byte[] bytes) {
        this.bytes = Arrays.copyOf(bytes, bytes.length);
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
    public static InitialVectorDef newEmpty() {
        return new InitialVectorDef(EMPTY);
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

    @NotNull
    byte[] getInitialVector() throws NoSuchAlgorithmException, NoSuchProviderException {
        byte[] result;

        if ( this.bytes != null ) {
            result = this.bytes;
        } else {
            result = Bytes.generateSecureRandomBytes(this.randomAlgorithm, this.length);
        }

        return result;
    }

    byte[] postEncrypt(@Nullable IvParameterSpec ivParameterSpec, @NotNull byte[] encrypted) {
        if (this.bytes != null) {
            if (this.bytes.length > 0) {
                // bytes were predefined => are stored separately
                return encrypted;
            } else {
                // no initial vector => just return encrypted array
                return encrypted;
            }
        } else {
            // bytes were generated => store them in encrypted array
            assert ivParameterSpec != null;
            return Collections.concat(ivParameterSpec.getIV(), encrypted);
        }
    }

    byte[] preDecrypt(byte[] encrypted) {
        final byte[] encryptedBytes;
        if (this != null) {
            if (this.bytes == null) {
                encryptedBytes = Arrays.copyOfRange(encrypted, getLength(), encrypted.length);
            } else {
                encryptedBytes = encrypted;
            }
        } else {
            encryptedBytes = encrypted;
        }
        return encryptedBytes;
    }

    @Nullable
    IvParameterSpec getEncryptIvParameterSpec() throws NoSuchProviderException, NoSuchAlgorithmException {
        if ( this.bytes != null ) {
            if ( this.bytes.length > 0 ) {
                // predefined
                return new IvParameterSpec(this.bytes);
            } else {
                // no initial vector
                return null;
            }
        } else {
            final byte[] bytes = Bytes.generateSecureRandomBytes(this.randomAlgorithm, this.length);
            return new IvParameterSpec(bytes);
        }
    }

    @Nullable
    IvParameterSpec getDecryptIvParameterSpec(@NotNull byte[] encrypted) {
        if ( this.bytes != null ) {
            if ( this.bytes.length > 0 ) {
                return new IvParameterSpec(this.bytes);
            } else {
                return null;
            }
        } else {
            // generated => initial vector is stored with encrypted data
            final byte[] ivBytes = Arrays.copyOfRange(encrypted, 0, getLength());
            return new IvParameterSpec(ivBytes);
        }
    }
}
