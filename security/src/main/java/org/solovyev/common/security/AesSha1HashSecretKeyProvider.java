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
import org.jetbrains.annotations.NotNull;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AesSha1HashSecretKeyProvider implements SecretKeyProvider {

    @NotNull
    private static final SecretKeyProvider instance = new AesSha1HashSecretKeyProvider();

    private AesSha1HashSecretKeyProvider() {
    }

    @NotNull
    public static SecretKeyProvider newInstance() {
        return instance;
    }

    @NotNull
    @Override
    public SecretKey getSecretKey(@NotNull String password, @NotNull String salt) throws CiphererException {
        final String secretKey = password + salt;

        try {
            final MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] secretKeyHash = sha.digest(secretKey.getBytes(Charsets.UTF_8));
            secretKeyHash = Arrays.copyOf(secretKeyHash, 16); // use only first 128 bit

            return new SecretKeySpec(secretKeyHash, Security.CIPHERER_ALGORITHM_AES);
        } catch (NoSuchAlgorithmException e) {
            throw new CiphererException(e);
        }
    }
}
