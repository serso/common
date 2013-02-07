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
import org.solovyev.common.text.HexString;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 7:44 PM
 */
class CiphererImpl implements Cipherer {

    private static final String PROVIDER = "BC";
    private static final int IV_LENGTH = 16;

    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    @Nullable
    private InitialVectorDef initialVectorDef;

    @NotNull
    private final String cipherAlgorithm;

    @Nullable
    private final String provider;

    private CiphererImpl(@Nullable InitialVectorDef initialVectorDef,
                         @NotNull String cipherAlgorithm,
                         @Nullable String provider) {
        this.initialVectorDef = initialVectorDef;
        this.cipherAlgorithm = cipherAlgorithm;
        this.provider = provider;
    }

    @NotNull
    public static Cipherer newAndroidAesCipherer() {
        return newInstance(InitialVectorDef.newSha1Prng(IV_LENGTH), CIPHER_ALGORITHM, PROVIDER);
    }

    @NotNull
    public static CiphererImpl newInstance(@Nullable InitialVectorDef initialVectorDef,
                                           @NotNull String cipherAlgorithm,
                                           @Nullable String provider) {
        return new CiphererImpl(initialVectorDef, cipherAlgorithm, provider);
    }

    @Override
    @NotNull
    public HexString encrypt(@NotNull SecretKey secret,
                             @NotNull String plainText) throws CiphererException {
        try {
            final HexString ivHex;

            if (initialVectorDef != null) {
                final byte[] iv = Bytes.generateRandomBytes(initialVectorDef.getRandomAlgorithm(), initialVectorDef.getLength());
                ivHex = HexString.fromBytes(iv);
            } else {
                ivHex = HexString.newEmpty();
            }

            return encrypt(secret, plainText, ivHex);
        } catch (Exception e) {
            throw new CiphererException("Unable to encrypt due to some errors!", e);
        }
    }

    @NotNull
    public HexString encrypt(@NotNull SecretKey secret,
                             @NotNull String plainText,
                             @NotNull HexString ivHex) throws CiphererException {
        try {
            final IvParameterSpec ivParameterSpec;
            if (initialVectorDef != null) {
                ivParameterSpec = new IvParameterSpec(ivHex.getOriginal().getBytes("UTF-8"));
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

            final byte[] encrypted = encryptionCipher.doFinal(plainText.getBytes("UTF-8"));

            return ivHex.concat(HexString.fromBytes(encrypted));
        } catch (Exception e) {
            throw new CiphererException("Unable to encrypt due to some errors!", e);
        }
    }

    @NotNull
    @Override
    public String decrypt(@NotNull SecretKey secret, @NotNull HexString encryptedText) throws CiphererException {
        try {
            final HexString ivHex = getIvHexFromEncrypted(encryptedText);

            final HexString encryptedHex;
            if (initialVectorDef != null) {
                encryptedHex = encryptedText.substring(initialVectorDef.getHexLength());
            } else {
                encryptedHex = encryptedText;
            }

            final Cipher decryptionCipher;
            if (provider != null) {
                decryptionCipher = Cipher.getInstance(cipherAlgorithm, provider);
            } else {
                decryptionCipher = Cipher.getInstance(cipherAlgorithm);
            }

            if (!ivHex.isEmpty()) {
                final IvParameterSpec ivParameterSpec = new IvParameterSpec(ivHex.getOriginal().getBytes("UTF-8"));
                decryptionCipher.init(Cipher.DECRYPT_MODE, secret, ivParameterSpec);
            } else {
                decryptionCipher.init(Cipher.DECRYPT_MODE, secret);
            }

            final byte[] decrypted = decryptionCipher.doFinal(encryptedHex.getOriginal().getBytes("UTF-8"));
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            throw new CiphererException("Unable to decrypt due to some errors!", e);
        }
    }

    @NotNull
    public HexString getIvHexFromEncrypted(@NotNull HexString encryptedText) throws CiphererException {
        try {
            if (initialVectorDef != null) {
                return encryptedText.substring(0, initialVectorDef.getHexLength());
            } else {
                return HexString.newEmpty();
            }
        } catch (Exception e) {
            throw new CiphererException("Unable to extract initial vector!", e);
        }
    }
}
