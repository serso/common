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

import java.security.MessageDigest;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:17 PM
 */
class ByteArrayHashProvider implements HashProvider<byte[], byte[]> {

    @NotNull
    private final String hashAlgorithm;

    @NotNull
    private final String provider;

    private ByteArrayHashProvider(@NotNull String hashAlgorithm, @NotNull String provider) {
        this.hashAlgorithm = hashAlgorithm;
        this.provider = provider;
    }

    @NotNull
    static HashProvider<byte[], byte[]> newInstance(@NotNull String hashAlgorithm, @NotNull String provider) {
        return new ByteArrayHashProvider(hashAlgorithm, provider);
    }

    @Override
    @NotNull
    public byte[] getHash(@NotNull byte[] object, @NotNull byte[] salt) throws CiphererException {
        try {
            final MessageDigest md = MessageDigest.getInstance(hashAlgorithm, provider);
            return md.digest(Bytes.concat(object, salt));
        } catch (Exception e) {
            throw new CiphererException("Unable to get hash due to some errors!", e);
        }
    }

}
