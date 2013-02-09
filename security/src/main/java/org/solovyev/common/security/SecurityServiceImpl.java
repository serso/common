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

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:24 PM
 */
class SecurityServiceImpl<E, D> implements SecurityService<E, D> {

    @NotNull
    private Cipherer<E, D> cipherer;

    @NotNull
    private SecretKeyProvider secretKeyProvider;

    @NotNull
    private SaltGenerator saltGenerator;

    @NotNull
    private HashProvider hashProvider;

    private SecurityServiceImpl(@NotNull Cipherer<E, D> cipherer,
                                @NotNull SecretKeyProvider secretKeyProvider,
                                @NotNull SaltGenerator saltGenerator,
                                @NotNull HashProvider hashProvider) {
        this.cipherer = cipherer;
        this.secretKeyProvider = secretKeyProvider;
        this.saltGenerator = saltGenerator;
        this.hashProvider = hashProvider;
    }

    @NotNull
    static <E, D> SecurityService<E, D> newInstance(@NotNull Cipherer<E, D> cipherer,
                                                           @NotNull SecretKeyProvider secretKeyProvider,
                                                           @NotNull SaltGenerator saltGenerator,
                                                           @NotNull HashProvider hashProvider) {
        return new SecurityServiceImpl<E, D>(cipherer, secretKeyProvider, saltGenerator, hashProvider);
    }


    @NotNull
    @Override
    public SaltGenerator getSaltGenerator() {
        return saltGenerator;
    }

    @NotNull
    @Override
    public SecretKeyProvider getSecretKeyProvider() {
        return secretKeyProvider;
    }

    @NotNull
    @Override
    public HashProvider getHashProvider() {
        return hashProvider;
    }

    @NotNull
    @Override
    public Cipherer<E, D> getCipherer() {
        return cipherer;
    }
}
