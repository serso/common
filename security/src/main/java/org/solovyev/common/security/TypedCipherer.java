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
import org.solovyev.common.Converter;

import javax.crypto.SecretKey;

public class TypedCipherer<E, D> implements Cipherer<E, D> {

    @NotNull
    private Cipherer<byte[], byte[]> byteCipherer;

    @NotNull
    private Converter<D, byte[]> decryptedDecoder;

    @NotNull
    private Converter<byte[], D> decryptedEncoder;

    @NotNull
    private Converter<E, byte[]> encryptedDecoder;

    @NotNull
    private Converter<byte[], E> encryptedEncoder;

    private TypedCipherer(@NotNull Cipherer<byte[], byte[]> byteCipherer,
                          @NotNull Converter<D, byte[]> decryptedDecoder,
                          @NotNull Converter<byte[], D> decryptedEncoder,
                          @NotNull Converter<E, byte[]> encryptedDecoder,
                          @NotNull Converter<byte[], E> encryptedEncoder) {
        this.byteCipherer = byteCipherer;
        this.decryptedDecoder = decryptedDecoder;
        this.decryptedEncoder = decryptedEncoder;
        this.encryptedDecoder = encryptedDecoder;
        this.encryptedEncoder = encryptedEncoder;
    }

    @NotNull
    public static <E, D> TypedCipherer<E, D> newInstance(@NotNull Cipherer<byte[], byte[]> byteCipherer,
                                                         @NotNull Converter<D, byte[]> decryptedDecoder,
                                                         @NotNull Converter<byte[], D> decryptedEncoder,
                                                         @NotNull Converter<E, byte[]> encryptedDecoder,
                                                         @NotNull Converter<byte[], E> encryptedEncoder) {
        return new TypedCipherer<E, D>(byteCipherer, decryptedDecoder, decryptedEncoder, encryptedDecoder, encryptedEncoder);
    }

    @NotNull
    public static <T> TypedCipherer<T, T> newInstance(@NotNull Cipherer<byte[], byte[]> byteCipherer,
                                                      @NotNull Converter<T, byte[]> decoder,
                                                      @NotNull Converter<byte[], T> encoder) {
        return new TypedCipherer<T, T>(byteCipherer, decoder, encoder, decoder, encoder);
    }


    @NotNull
    @Override
    public E encrypt(@NotNull SecretKey secret, @NotNull D decrypted) throws CiphererException {
        byte[] decryptedBytes = decryptedDecoder.convert(decrypted);
        final byte[] encryptedBytes = byteCipherer.encrypt(secret, decryptedBytes);
        return encryptedEncoder.convert(encryptedBytes);
    }

    @NotNull
    @Override
    public D decrypt(@NotNull SecretKey secret, @NotNull E encrypted) throws CiphererException {
        byte[] encryptedBytes = encryptedDecoder.convert(encrypted);
        final byte[] decryptedBytes = byteCipherer.decrypt(secret, encryptedBytes);
        return decryptedEncoder.convert(decryptedBytes);
    }
}
