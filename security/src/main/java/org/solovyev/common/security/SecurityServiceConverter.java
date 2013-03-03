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

/**
 * User: serso
 * Date: 2/9/13
 * Time: 5:23 PM
 */
public class SecurityServiceConverter<E, D, H> implements SecurityService<E, D, H> {

    @Nonnull
    private final SecurityService<byte[], byte[], byte[]> securityService;

    @Nonnull
    private final Cipherer<E, D> cipherer;

    @Nonnull
    private final HashProvider<D, H> hashProvider;

    private SecurityServiceConverter(@Nonnull SecurityService<byte[], byte[], byte[]> securityService,
                                     @Nonnull Cipherer<E, D> cipherer,
                                     @Nonnull HashProvider<D, H> hashProvider) {
        this.securityService = securityService;
        this.cipherer = cipherer;
        this.hashProvider = hashProvider;
    }

    @Nonnull
    public static <T> SecurityService<T, T, T> wrap(@Nonnull SecurityService<byte[], byte[], byte[]> securityService,
                                                    @Nonnull Converter<T, byte[]> decoder,
                                                    @Nonnull Converter<byte[], T> encoder) {
        final Cipherer<T, T> cipherer = TypedCipherer.newInstance(securityService.getCipherer(), decoder, encoder);
        final HashProvider<T, T> hashProvider = TypedHashProvider.newInstance(securityService.getHashProvider(), decoder, encoder);
        return new SecurityServiceConverter<T, T, T>(securityService, cipherer, hashProvider);
    }

    @Nonnull
    public static <E, D> SecurityServiceConverter<E, D, E> wrap(@Nonnull SecurityService<byte[], byte[], byte[]> securityService,
                                                                @Nonnull Converter<D, byte[]> decryptedDecoder,
                                                                @Nonnull Converter<byte[], D> decryptedEncoder,
                                                                @Nonnull Converter<E, byte[]> encryptedDecoder,
                                                                @Nonnull Converter<byte[], E> encryptedEncoder) {
        return wrap(securityService, decryptedDecoder, decryptedEncoder, encryptedDecoder, encryptedEncoder, encryptedEncoder);
    }

    @Nonnull
    public static <E, D, H> SecurityServiceConverter<E, D, H> wrap(@Nonnull SecurityService<byte[], byte[], byte[]> securityService,
                                                                   @Nonnull Converter<D, byte[]> decryptedDecoder,
                                                                   @Nonnull Converter<byte[], D> decryptedEncoder,
                                                                   @Nonnull Converter<E, byte[]> encryptedDecoder,
                                                                   @Nonnull Converter<byte[], E> encryptedEncoder,
                                                                   @Nonnull Converter<byte[], H> hashConverter) {
        final Cipherer<E, D> cipherer = TypedCipherer.newInstance(securityService.getCipherer(), decryptedDecoder, decryptedEncoder, encryptedDecoder, encryptedEncoder);
        final HashProvider<D, H> hashProvider = TypedHashProvider.newInstance(securityService.getHashProvider(), decryptedDecoder, hashConverter);
        return new SecurityServiceConverter<E, D, H>(securityService, cipherer, hashProvider);
    }

    @Nonnull
    @Override
    public SaltGenerator getSaltGenerator() {
        return securityService.getSaltGenerator();
    }

    @Nonnull
    @Override
    public SecretKeyProvider getSecretKeyProvider() {
        return securityService.getSecretKeyProvider();
    }

    @Nonnull
    @Override
    public HashProvider<D, H> getHashProvider() {
        return hashProvider;
    }

    @Nonnull
    @Override
    public Cipherer<E, D> getCipherer() {
        return cipherer;
    }
}
