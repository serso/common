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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 7:44 PM
 */
class ByteArrayCipherer implements Cipherer<byte[], byte[]> {

    @Nonnull
    private final String ciphererAlgorithm;

    @Nullable
    private final String provider;

    @Nonnull
    InitialVectorDef initialVectorDef;

    private ByteArrayCipherer(@Nonnull String ciphererAlgorithm,
                              @Nullable String provider,
                              @Nonnull InitialVectorDef initialVectorDef) {
        this.ciphererAlgorithm = ciphererAlgorithm;
        this.provider = provider;
        this.initialVectorDef = initialVectorDef;
    }

    @Nonnull
    public static Cipherer<byte[], byte[]> newInstance(@Nonnull String ciphererAlgorithm,
                                                       @Nullable String provider,
                                                       @Nonnull InitialVectorDef initialVectorDef) {
        return new ByteArrayCipherer(ciphererAlgorithm, provider, initialVectorDef);
    }

    @Nonnull
    public static Cipherer<byte[], byte[]> newNoIv(@Nonnull String ciphererAlgorithm,
                                                   @Nullable String provider) {
        return new ByteArrayCipherer(ciphererAlgorithm, provider, InitialVectorDef.newEmpty());
    }

    @Nonnull
    public byte[] encrypt(@Nonnull SecretKey secret,
                          @Nonnull byte[] decrypted) throws CiphererException {
        try {
            final IvParameterSpec ivParameterSpec = initialVectorDef.getEncryptIvParameterSpec();

            final Cipher encrypter = getEncrypter(secret, ivParameterSpec);

            final byte[] encrypted = encrypter.doFinal(decrypted);

            return initialVectorDef.postEncrypt(ivParameterSpec, encrypted);
        } catch (Exception e) {
            throw new CiphererException("Unable to encrypt due to some errors!", e);
        }
    }

    @Nonnull
    private Cipher getEncrypter(@Nonnull SecretKey secret,
                                @Nullable IvParameterSpec ivParameterSpec) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        final Cipher result;

        if (provider != null) {
            result = Cipher.getInstance(ciphererAlgorithm, provider);
        } else {
            result = Cipher.getInstance(ciphererAlgorithm);
        }

        if (ivParameterSpec != null) {
            result.init(Cipher.ENCRYPT_MODE, secret, ivParameterSpec);
        } else {
            result.init(Cipher.ENCRYPT_MODE, secret);
        }

        return result;
    }

    @Nonnull
    @Override
    public byte[] decrypt(@Nonnull SecretKey secret, @Nonnull byte[] encrypted) throws CiphererException {
        try {
            final IvParameterSpec ivParameterSpec = initialVectorDef.getDecryptIvParameterSpec(encrypted);

            final byte[] encryptedBytes = initialVectorDef.preDecrypt(encrypted);

            final Cipher decrypter = getDecrypter(secret, ivParameterSpec);

            return decrypter.doFinal(encryptedBytes);
        } catch (Exception e) {
            throw new CiphererException("Unable to decrypt due to some errors!", e);
        }
    }

    @Nonnull
    private Cipher getDecrypter(@Nonnull SecretKey secret,
                                @Nullable IvParameterSpec ivParameterSpec) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher result;

        if (provider != null) {
            result = Cipher.getInstance(ciphererAlgorithm, provider);
        } else {
            result = Cipher.getInstance(ciphererAlgorithm);
        }

        if (ivParameterSpec != null) {
            result.init(Cipher.DECRYPT_MODE, secret, ivParameterSpec);
        } else {
            result.init(Cipher.DECRYPT_MODE, secret);
        }

        return result;
    }

}
