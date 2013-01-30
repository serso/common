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

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:14 PM
 */
public final class Security {

    private Security() {
        throw new AssertionError();
    }

    public static byte[] generateRandomBytes(@NotNull String randomAlgorithm, int length) throws NoSuchAlgorithmException, NoSuchProviderException {
        final Random random = SecureRandom.getInstance(randomAlgorithm);
        byte[] result = new byte[length];
        random.nextBytes(result);
        return result;
    }
}
