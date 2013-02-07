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
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.Bytes;
import org.solovyev.common.text.Strings;

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

    private static final int PBE_ITERATION_COUNT = 100;
    private static final String PBE_ALGORITHM = "PBEWithSHA256And256BitAES-CBC-BC";
    private static final String PROVIDER = "BC";
    private static final String SECRET_KEY_ALGORITHM = "AES";
    private static final int PBE_KEY_LENGTH = 256;

    private final int iterationCount;

    @NotNull
    private final String algorithm;

    @Nullable
    private final String provider;

    @NotNull
    private final String secretKeyAlgorithm;

    private final int keyLength;

    private PbeSecretKeyProvider(int iterationCount,
                                 @NotNull String algorithm,
                                 @NotNull String secretKeyAlgorithm,
                                 @Nullable String provider,
                                 int keyLength) {
        this.iterationCount = iterationCount;
        this.algorithm = algorithm;
        this.provider = provider;
        this.secretKeyAlgorithm = secretKeyAlgorithm;
        this.keyLength = keyLength;
    }

    @NotNull
    public static SecretKeyProvider newAndroidPbeSecretKeyProvider() {
        return newInstance(PBE_ITERATION_COUNT, PBE_ALGORITHM, SECRET_KEY_ALGORITHM, PROVIDER, PBE_KEY_LENGTH);
    }

    @NotNull
    public static SecretKeyProvider newInstance(int iterationCount,
                                                @NotNull String algorithm,
                                                @NotNull String secretKeyAlgorithm,
                                                @Nullable String provider,
                                                int keyLength) {
        return new PbeSecretKeyProvider(iterationCount, algorithm, secretKeyAlgorithm, provider, keyLength);
    }

    @Override
    @NotNull
    public SecretKey getSecretKey(@NotNull String password, @NotNull String salt) throws CiphererException {
        try {
            final PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), Bytes.hexToBytes(Strings.toHex(salt)), iterationCount, keyLength);
            final SecretKeyFactory factory;
            if (provider != null) {
                factory = SecretKeyFactory.getInstance(algorithm, provider);
            } else {
                factory = SecretKeyFactory.getInstance(algorithm);
            }
            final SecretKey tmp = factory.generateSecret(pbeKeySpec);

            return new SecretKeySpec(tmp.getEncoded(), secretKeyAlgorithm);
        } catch (Exception e) {
            throw new CiphererException("Unable to get secret key due to some errors!", e);
        }
    }
}
