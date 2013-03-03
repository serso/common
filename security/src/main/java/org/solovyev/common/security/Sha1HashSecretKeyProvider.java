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

import org.apache.commons.codec.Charsets;

import javax.annotation.Nonnull;

import org.solovyev.common.Bytes;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Sha1HashSecretKeyProvider implements SecretKeyProvider {

    @Nonnull
    private static final SecretKeyProvider aes = newInstance(Security.CIPHERER_ALGORITHM_AES, 16);

    @Nonnull
    private static final SecretKeyProvider des = newInstance(Security.CIPHERER_ALGORITHM_DES, 16);

    private final int saltLength;

    @Nonnull
    private final String ciphererAlgorithm;

    private Sha1HashSecretKeyProvider(@Nonnull String ciphererAlgorithm, int saltLength) {
        this.ciphererAlgorithm = ciphererAlgorithm;
        this.saltLength = saltLength;
    }

    @Nonnull
    public static SecretKeyProvider newAesInstance() {
        return aes;
    }

    @Nonnull
    public static SecretKeyProvider newDesInstance() {
        return des;
    }

    @Nonnull
    public static Sha1HashSecretKeyProvider newInstance(@Nonnull String ciphererAlgorithm, int saltLength) {
        return new Sha1HashSecretKeyProvider(ciphererAlgorithm, saltLength);
    }

    @Nonnull
    @Override
    public SecretKey getSecretKey(@Nonnull String secret, @Nonnull byte[] salt) throws CiphererException {
        final String secretKey = secret + Bytes.toHex(salt);

        try {
            final MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] secretKeyHash = sha.digest(secretKey.getBytes(Charsets.UTF_8));
            secretKeyHash = Arrays.copyOf(secretKeyHash, saltLength);

            return new SecretKeySpec(secretKeyHash, ciphererAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new CiphererException(e);
        }
    }
}
