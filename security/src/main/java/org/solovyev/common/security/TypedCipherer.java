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
import org.solovyev.common.Converter;

import javax.crypto.SecretKey;

public class TypedCipherer<E, D> implements Cipherer<E, D> {

    @Nonnull
    private Cipherer<byte[], byte[]> byteCipherer;

    @Nonnull
    private Converter<D, byte[]> decryptedDecoder;

    @Nonnull
    private Converter<byte[], D> decryptedEncoder;

    @Nonnull
    private Converter<E, byte[]> encryptedDecoder;

    @Nonnull
    private Converter<byte[], E> encryptedEncoder;

    private TypedCipherer(@Nonnull Cipherer<byte[], byte[]> byteCipherer,
                          @Nonnull Converter<D, byte[]> decryptedDecoder,
                          @Nonnull Converter<byte[], D> decryptedEncoder,
                          @Nonnull Converter<E, byte[]> encryptedDecoder,
                          @Nonnull Converter<byte[], E> encryptedEncoder) {
        this.byteCipherer = byteCipherer;
        this.decryptedDecoder = decryptedDecoder;
        this.decryptedEncoder = decryptedEncoder;
        this.encryptedDecoder = encryptedDecoder;
        this.encryptedEncoder = encryptedEncoder;
    }

    @Nonnull
    public static <E, D> TypedCipherer<E, D> newInstance(@Nonnull Cipherer<byte[], byte[]> byteCipherer,
                                                         @Nonnull Converter<D, byte[]> decryptedDecoder,
                                                         @Nonnull Converter<byte[], D> decryptedEncoder,
                                                         @Nonnull Converter<E, byte[]> encryptedDecoder,
                                                         @Nonnull Converter<byte[], E> encryptedEncoder) {
        return new TypedCipherer<E, D>(byteCipherer, decryptedDecoder, decryptedEncoder, encryptedDecoder, encryptedEncoder);
    }

    @Nonnull
    public static <T> TypedCipherer<T, T> newInstance(@Nonnull Cipherer<byte[], byte[]> byteCipherer,
                                                      @Nonnull Converter<T, byte[]> decoder,
                                                      @Nonnull Converter<byte[], T> encoder) {
        return new TypedCipherer<T, T>(byteCipherer, decoder, encoder, decoder, encoder);
    }


    @Nonnull
    @Override
    public E encrypt(@Nonnull SecretKey secret, @Nonnull D decrypted) throws CiphererException {
        byte[] decryptedBytes = decryptedDecoder.convert(decrypted);
        final byte[] encryptedBytes = byteCipherer.encrypt(secret, decryptedBytes);
        return encryptedEncoder.convert(encryptedBytes);
    }

    @Nonnull
    @Override
    public D decrypt(@Nonnull SecretKey secret, @Nonnull E encrypted) throws CiphererException {
        byte[] encryptedBytes = encryptedDecoder.convert(encrypted);
        final byte[] decryptedBytes = byteCipherer.decrypt(secret, encryptedBytes);
        return decryptedEncoder.convert(decryptedBytes);
    }

    @Nonnull
    public Cipherer<byte[], byte[]> getByteCipherer() {
        return byteCipherer;
    }
}
