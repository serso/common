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

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:24 PM
 */
class SecurityServiceImpl<E, D, H> implements SecurityService<E, D, H> {

    @Nonnull
    private Cipherer<E, D> cipherer;

    @Nonnull
    private SecretKeyProvider secretKeyProvider;

    @Nonnull
    private SaltGenerator saltGenerator;

    @Nonnull
    private HashProvider<D, H> hashProvider;

    private SecurityServiceImpl(@Nonnull Cipherer<E, D> cipherer,
                                @Nonnull SecretKeyProvider secretKeyProvider,
                                @Nonnull SaltGenerator saltGenerator,
                                @Nonnull HashProvider<D, H> hashProvider) {
        this.cipherer = cipherer;
        this.secretKeyProvider = secretKeyProvider;
        this.saltGenerator = saltGenerator;
        this.hashProvider = hashProvider;
    }

    @Nonnull
    static <E, D, H> SecurityService<E, D, H> newInstance(@Nonnull Cipherer<E, D> cipherer,
                                                          @Nonnull SecretKeyProvider secretKeyProvider,
                                                          @Nonnull SaltGenerator saltGenerator,
                                                          @Nonnull HashProvider<D, H> hashProvider) {
        return new SecurityServiceImpl<E, D, H>(cipherer, secretKeyProvider, saltGenerator, hashProvider);
    }


    @Nonnull
    @Override
    public SaltGenerator getSaltGenerator() {
        return saltGenerator;
    }

    @Nonnull
    @Override
    public SecretKeyProvider getSecretKeyProvider() {
        return secretKeyProvider;
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
