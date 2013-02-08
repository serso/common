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
import org.solovyev.common.text.HexString;
import org.solovyev.common.text.base64.Base64StringDecoder;
import org.solovyev.common.text.base64.Base64StringEncoder;
import org.solovyev.common.text.StringDecoder;
import org.solovyev.common.text.StringEncoder;
import org.solovyev.common.text.hex.HexStringDecoder;
import org.solovyev.common.text.hex.HexStringEncoder;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:14 PM
 */
public class Security {

    protected Security() {
        throw new AssertionError();
    }

    @NotNull
    protected static Cipherer<byte[], byte[]> newAndroidAesCipherer() {
        return ByteArrayCipherer.newAndroidAesCipherer();
    }

    @NotNull
    public static Cipherer<byte[], byte[]> newCipherer(@Nullable InitialVectorDef initialVectorDef,
                                       @NotNull String cipherAlgorithm,
                                       @Nullable String provider) {
        return ByteArrayCipherer.newInstance(initialVectorDef, cipherAlgorithm, provider);
    }

    @NotNull
    protected static SecretKeyProvider newAndroidSecretKeyProvider() {
        return PbeSecretKeyProvider.newAndroidPbeSecretKeyProvider();
    }

    @NotNull
    public static SecretKeyProvider newSecretKeyProvider(int iterationCount,
                                                         @NotNull String algorithm,
                                                         @NotNull String secretKeyAlgorithm,
                                                         @Nullable String provider,
                                                         int keyLength) {
        return PbeSecretKeyProvider.newInstance(iterationCount, algorithm, secretKeyAlgorithm, provider, keyLength);
    }

    @NotNull
    public static Cipherer<String, String> newBase64StringCipherer(@NotNull Cipherer<byte[], byte[]> byteCipherer) {
        return TypedCipherer.newInstance(byteCipherer, StringDecoder.getInstance(), StringEncoder.getInstance(), Base64StringDecoder.getInstance(), Base64StringEncoder.getInstance());
    }

    @NotNull
    public static Cipherer<HexString, String> newHexStringCipherer(@NotNull Cipherer<byte[], byte[]> byteCipherer) {
        return TypedCipherer.newInstance(byteCipherer, StringDecoder.getInstance(), StringEncoder.getInstance(), HexStringDecoder.getInstance(), HexStringEncoder.getInstance());
    }
}
