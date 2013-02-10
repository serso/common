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

    public static final String CIPHERER_ALGORITHM_AES = "AES";
    public static final String CIPHERER_ALGORITHM_DES = "DES";

    protected Security() {
        throw new AssertionError();
    }

    @NotNull
    public static Cipherer<byte[], byte[]> newCipherer(@NotNull String ciphererAlgorithm,
                                                       @Nullable String provider,
                                                       @NotNull InitialVectorDef initialVectorDef) {
        return ByteArrayCipherer.newInstance(ciphererAlgorithm, provider, initialVectorDef);
    }

    @NotNull
    public static Cipherer<byte[], byte[]> newCiphererNoIv(@NotNull String ciphererAlgorithm,
                                                           @Nullable String provider) {
        return ByteArrayCipherer.newNoIv(ciphererAlgorithm, provider);
    }

    @NotNull
    public static SecretKeyProvider newPbeSecretKeyProvider(int iterationCount,
                                                            @NotNull String algorithm,
                                                            @NotNull String ciphererAlgorithm,
                                                            @NotNull String provider,
                                                            int keyLength,
                                                            int saltLength) {
        return PbeSecretKeyProvider.newInstance(iterationCount, algorithm, ciphererAlgorithm, provider, keyLength, saltLength);
    }

    @NotNull
    public static HashProvider<byte[], byte[]> newHashProvider(@NotNull String hashAlgorithm, @NotNull String provider) {
        return ByteArrayHashProvider.newInstance(hashAlgorithm, provider);
    }

    @NotNull
    public static SaltGenerator newSaltGenerator(@NotNull String randomAlgorithm, int saltLength) {
        return SaltGeneratorImpl.newInstance(randomAlgorithm, saltLength);
    }

    @NotNull
    public static SecretKeyProvider newAesSha1HashSecretKeyProvider() {
        return Sha1HashSecretKeyProvider.newAesInstance();
    }


    @NotNull
    public static SecretKeyProvider newDesSha1HashSecretKeyProvider() {
        return Sha1HashSecretKeyProvider.newDesInstance();
    }

    @NotNull
    public static Cipherer<String, String> newBase64StringCipherer(@NotNull Cipherer<byte[], byte[]> byteCipherer) {
        return TypedCipherer.newInstance(byteCipherer, StringDecoder.getInstance(), StringEncoder.getInstance(), Base64StringDecoder.getInstance(), Base64StringEncoder.getInstance());
    }

    @NotNull
    public static Cipherer<HexString, String> newHexStringCipherer(@NotNull Cipherer<byte[], byte[]> byteCipherer) {
        return TypedCipherer.newInstance(byteCipherer, StringDecoder.getInstance(), StringEncoder.getInstance(), HexStringDecoder.getInstance(), HexStringEncoder.getInstance());
    }

    @NotNull
    public static SecurityService<String, String, String> newBase64StringSecurityService(@NotNull SecurityService<byte[], byte[], byte[]> byteSecurityService) {
        return SecurityServiceConverter.wrap(byteSecurityService, StringDecoder.getInstance(), StringEncoder.getInstance(), Base64StringDecoder.getInstance(), Base64StringEncoder.getInstance());
    }

    @NotNull
    public static SecurityService<HexString, String, HexString> newHexStringSecurityService(@NotNull SecurityService<byte[], byte[], byte[]> byteSecurityService) {
        return SecurityServiceConverter.wrap(byteSecurityService, StringDecoder.getInstance(), StringEncoder.getInstance(), HexStringDecoder.getInstance(), HexStringEncoder.getInstance());
    }

    @NotNull
    public static <E, D, H> SecurityService<E, D, H> newSecurityService(@NotNull Cipherer<E, D> cipherer,
                                                                        @NotNull SecretKeyProvider secretKeyProvider,
                                                                        @NotNull SaltGenerator saltGenerator,
                                                                        @NotNull HashProvider<D, H> hashProvider) {
        return SecurityServiceImpl.newInstance(cipherer, secretKeyProvider, saltGenerator, hashProvider);
    }
}
