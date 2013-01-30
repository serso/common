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

import java.security.MessageDigest;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:17 PM
 */
public class HashProviderImpl implements HashProvider {

    private static final String PROVIDER = "BC";
    private static final String HASH_ALGORITHM = "SHA-512";

    @NotNull
    private final String hashAlgorithm;

    @NotNull
    private final String provider;

    public HashProviderImpl(@NotNull String hashAlgorithm, @NotNull String provider) {
        this.hashAlgorithm = hashAlgorithm;
        this.provider = provider;
    }

    @NotNull
    public static HashProvider newAndroidDefaultInstance() {
        return new HashProviderImpl(HASH_ALGORITHM, PROVIDER);
    }

    @Override
    @NotNull
    public String getHash(@NotNull String text, @NotNull String salt) throws CiphererException {
        try {
            final String input = text + salt;
            final MessageDigest md = MessageDigest.getInstance(hashAlgorithm, provider);
            return JBytes.toHex(md.digest(input.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new CiphererException("Unable to get hash due to some errors!", e);
        }
    }

}
