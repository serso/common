/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ---------------------------------------------------------------------
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.Bytes;
import org.solovyev.common.collections.Collections;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.Arrays;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 7:44 PM
 */
class ByteArrayCipherer implements Cipherer<byte[], byte[]> {

    private static final String PROVIDER = "BC";
    private static final int IV_LENGTH = 16;

    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final byte[] EMPTY = new byte[]{};

    @Nullable
    private InitialVectorDef initialVectorDef;

    @NotNull
    private final String cipherAlgorithm;

    @Nullable
    private final String provider;

    private ByteArrayCipherer(@Nullable InitialVectorDef initialVectorDef,
                              @NotNull String cipherAlgorithm,
                              @Nullable String provider) {
        this.initialVectorDef = initialVectorDef;
        this.cipherAlgorithm = cipherAlgorithm;
        this.provider = provider;
    }

    @NotNull
    public static Cipherer<byte[], byte[]> newAndroidAesCipherer() {
        return newInstance(InitialVectorDef.newSha1Prng(IV_LENGTH), CIPHER_ALGORITHM, PROVIDER);
    }

    @NotNull
    public static Cipherer<byte[], byte[]> newInstance(@Nullable InitialVectorDef initialVectorDef,
                                           @NotNull String cipherAlgorithm,
                                           @Nullable String provider) {
        return new ByteArrayCipherer(initialVectorDef, cipherAlgorithm, provider);
    }

    @Override
    @NotNull
    public byte[] encrypt(@NotNull SecretKey secret,
                             @NotNull byte[] decrypted) throws CiphererException {
        try {
            final byte[] ivHex;

            if (initialVectorDef != null) {
                ivHex = Bytes.generateRandomBytes(initialVectorDef.getRandomAlgorithm(), initialVectorDef.getLength());
            } else {
                ivHex = new byte[]{};
            }

            return encrypt(secret, decrypted, ivHex);
        } catch (Exception e) {
            throw new CiphererException("Unable to encrypt due to some errors!", e);
        }
    }

    @NotNull
    public byte[] encrypt(@NotNull SecretKey secret,
                             @NotNull byte[] decrypted,
                             @NotNull byte[] ivHex) throws CiphererException {
        try {
            final IvParameterSpec ivParameterSpec;
            if (initialVectorDef != null) {
                ivParameterSpec = new IvParameterSpec(ivHex);
            } else {
                ivParameterSpec = null;
            }

            final Cipher encryptionCipher;
            if (provider != null) {
                encryptionCipher = Cipher.getInstance(cipherAlgorithm, provider);
            } else {
                encryptionCipher = Cipher.getInstance(cipherAlgorithm);
            }

            if (ivParameterSpec != null) {
                encryptionCipher.init(Cipher.ENCRYPT_MODE, secret, ivParameterSpec);
            } else {
                encryptionCipher.init(Cipher.ENCRYPT_MODE, secret);
            }

            final byte[] encrypted = encryptionCipher.doFinal(decrypted);

            if ( ivHex.length == 0 ) {
                return encrypted;
            } else {
                return Collections.concat(ivHex, encrypted);
            }
        } catch (Exception e) {
            throw new CiphererException("Unable to encrypt due to some errors!", e);
        }
    }

    @NotNull
    @Override
    public byte[] decrypt(@NotNull SecretKey secret, @NotNull byte[] encrypted) throws CiphererException {
        try {
            final byte[] ivBytes = getIvHexFromEncrypted(encrypted);

            final byte[] encryptedBytes;
            if (initialVectorDef != null) {
                encryptedBytes = Arrays.copyOfRange(encrypted, initialVectorDef.getLength(), encrypted.length);
            } else {
                encryptedBytes = encrypted;
            }

            final Cipher decryptionCipher;
            if (provider != null) {
                decryptionCipher = Cipher.getInstance(cipherAlgorithm, provider);
            } else {
                decryptionCipher = Cipher.getInstance(cipherAlgorithm);
            }

            if (ivBytes.length > 0) {
                final IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
                decryptionCipher.init(Cipher.DECRYPT_MODE, secret, ivParameterSpec);
            } else {
                decryptionCipher.init(Cipher.DECRYPT_MODE, secret);
            }

            return decryptionCipher.doFinal(encryptedBytes);
        } catch (Exception e) {
            throw new CiphererException("Unable to decrypt due to some errors!", e);
        }
    }

    @NotNull
    public byte[] getIvHexFromEncrypted(@NotNull byte[] encrypted) throws CiphererException {
        try {
            if (initialVectorDef != null) {
                return Arrays.copyOfRange(encrypted, 0, initialVectorDef.getLength());
            } else {
                return EMPTY;
            }
        } catch (Exception e) {
            throw new CiphererException("Unable to extract initial vector!", e);
        }
    }
}
