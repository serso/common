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
import org.solovyev.common.JBytes;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:13 PM
 */
public class SaltGeneratorImpl implements SaltGenerator {

    private static final int SALT_LENGTH = 20;
    private static final String RANDOM_ALGORITHM = "SHA1PRNG";

    @NotNull
    private final String randomAlgorithm;
    private final int saltLength;

    public SaltGeneratorImpl(@NotNull String randomAlgorithm, int saltLength) {
        this.randomAlgorithm = randomAlgorithm;
        this.saltLength = saltLength;
    }

    @NotNull
    public static SaltGenerator newAndroidDefaultInstance() {
        return new SaltGeneratorImpl(RANDOM_ALGORITHM, SALT_LENGTH);
    }

    @Override
    @NotNull
    public String generateSalt() throws CiphererException {
        try {
            byte[] salt = JSecurity.generateRandomBytes(randomAlgorithm, saltLength);
            return JBytes.toHex(salt);
        } catch (Exception e) {
            throw new CiphererException("Unable to generate salt due to some errors!", e);
        }
    }
}
