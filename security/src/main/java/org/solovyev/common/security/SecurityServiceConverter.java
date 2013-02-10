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

/**
 * User: serso
 * Date: 2/9/13
 * Time: 5:23 PM
 */
public class SecurityServiceConverter<E, D, H> implements SecurityService<E, D, H> {

    @NotNull
    private final SecurityService<byte[], byte[], byte[]> securityService;

    @NotNull
    private final Cipherer<E, D> cipherer;

    @NotNull
    private final HashProvider<D, H> hashProvider;

    private SecurityServiceConverter(@NotNull SecurityService<byte[], byte[], byte[]> securityService,
                                     @NotNull Cipherer<E, D> cipherer,
                                     @NotNull HashProvider<D, H> hashProvider) {
        this.securityService = securityService;
        this.cipherer = cipherer;
        this.hashProvider = hashProvider;
    }

    @NotNull
    public static <T> SecurityService<T, T, T> wrap(@NotNull SecurityService<byte[], byte[], byte[]> securityService,
                                                    @NotNull Converter<T, byte[]> decoder,
                                                    @NotNull Converter<byte[], T> encoder) {
        final Cipherer<T, T> cipherer = TypedCipherer.newInstance(securityService.getCipherer(), decoder, encoder);
        final HashProvider<T, T> hashProvider = TypedHashProvider.newInstance(securityService.getHashProvider(), decoder, encoder);
        return new SecurityServiceConverter<T, T, T>(securityService, cipherer, hashProvider);
    }

    @NotNull
    public static <E, D> SecurityServiceConverter<E, D, E> wrap(@NotNull SecurityService<byte[], byte[], byte[]> securityService,
                                                                @NotNull Converter<D, byte[]> decryptedDecoder,
                                                                @NotNull Converter<byte[], D> decryptedEncoder,
                                                                @NotNull Converter<E, byte[]> encryptedDecoder,
                                                                @NotNull Converter<byte[], E> encryptedEncoder) {
        return wrap(securityService, decryptedDecoder, decryptedEncoder, encryptedDecoder, encryptedEncoder, encryptedEncoder);
    }

    @NotNull
    public static <E, D, H> SecurityServiceConverter<E, D, H> wrap(@NotNull SecurityService<byte[], byte[], byte[]> securityService,
                                                                   @NotNull Converter<D, byte[]> decryptedDecoder,
                                                                   @NotNull Converter<byte[], D> decryptedEncoder,
                                                                   @NotNull Converter<E, byte[]> encryptedDecoder,
                                                                   @NotNull Converter<byte[], E> encryptedEncoder,
                                                                   @NotNull Converter<byte[], H> hashConverter) {
        final Cipherer<E, D> cipherer = TypedCipherer.newInstance(securityService.getCipherer(), decryptedDecoder, decryptedEncoder, encryptedDecoder, encryptedEncoder);
        final HashProvider<D, H> hashProvider = TypedHashProvider.newInstance(securityService.getHashProvider(), decryptedDecoder, hashConverter);
        return new SecurityServiceConverter<E, D, H>(securityService, cipherer, hashProvider);
    }

    @NotNull
    @Override
    public SaltGenerator getSaltGenerator() {
        return securityService.getSaltGenerator();
    }

    @NotNull
    @Override
    public SecretKeyProvider getSecretKeyProvider() {
        return securityService.getSecretKeyProvider();
    }

    @NotNull
    @Override
    public HashProvider<D, H> getHashProvider() {
        return hashProvider;
    }

    @NotNull
    @Override
    public Cipherer<E, D> getCipherer() {
        return cipherer;
    }
}
