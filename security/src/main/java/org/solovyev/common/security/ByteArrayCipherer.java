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
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org/java/jcl
 */

package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.Bytes;
import org.solovyev.common.collections.Collections;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 7:44 PM
 */
class ByteArrayCipherer implements Cipherer<byte[], byte[]> {

    private static final byte[] EMPTY = new byte[]{};

    @NotNull
    private final String ciphererAlgorithm;

    @Nullable
    private final String provider;

    @Nullable
    private InitialVectorDef initialVectorDef;

    // initialized after first usage
    private volatile Cipher encrypter;

    // initialized after first usage
    private volatile Cipher decrypter;

    private ByteArrayCipherer(@NotNull String ciphererAlgorithm,
                              @Nullable String provider,
                              @Nullable InitialVectorDef initialVectorDef) {
        this.ciphererAlgorithm = ciphererAlgorithm;
        this.provider = provider;
        this.initialVectorDef = initialVectorDef;
    }

    @NotNull
    public static Cipherer<byte[], byte[]> newInstance(@Nullable InitialVectorDef initialVectorDef,
                                                       @NotNull String ciphererAlgorithm,
                                                       @Nullable String provider) {
        return new ByteArrayCipherer(ciphererAlgorithm, provider, initialVectorDef);
    }

    @Override
    @NotNull
    public byte[] encrypt(@NotNull SecretKey secret,
                          @NotNull byte[] decrypted) throws CiphererException {
        try {
            final byte[] ivBytes;

            if (initialVectorDef != null) {
                final byte[] predefinedIvBytes = initialVectorDef.getBytes();
                if ( predefinedIvBytes != null ) {
                    ivBytes = predefinedIvBytes;
                } else {
                    ivBytes = Bytes.generateSecureRandomBytes(initialVectorDef.getRandomAlgorithm(), initialVectorDef.getLength());
                }
            } else {
                ivBytes = new byte[]{};
            }

            return encrypt(secret, decrypted, ivBytes);
        } catch (Exception e) {
            throw new CiphererException("Unable to encrypt due to some errors!", e);
        }
    }

    @NotNull
    public byte[] encrypt(@NotNull SecretKey secret,
                          @NotNull byte[] decrypted,
                          @NotNull byte[] ivBytes) throws CiphererException {
        try {
            final IvParameterSpec ivParameterSpec;
            if (initialVectorDef != null) {
                ivParameterSpec = new IvParameterSpec(ivBytes);
            } else {
                ivParameterSpec = null;
            }

            initEncrypter(secret, ivParameterSpec);

            final byte[] encrypted = encrypter.doFinal(decrypted);

            if (ivBytes.length == 0) {
                return encrypted;
            } else {
                if (initialVectorDef != null && initialVectorDef.getBytes() != null) {
                    return encrypted;
                } else {
                    return Collections.concat(ivBytes, encrypted);
                }
            }
        } catch (Exception e) {
            throw new CiphererException("Unable to encrypt due to some errors!", e);
        }
    }

    private void initEncrypter(@NotNull SecretKey secret,
                               @Nullable IvParameterSpec ivParameterSpec) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (encrypter == null) {
            synchronized (this) {
                if (encrypter == null) {

                    if (provider != null) {
                        encrypter = Cipher.getInstance(ciphererAlgorithm, provider);
                    } else {
                        encrypter = Cipher.getInstance(ciphererAlgorithm);
                    }

                    if (ivParameterSpec != null) {
                        encrypter.init(Cipher.ENCRYPT_MODE, secret, ivParameterSpec);
                    } else {
                        encrypter.init(Cipher.ENCRYPT_MODE, secret);
                    }

                }
            }
        }
    }

    @NotNull
    @Override
    public byte[] decrypt(@NotNull SecretKey secret, @NotNull byte[] encrypted) throws CiphererException {
        try {
            final byte[] ivBytes = getIvBytes(encrypted);

            final IvParameterSpec ivParameterSpec;
            if (ivBytes.length > 0) {
                ivParameterSpec = new IvParameterSpec(ivBytes);
            } else {
                ivParameterSpec = null;
            }

            final byte[] encryptedBytes;
            if (initialVectorDef != null) {
                if (initialVectorDef.getBytes() == null) {
                    encryptedBytes = Arrays.copyOfRange(encrypted, initialVectorDef.getLength(), encrypted.length);
                } else {
                    encryptedBytes = encrypted;
                }
            } else {
                encryptedBytes = encrypted;
            }

            initDescrypter(secret, ivParameterSpec);

            return decrypter.doFinal(encryptedBytes);
        } catch (Exception e) {
            throw new CiphererException("Unable to decrypt due to some errors!", e);
        }
    }

    private void initDescrypter(@NotNull SecretKey secret,
                                @Nullable IvParameterSpec ivParameterSpec) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (decrypter == null) {
            synchronized (this) {
                if (decrypter == null) {
                    if (provider != null) {
                        decrypter = Cipher.getInstance(ciphererAlgorithm, provider);
                    } else {
                        decrypter = Cipher.getInstance(ciphererAlgorithm);
                    }

                    if (ivParameterSpec != null) {
                        decrypter.init(Cipher.DECRYPT_MODE, secret, ivParameterSpec);
                    } else {
                        decrypter.init(Cipher.DECRYPT_MODE, secret);
                    }
                }
            }
        }
    }

    @NotNull
    public byte[] getIvBytes(@NotNull byte[] encrypted) throws CiphererException {
        try {
            if (initialVectorDef != null) {
                final byte[] predefinedIvBytes = initialVectorDef.getBytes();
                if ( predefinedIvBytes != null ) {
                    return predefinedIvBytes;
                } else {
                    return Arrays.copyOfRange(encrypted, 0, initialVectorDef.getLength());
                }
            } else {
                return EMPTY;
            }
        } catch (Exception e) {
            throw new CiphererException("Unable to extract initial vector!", e);
        }
    }
}
