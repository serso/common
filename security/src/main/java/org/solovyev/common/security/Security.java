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

import org.solovyev.common.text.HexString;
import org.solovyev.common.text.StringDecoder;
import org.solovyev.common.text.StringEncoder;
import org.solovyev.common.text.base64.Base64StringDecoder;
import org.solovyev.common.text.base64.Base64StringEncoder;
import org.solovyev.common.text.hex.HexStringDecoder;
import org.solovyev.common.text.hex.HexStringEncoder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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

	@Nonnull
	public static Cipherer<byte[], byte[]> newCipherer(@Nonnull String ciphererAlgorithm,
													   @Nullable String provider,
													   @Nonnull InitialVectorDef initialVectorDef) {
		return ByteArrayCipherer.newInstance(ciphererAlgorithm, provider, initialVectorDef);
	}

	@Nonnull
	public static Cipherer<byte[], byte[]> newCiphererNoIv(@Nonnull String ciphererAlgorithm,
														   @Nullable String provider) {
		return ByteArrayCipherer.newNoIv(ciphererAlgorithm, provider);
	}

	@Nonnull
	public static SecretKeyProvider newPbeSecretKeyProvider(int iterationCount,
															@Nonnull String algorithm,
															@Nonnull String ciphererAlgorithm,
															@Nonnull String provider,
															int keyLength,
															int saltLength) {
		return PbeSecretKeyProvider.newInstance(iterationCount, algorithm, ciphererAlgorithm, provider, keyLength, saltLength);
	}

	@Nonnull
	public static HashProvider<byte[], byte[]> newHashProvider(@Nonnull String hashAlgorithm, @Nonnull String provider) {
		return ByteArrayHashProvider.newInstance(hashAlgorithm, provider);
	}

	@Nonnull
	public static SaltGenerator newSaltGenerator(@Nonnull String randomAlgorithm, int saltLength) {
		return SaltGeneratorImpl.newInstance(randomAlgorithm, saltLength);
	}

	@Nonnull
	public static SecretKeyProvider newAesSha1HashSecretKeyProvider() {
		return Sha1HashSecretKeyProvider.newAesInstance();
	}


	@Nonnull
	public static SecretKeyProvider newDesSha1HashSecretKeyProvider() {
		return Sha1HashSecretKeyProvider.newDesInstance();
	}

	@Nonnull
	public static Cipherer<String, String> newBase64StringCipherer(@Nonnull Cipherer<byte[], byte[]> byteCipherer) {
		return TypedCipherer.newInstance(byteCipherer, StringDecoder.getInstance(), StringEncoder.getInstance(), Base64StringDecoder.getInstance(), Base64StringEncoder.getInstance());
	}

	@Nonnull
	public static Cipherer<HexString, String> newHexStringCipherer(@Nonnull Cipherer<byte[], byte[]> byteCipherer) {
		return TypedCipherer.newInstance(byteCipherer, StringDecoder.getInstance(), StringEncoder.getInstance(), HexStringDecoder.getInstance(), HexStringEncoder.getInstance());
	}

	@Nonnull
	public static SecurityService<String, String, String> newBase64StringSecurityService(@Nonnull SecurityService<byte[], byte[], byte[]> byteSecurityService) {
		return SecurityServiceConverter.wrap(byteSecurityService, StringDecoder.getInstance(), StringEncoder.getInstance(), Base64StringDecoder.getInstance(), Base64StringEncoder.getInstance());
	}

	@Nonnull
	public static SecurityService<HexString, String, HexString> newHexStringSecurityService(@Nonnull SecurityService<byte[], byte[], byte[]> byteSecurityService) {
		return SecurityServiceConverter.wrap(byteSecurityService, StringDecoder.getInstance(), StringEncoder.getInstance(), HexStringDecoder.getInstance(), HexStringEncoder.getInstance());
	}

	@Nonnull
	public static <E, D, H> SecurityService<E, D, H> newSecurityService(@Nonnull Cipherer<E, D> cipherer,
																		@Nonnull SecretKeyProvider secretKeyProvider,
																		@Nonnull SaltGenerator saltGenerator,
																		@Nonnull HashProvider<D, H> hashProvider) {
		return SecurityServiceImpl.newInstance(cipherer, secretKeyProvider, saltGenerator, hashProvider);
	}
}
