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

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:08 PM
 */
class PbeSecretKeyProvider implements SecretKeyProvider {

    private final int iterationCount;

    @Nonnull
    private final String algorithm;

    @Nonnull
    private final String provider;

    @Nonnull
    private final String ciphererAlgorithm;

    private final int keyLength;

    private final int saltLength;

    private PbeSecretKeyProvider(int iterationCount,
                                 @Nonnull String algorithm,
                                 @Nonnull String ciphererAlgorithm,
                                 @Nonnull String provider,
                                 int keyLength,
                                 int saltLength) {
        this.iterationCount = iterationCount;
        this.algorithm = algorithm;
        this.provider = provider;
        this.ciphererAlgorithm = ciphererAlgorithm;
        this.keyLength = keyLength;
        this.saltLength = saltLength;
    }

    @Nonnull
    public static SecretKeyProvider newInstance(int iterationCount,
                                                @Nonnull String algorithm,
                                                @Nonnull String ciphererAlgorithm,
                                                @Nonnull String provider,
                                                int keyLength,
                                                int saltLength) {
        return new PbeSecretKeyProvider(iterationCount, algorithm, ciphererAlgorithm, provider, keyLength, saltLength);
    }

    @Override
    @Nonnull
    public SecretKey getSecretKey(@Nonnull String secret, @Nonnull byte[] salt) throws CiphererException {
        try {
            if (salt.length != saltLength) {
                throw new IllegalArgumentException("Salt size is not valid -  expected: " + saltLength + ", actual: " + salt.length);
            }

            final PBEKeySpec pbeKeySpec = new PBEKeySpec(secret.toCharArray(), salt, iterationCount, keyLength);
            final SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm, provider);

            final SecretKey tmp = factory.generateSecret(pbeKeySpec);

            return new SecretKeySpec(tmp.getEncoded(), ciphererAlgorithm);
        } catch (Exception e) {
            throw new CiphererException("Unable to get secret key due to some errors!", e);
        }
    }
}
