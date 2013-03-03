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

import org.solovyev.common.Bytes;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:13 PM
 */
class SaltGeneratorImpl implements SaltGenerator {

    @Nonnull
    private final String randomAlgorithm;

    private final int saltLength;

    private SaltGeneratorImpl(@Nonnull String randomAlgorithm, int saltLength) {
        this.randomAlgorithm = randomAlgorithm;
        this.saltLength = saltLength;
    }

    @Nonnull
    static SaltGenerator newInstance(@Nonnull String randomAlgorithm, int saltLength) {
        return new SaltGeneratorImpl(randomAlgorithm, saltLength);
    }

    @Override
    @Nonnull
    public byte[] generateSalt() throws CiphererException {
        try {
            return Bytes.generateSecureRandomBytes(randomAlgorithm, saltLength);
        } catch (Exception e) {
            throw new CiphererException("Unable to generate salt due to some errors!", e);
        }
    }
}
