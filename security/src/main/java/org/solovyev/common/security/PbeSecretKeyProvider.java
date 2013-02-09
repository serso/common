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
import org.solovyev.common.Bytes;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:08 PM
 */
class PbeSecretKeyProvider implements SecretKeyProvider {

    private final int iterationCount;

    @NotNull
    private final String algorithm;

    @NotNull
    private final String provider;

    @NotNull
    private final String ciphererAlgorithm;

    private final int keyLength;

    private final int saltLength;

    private PbeSecretKeyProvider(int iterationCount,
                                 @NotNull String algorithm,
                                 @NotNull String ciphererAlgorithm,
                                 @NotNull String provider,
                                 int keyLength,
                                 int saltLength) {
        this.iterationCount = iterationCount;
        this.algorithm = algorithm;
        this.provider = provider;
        this.ciphererAlgorithm = ciphererAlgorithm;
        this.keyLength = keyLength;
        this.saltLength = saltLength;
    }

    @NotNull
    public static SecretKeyProvider newInstance(int iterationCount,
                                                @NotNull String algorithm,
                                                @NotNull String ciphererAlgorithm,
                                                @NotNull String provider,
                                                int keyLength,
                                                int saltLength) {
        return new PbeSecretKeyProvider(iterationCount, algorithm, ciphererAlgorithm, provider, keyLength, saltLength);
    }

    @Override
    @NotNull
    public SecretKey getSecretKey(@NotNull String password, @NotNull String salt) throws CiphererException {
        try {
            byte[] saltBytes = Bytes.hexToBytes(salt);
            if ( saltBytes.length != saltLength ) {
                // we need to prolong/truncate our byte array
                final MessageDigest sha = MessageDigest.getInstance("SHA-1");
                saltBytes = sha.digest(saltBytes);
                saltBytes = Arrays.copyOf(saltBytes, saltLength);
            }

            final PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), saltBytes, iterationCount, keyLength);
            final SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm, provider);

            final SecretKey tmp = factory.generateSecret(pbeKeySpec);

            return new SecretKeySpec(tmp.getEncoded(), ciphererAlgorithm);
        } catch (Exception e) {
            throw new CiphererException("Unable to get secret key due to some errors!", e);
        }
    }
}
